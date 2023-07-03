package com.pt.vx.service.vx;

import cn.hutool.core.date.ChineseDate;
import cn.hutool.json.JSONUtil;
import com.pt.vx.config.KeyConfig;
import com.pt.vx.config.MainConfig;
import com.pt.vx.config.WeatherConfig;
import com.pt.vx.config.WechatConfig;
import com.pt.vx.pojo.BirthDay;
import com.pt.vx.pojo.DataInfo;
import com.pt.vx.pojo.User;
import com.pt.vx.pojo.VxMessageDto;
import com.pt.vx.pojo.BaseWeather;
import com.pt.vx.pojo.KeyDTO;
import com.pt.vx.pojo.WeatherFuture;
import com.pt.vx.pojo.WeatherOtherInfo;
import com.pt.vx.service.api.ApiMessageService;
import com.pt.vx.service.weather.WeatherAdapter;
import com.pt.vx.service.weather.WeatherService;
import com.pt.vx.utils.DateUtil;
import com.pt.vx.utils.ThreadPoolUtil;
import com.pt.vx.utils.VxUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class MessageService {

    private VxMessageDto dto;

    private final WeatherService weatherService = new WeatherAdapter();
    private final SecureRandom random = new SecureRandom();

    private final ApiMessageService apiMessageService = new ApiMessageService();


    public void sendMessage(User user) {
        log.info("开始处理用户：{}", JSONUtil.toJsonStr(user));
        if (Objects.equals(MainConfig.messageMode, 0) || Objects.isNull(dto)) {
            dto = new VxMessageDto();
            Map<String, DataInfo> context = buildMessageContext(user);
            dto.setTemplate_id(user.getTemplateId());
            dto.setData(context);
        }
        dto.setTouser(user.getVx());
        String message = JSONUtil.toJsonStr(dto);
        VxUtil.sendMessage(message, WechatConfig.VxAppId, WechatConfig.VxAppSecret);
    }

    private Map<String, DataInfo> buildMessageContext(User user) {
        ConcurrentHashMap<String, DataInfo> map = new ConcurrentHashMap<>();
        List<CompletableFuture<Void>> apiInfoList = buildApiInfoFuture(map, user);


        CompletableFuture<Void> weekFuture = buildWeekFuture(map);
        apiInfoList.add(weekFuture);

        CompletableFuture<Void> birthFuture = buildBirthFuture(map, user);
        apiInfoList.add(birthFuture);

        if (WeatherConfig.OPEN) {
            CompletableFuture<List<BaseWeather>> weatherFuture = buildWeatherFuture(map, user);
            CompletableFuture<Void> buildWeatherInfoFuture = buildWeatherOtherInfoFuture(weatherFuture,birthFuture,map);
            apiInfoList.add(buildWeatherInfoFuture);
        }

        CompletableFuture<Void> all = CompletableFuture.allOf(apiInfoList.toArray(new CompletableFuture[apiInfoList.size()]));
        try {
            all.get();
        } catch (Exception e) {
            log.error("发送消息出现错误，错误为：{}", e.getMessage(), e);
        }
        return map;
    }

    private List<CompletableFuture<Void>> buildApiInfoFuture(Map<String, DataInfo> map, User user) {
        List<CompletableFuture<Void>> futureList = new ArrayList<>();
        if (MainConfig.randomApiMessageMode) {
            CompletableFuture<Void> randomInfo = CompletableFuture.runAsync(() -> {
                KeyDTO randomKey = apiMessageService.getRandomKey();
                String apiMessage = apiMessageService.getApiMessage(randomKey, user);
                setMap(map, KeyConfig.KEY_RANDOM_INFO, apiMessage);

            }, ThreadPoolUtil.pool);
            futureList.add(randomInfo);
        } else {
            ApiMessageService.keyDTOS.stream().filter(KeyDTO::isOpen).forEach(keyDTO -> {
                CompletableFuture<Void> infoFuture = CompletableFuture.runAsync(() -> {
                    String apiMessage = apiMessageService.getApiMessage(keyDTO, user);
                    setMap(map, keyDTO, apiMessage);
                }, ThreadPoolUtil.pool);
                futureList.add(infoFuture);
            });
        }
        return futureList;
    }

    private CompletableFuture<Void> buildBirthFuture(Map<String, DataInfo> map, User user) {
        return CompletableFuture.runAsync(() -> {
            BirthDay[] birthDays = user.getBirthDays();
            for (int i = 0; i < birthDays.length; i++) {
                BirthDay birthDay = birthDays[i];
                boolean chineseFlag = birthDay.isChineseFlag();
                boolean countFlag = birthDay.isCountFlag();
                String day = null;
                if (countFlag) {
                    day = getPassDay(birthDay, chineseFlag);
                } else {
                    String nextChineseBirthDay = getNextBirthDay(birthDay, chineseFlag);
                    day = nextChineseBirthDay;
                    if ("0".equals(nextChineseBirthDay)) {
                        //倒计时额外信息
                        if (!Objects.equals(MainConfig.otherInfoMode, 0)) {
                            String value;
                            DataInfo dataInfo = map.get(KeyConfig.KEY_OTHER_INFO.getKey());
                            if(Objects.isNull(dataInfo) || StringUtils.isBlank(dataInfo.getValue())){
                                value = birthDay.getInfo();
                            }else {
                                value = dataInfo.getValue() + "\n" + birthDay.getInfo();
                            }
                            setMap(map, KeyConfig.KEY_OTHER_INFO, value);
                        }
                    }
                }
                setMap(map, KeyConfig.KEY_BIRTHDAY, day, i);

            }
            setMap(map, KeyConfig.KEY_USER_NAME, user.getUserName());
        }, ThreadPoolUtil.pool);
    }




    private CompletableFuture<Void> buildWeekFuture(Map<String, DataInfo> map) {
        return CompletableFuture.runAsync(() -> {
            LocalDate today = LocalDate.now();
            for (int i = 0; i < 5; i++) {
                LocalDate now = today.plusDays(i);
                String week = DateUtil.getWeek(now, MainConfig.chineseWeek);
                String date = now.toString();
                setMap(map, KeyConfig.KEY_DATE, date, i);
                setMap(map, KeyConfig.KEY_WEEK, week, i);
            }
        }, ThreadPoolUtil.pool);
    }

    private CompletableFuture<List<BaseWeather>> buildWeatherFuture(Map<String, DataInfo> map, User user) {
        return CompletableFuture.supplyAsync(() -> {
            List<BaseWeather> list = new ArrayList<>();
            try {
                if (Objects.equals(0, WeatherConfig.getWeatherType)) {
                    //实时天气
                    BaseWeather weatherNow = weatherService.getWeatherNow(user.getAddress(), user.getCity());
                    setMap(map, KeyConfig.KEY_WEATHER_NOW, weatherNow.getInfo());
                    setMap(map, KeyConfig.KEY_HUMIDITY_NOW, weatherNow.getHumidity());
                    setMap(map, KeyConfig.KEY_TEMPERATURE_NOW, weatherNow.getTemperature());
                    setMap(map, KeyConfig.KEY_WIND_NOW, weatherNow.getWind());
                    setMap(map, KeyConfig.KEY_POWER_NOW, weatherNow.getPower());
                    list.add(weatherNow);
                    //buildWeatherOtherInfo(map, weatherNow);
                } else {
                    //天气预报
                    List<WeatherFuture> weatherFutureList = weatherService.getWeatherFuture(user.getAddress(), user.getCity());
                    for (int i = 0; i < weatherFutureList.size(); i++) {
                        WeatherFuture weather = weatherFutureList.get(i);
                        setMap(map, KeyConfig.KEY_POWER_DAY, weather.getWeatherDay().getPower(), i);
                        setMap(map, KeyConfig.KEY_TEMPERATURE_DAY, weather.getWeatherDay().getTemperature(), i);
                        setMap(map, KeyConfig.KEY_WEATHER_DAY, weather.getWeatherDay().getInfo(), i);
                        setMap(map, KeyConfig.KEY_WIND_DAY, weather.getWeatherDay().getWind(), i);
                        setMap(map, KeyConfig.KEY_POWER_DAY, weather.getWeatherDay().getPower(), i);

                        setMap(map, KeyConfig.KEY_POWER_NIGHT, weather.getWeatherNight().getPower(), i);
                        setMap(map, KeyConfig.KEY_TEMPERATURE_NIGHT, weather.getWeatherNight().getTemperature(), i);
                        setMap(map, KeyConfig.KEY_WEATHER_NIGHT, weather.getWeatherNight().getInfo(), i);
                        setMap(map, KeyConfig.KEY_WIND_NIGHT, weather.getWeatherNight().getWind(), i);
                        setMap(map, KeyConfig.KEY_POWER_NIGHT, weather.getWeatherNight().getPower(), i);

                        if (i == 0) {
                            setMap(map, KeyConfig.KEY_SUN_RISE, weather.getSunUp());
                            setMap(map, KeyConfig.KEY_SUN_SET, weather.getSunDown());

                            list.add(weather.getWeatherDay());
                            list.add(weather.getWeatherNight());
                        }
                    }
                }
            } catch (Exception e) {
                log.error("获取天气失败，返回值为：{}", e.getMessage());
            }

            return list;
        }, ThreadPoolUtil.pool);
    }

    private CompletableFuture<Void> buildWeatherOtherInfoFuture(CompletableFuture<List<BaseWeather>> weatherFuture,CompletableFuture<Void> birthFuture,Map<String, DataInfo> map) {
        return weatherFuture.thenAcceptBothAsync(birthFuture, (list, x) -> {
            if (Objects.equals(MainConfig.otherInfoMode, 0)) {
                return;
            }

            if (Objects.equals(MainConfig.otherInfoMode, 1) && map.containsKey(KeyConfig.KEY_OTHER_INFO.getKey())) {
                return;
            }

            list.forEach(weather -> buildWeatherOtherInfo(map,weather));

        }, ThreadPoolUtil.pool);
    }

    /**
     * 构造天气的额外消息
     *
     */
    private void buildWeatherOtherInfo(Map<String, DataInfo> map, BaseWeather weather) {
        for (WeatherOtherInfo weatherOtherInfo : MainConfig.weatherOtherInfos) {
            String key = weatherOtherInfo.getKey();
            if (StringUtils.isBlank(key)) {
                continue;
            }
            if (Objects.equals(weatherOtherInfo.getType(), 0)) {
                int temperatureKey = Integer.parseInt(key.substring(1));
                int temperatureNow = Integer.parseInt(weather.getTemperature());
                boolean isGreater = key.startsWith(">") && temperatureNow > temperatureKey;
                boolean isLess = key.startsWith("<") && temperatureNow < temperatureKey;
                boolean isEqual = key.startsWith("=") && temperatureNow == temperatureKey;
                if (isGreater || isLess || isEqual) {
                    setMapExist(map, KeyConfig.KEY_OTHER_INFO, weatherOtherInfo.getMessage());
                }
            } else if (Objects.equals(weatherOtherInfo.getType(), 1)) {
                String info = weather.getInfo();
                if (info.contains(key)) {
                    setMapExist(map, KeyConfig.KEY_OTHER_INFO, weatherOtherInfo.getMessage());
                }
            }

        }
    }

    private String getNextBirthDay(BirthDay birthDay, boolean chineseFlag) {
        if (chineseFlag) {
            return DateUtil.getNextChineseBirthDay(birthDay.getMonth(), birthDay.getDay());
        } else {
            return DateUtil.getNextBirthDay(birthDay.getMonth(), birthDay.getDay());
        }
    }

    private String getPassDay(BirthDay birthDay, boolean chineseFlag) {
        LocalDate now = LocalDate.now();
        if (chineseFlag) {
            ChineseDate chineseDate = new ChineseDate(birthDay.getYear(), birthDay.getMonth(), birthDay.getDay());
            return DateUtil.passChineseDayAbs(chineseDate,now);
        } else {
            LocalDate of = LocalDate.of(birthDay.getYear(), birthDay.getMonth(), birthDay.getDay());
            return DateUtil.passDayAbs(now,of);
        }
    }


    private void setMapExist(Map<String, DataInfo> map, KeyDTO dto, String value) {
        setMapExist(map, dto, value, null);
    }

    private void setMapExist(Map<String, DataInfo> map, KeyDTO dto, String value, Integer id) {
        DataInfo dataInfo = map.get(dto.getKey());
        if (Objects.nonNull(dataInfo)) {
            String newValue = dataInfo.getValue();
            if (newValue.contains(value)) {
                return;
            }
            newValue += "\n" + value;
            setMap(map, dto, newValue, id);
        } else {
            setMap(map, dto, value, id);
        }
    }

    private void setMap(Map<String, DataInfo> map, KeyDTO dto, String value) {
        setMap(map, dto, value, null);
    }

    private void setMap(Map<String, DataInfo> map, KeyDTO dto, String value, Integer id) {
        if (StringUtils.isNotBlank(value) && dto.isOpen()) {
            DataInfo dataInfo;
            String color = this.getColor(dto.getColor());
            String key = Objects.isNull(id) || Objects.equals(id, 0) ? dto.getKey() : dto.getKey() + id;
            if (MainConfig.keyMessageSplit) {
                int x = (int) (value.length() / (float) MainConfig.splitMessageLength + 0.5f);
                int i = 0;
                do {
                    if (i == 1) {
                        key += MainConfig.splitMessageFlag;
                    }
                    if (i > 1) {
                        key += i;
                    }
                    String substring = value.substring(i * MainConfig.splitMessageLength, Math.min(i * MainConfig.splitMessageLength + MainConfig.splitMessageLength, value.length()));
                    dataInfo = new DataInfo();
                    dataInfo.setValue(substring);
                    dataInfo.setColor(color);
                    map.put(key, dataInfo);
                    i++;
                } while (i < x);

            } else {
                dataInfo = new DataInfo();
                dataInfo.setValue(value);
                dataInfo.setColor(color);
                map.put(key, dataInfo);
            }
        }
    }

    /**
     * 随机颜色
     *
     * @param color
     * @return
     */
    private String getColor(String color) {
        if (MainConfig.randomMessageColorMode) {
            int i = random.nextInt(MainConfig.randomColors.length);
            return MainConfig.randomColors[i];
        } else {
            return color;
        }
    }
}
