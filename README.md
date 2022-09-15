# VxMessageWeatherActions
微信消息推送Gitub Actions版本+天气预报
> 注意
> 
> actions上设置的时间并不是准时的。
> 
> 比如设置8.08，系统会帮你在8.08的时候进行执行排队,轮到你才会执行。
> 
> 人多的时候可能会排队1个小时左右，甚至更久。所以通常不要设置的热门时间点，如每个小时的整点。

1. 复制本项目到你的仓库，设置为私有项目
2. 修改`AllConfig`中对应的地方替换成你自己从[微信公众号测试平台](https://mp.weixin.qq.com/debug/cgi-bin/sandboxinfo?action=showinfo&t=sandbox/index)
获取的内容即可
3. 修改`AllConfig`中对应的地方替换成你从[高德地图开发者平台](https://lbs.amap.com/api/webservice/guide/create-project/get-key)获取的KEY。（这个链接直通获取key的教程）
4. 修改`AllConfig`中对应的地方,替换成你或者你对象的个人信息,如生日，纪念日，地址等
5. 如果项目上方没有Actions,就要手动开启Actions，步骤为：
    1. 点击项目上方的Settings
    2. 点击页面左边Actions
    3. 点击Actions下面的General
    4. 点击页面中间的Allow all actions and rausabel workflows
    5. 点击save保持设置

> 关于微信公众号测试平台的内容的说明：
>  1. AppID和appSecret在微信公众号测试平台网站最上方
>  2. 模板ID在添加模板后生成
>  3. 用户ID在扫码关注后生产


`AllConfig`要改的部分，如下：
```java
public class AllConfig {
    public static final String VxAppId = "微信的APPID";
    public static final String VxAppSecret = "微信的密钥";
    public static final String WeatherKey = "高德地图key";
    private static void init(){
        //如果要多个人的话，就复制这个一遍，然后填写里面的内容。这里默认两个人,大伙应该是两个人吧（笑）
        userList.add(getUser(
                "你自己扫码后的微信号",
                "她的名字",
                new BirthDay(1999,8,11,false),  //最后的这个true/false，如果是过公历生日就写false，如果是过农历生日写true
                new BirthDay(1999,2,15,true), //最后的这个true/false，如果是过公历生日就写false，如果是过农历生日写true
                LocalDate.of(2020,7,8),
                "江苏省南京市玄武区",
                "南京",
                "微信消息模板ID"));

        userList.add(getUser(
                "你自己扫码后的微信号",
                "她的名字",
                new BirthDay(1999,2,15,true),  //最后的这个true/false，如果是过公历生日就写false，如果是过农历生日写true
                new BirthDay(1999,8,11,false), //最后的这个true/false，如果是过公历生日就写false，如果是过农历生日写true
                LocalDate.of(2020,7,8),
                "江苏省南京市玄武区",
                "南京",
                "微信消息模板ID"));

    }

```

微信消息模板，复制到[微信公众号测试平台](https://mp.weixin.qq.com/debug/cgi-bin/sandboxinfo?action=showinfo&t=sandbox/index)的模板里面
```
      	{{userName.DATA}}，
      	今天是我们在一起的{{holdDay.DATA}}天
      	你的生日还有{{yourBirthDay.DATA}}天
      	我的生日还有{{myBirthDay.DATA}}天
      	距离我们的下一次纪念还有{{loveDay.DATA}}天
        今天白天{{weatherDay.DATA}}，温度{{temperatureDay.DATA}}℃
      	今天晚上{{weatherNight.DATA}}，温度{{temperatureNight.DATA}}℃

        最后，开心每一天！
```


对你有帮助的话，记得一健三连支持一下哦~
