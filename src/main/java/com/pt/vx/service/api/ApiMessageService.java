package com.pt.vx.service.api;

import cn.hutool.core.date.ChineseDate;
import cn.hutool.core.stream.CollectorUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.pt.vx.config.KeyConfig;
import com.pt.vx.config.MainConfig;
import com.pt.vx.pojo.BirthDay;
import com.pt.vx.pojo.User;
import com.pt.vx.pojo.KeyDTO;
import com.pt.vx.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class ApiMessageService {

    private final SecureRandom random = new SecureRandom();

    public static List<KeyDTO> keyDTOS = new ArrayList<>();

    static {
        keyDTOS.add(KeyConfig.KEY_QING_HUA);
        keyDTOS.add(KeyConfig.KEY_DUAN_ZI);
        keyDTOS.add(KeyConfig.KEY_DU_JI_TANG);
        keyDTOS.add(KeyConfig.KEY_SENTENCE);
        keyDTOS.add(KeyConfig.KEY_MI_YU);
        keyDTOS.add(KeyConfig.KEY_HOROSCOPE);
        keyDTOS.add(KeyConfig.KEY_HISTORY_TODAY);
        keyDTOS.add(KeyConfig.KEY_XIN_GUAN);
    }
    public String getApiMessage(KeyDTO keyDTO, User user){
        String result = null;
        if (KeyConfig.KEY_QING_HUA.equalsKey(keyDTO)){
            result =  getQinghua();
        }else if (KeyConfig.KEY_DUAN_ZI.equalsKey(keyDTO)){
            result = getDuanZi();
        }else if (KeyConfig.KEY_DU_JI_TANG.equalsKey(keyDTO)){
            result = getDuJiTang();
        }else if (KeyConfig.KEY_SENTENCE.equalsKey(keyDTO)){
            result = getRandomSentence();
        }else if (KeyConfig.KEY_MI_YU.equalsKey(keyDTO)){
            result = getRiddle();
        }else if (KeyConfig.KEY_HOROSCOPE.equalsKey(keyDTO)){
            BirthDay[] birthDays = user.getBirthDays();
            if( Objects.nonNull(birthDays)){
                result = getHoroscope(birthDays[0]);
            }
        }else if (KeyConfig.KEY_HISTORY_TODAY.equalsKey(keyDTO)){
            result = getHistoryToday();
        }else if (KeyConfig.KEY_XIN_GUAN.equalsKey(keyDTO)){
            String address = user.getAddress().split("省",2)[0];
            result = getXinGuan(address);
        }
        log.info("随机API接口为：{},获取的结果为：{}",keyDTO.getKey(),result);
        return result;
    }

    /**
     *
     * @return 随机一个API访问
     */
    public KeyDTO getRandomKey(){
        List<KeyDTO> collect = keyDTOS.stream().filter(KeyDTO::isOpen).collect(Collectors.toList());
        if(collect.isEmpty()){
            return null;
        }
        int i = random.nextInt(collect.size());
        return collect.get(i);
    }

    /**
     * 随机情话
     *
     * @return
     */
    private String getQinghua() {
        String url = "https://api.uomg.com/api/rand.qinghua?format=json";
        String s = HttpUtil.get(url);
        JSONObject jsonObject = JSONUtil.parseObj(s);
        return jsonObject.getStr("content");
    }

    /**
     * @return 获取段子
     */
    private String getDuanZi() {
        String url = "https://api.linhun.vip/api/duanzi?apiKey=c15d45f2e9d44858a7d95f421d0e7df1";
        String result = HttpUtil.get(url);
        JSONObject jsonObject = JSONUtil.parseObj(result);
        return jsonObject.getStr("mum");
    }

    /**
     * @return 获取毒鸡汤
     */
    private String getDuJiTang() {
        String url = "https://api.linhun.vip/api/dujitang?apiKey=067e54eb3b4128ed513f3bbad1a2f394";
        String result = HttpUtil.get(url);
        JSONObject jsonObject = JSONUtil.parseObj(result);
        return jsonObject.getStr("msg");
    }

    /**
     * @return 随机一句
     */
    private String getRandomSentence() {
        String url = "https://api.linhun.vip/api/Aword?apiKey=3df6350da79fc77334102f876f72dad1";
        String result = HttpUtil.get(url);
        JSONObject jsonObject = JSONUtil.parseObj(result);
        return jsonObject.getStr("duanju");
    }

    /**
     * @return 谜语
     */
    private String getRiddle() {
        String url = "https://api.linhun.vip/api/miyu?apiKey=ef06f5c549a2051286bb1a5d530c2bae";
        String result = HttpUtil.get(url);
        JSONObject jsonObject = JSONUtil.parseObj(result);
        String name = jsonObject.getStr("name");
        String tips = jsonObject.getStr("Tips");
        String answer = jsonObject.getStr("Answer");
        log.info("---获取谜语--- 谜题：{}，提示：{}，谜底：{}", name, tips, answer);
        return name + "(提示：" + tips + ")";
    }

    /**
     * @return 获取星座分析
     */
    private String getHoroscope(BirthDay birthDay) {
        String url = "https://api.linhun.vip/api/xzys?apiKey=1ceee3e6244c63171e9f2228b5eac1a4&name=%s";
        int month = birthDay.getMonth();
        int day = birthDay.getDay();
        boolean chinese = birthDay.isChineseFlag();
        if (chinese) {
            int year = birthDay.getYear();
            ChineseDate chineseDate = new ChineseDate(year, month, day);
            month = chineseDate.getGregorianMonthBase1();
            day = chineseDate.getGregorianDay();
        }
        String horoscope = "";
        if (month == 3 && day >= 21 || month == 4 && day <= 20) {
            horoscope =  "白羊";
        } else if (month == 4 || month == 5 && day <= 20) {
            horoscope = "金牛";
        } else if (month == 5 || month == 6 && day <= 20) {
            horoscope = "双子";
        } else if (month == 6 || month == 7 && day <= 22) {
            horoscope = "巨蟹";
        } else if (month == 7 || month == 8 && day <= 22) {
            horoscope = "狮子";
        } else if (month == 8 || month == 9 && day <= 22) {
            horoscope = "处女";
        } else if (month == 9 || month == 10 && day <= 22) {
            horoscope = "天秤";
        } else if (month == 10 || month == 11 && day <= 22) {
            horoscope = "天蝎";
        } else if (month == 11 || month == 12 && day <= 22) {
            horoscope = "射手";
        } else if (month == 12 || month == 1 && day <= 21) {
            horoscope = "摩羯";
        } else if (month == 1 || month == 2 && day <= 19) {
            horoscope = "水瓶";
        } else if (month == 2 || month == 3) {
            horoscope = "双鱼";
        }else {
            horoscope = "不知道";
        }
        return HttpUtil.get(String.format(url, horoscope));
    }

    /**
     * @return 获取历史上今天
     */
    private String getHistoryToday() {
        String url = "https://api.linhun.vip/api/history?format=json&apiKey=099a422902a55e6020abf0f5f97031e9";
        String result = HttpUtil.get(url);
        JSONObject jsonObject = JSONUtil.parseObj(result);
        JSONArray content = jsonObject.getJSONArray("content");
        StringBuilder history = new StringBuilder();
        int historyTodayCount = content.size();
        if(MainConfig.historyTodayCount < 1 ){
            historyTodayCount = 1;
        }else if(MainConfig.historyTodayCount < historyTodayCount){
            historyTodayCount = MainConfig.historyTodayCount;
        }

        for (int i = 0; i < historyTodayCount; i++) {
            String str = content.getStr(i);
            history.append(str).append("\n");
        }
        return history.toString();
    }

    /**
     *
     * @param province 省份
     * @return 新冠信息
     */
    private  String getXinGuan(String province){
        String url = "https://api.linhun.vip/api/yiqing?apiKey=c2278bb8c0aa62d017e0c17b9561537c&keyword=%s";
        String result = HttpUtil.get(String.format(url, province));
        JSONObject jsonObject = JSONUtil.parseObj(result);
        JSONObject data = jsonObject.getJSONObject("data");
        String time = jsonObject.getStr("UpTime");
        String now = data.getStr("现存确诊");
        String nowNo = data.getStr("现存无症状");
        return time + "现存确诊:" + now + ",现存无症状:"+nowNo;
    }



}
