# VxMessageWeatherActions
**请仔细阅读本文档哦~**

微信消息推送Gitub Actions版本+天气预报，究极简单版2.0

|                      | 究极简单版1.0                                                | 究极简单版2.0                       |
| -------------------- | ------------------------------------------------------------ | ----------------------------------- |
| 修改哪些文件？       | `VxUtil` `WeatherUtil` `MessageService` 共3个                | `AllConfig` 共一个                  |
| 支持多个人?          | 支持，但是对0基础的人不友好                                  | 支持，只需要复制粘贴，对0基础也友好 |
| 是什么时区？         | 格林标准时间                                                 | 北京时间?                           |
| 是否能修改模板？     | 对于0基础的同学只能修改模板中的中文部分，{{xxx}}不能修改。<br />对于有基础的同学可通过修改代码达到展示更多**动态内容的**目的。（注意：动态内容指要变化的内容，如生日倒计时，即每个{{xxx}}都是一个动态内容） | 同1.0。本文档后面也介绍修改的方法                              |
| 为什么没有收到消息？ | 1. 检查一下配置好了嘛？<br />2. 复制粘贴的时候前后有没有多空格？<br />3. github actions有没有打开？<br />4. actions可能在排队，会排队1个小时左右，甚至更久<br />5. 一般都是你自己没弄好，但是你实在检查不出来，欢迎B站去留言 | 同1.0                               |

> 注意
> 
> actions上设置的时间并不是准时的。
> 
> 比如设置8.08，系统会帮你在8.08的时候进行执行排队,轮到你才会执行。
> 
> 
> 人多的时候可能会排队1个小时左右，甚至更久。所以通常不要设置的热门时间点，如每个小时的整点。
>


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
                "这个人扫码后的微信号",//扫码关注你的测试号以后，测试平台会出现TA的微信号
                "这个人的称呼",//咋称呼这个人
                new BirthDay(1999,8,11,false),  //这个人的生日，最后的这个true/false，如果是过公历生日就写false，如果是过农历生日写true
                new BirthDay(1999,2,15,true), //这个人对象的生日，最后的这个true/false，如果是过公历生日就写false，如果是过农历生日写true
                LocalDate.of(2020,7,8),//这个人的纪念日
                "江苏省南京市玄武区",//这个人的详细地址
                "南京",//这个人在的城市
                "微信消息模板ID"));//要给这个人发送的模板ID

        userList.add(getUser(
                "这个人扫码后的微信号",
                "这个人的名字",
                new BirthDay(1999,2,15,true),  //这个人的生日，最后的这个true/false，如果是过公历生日就写false，如果是过农历生日写true
                new BirthDay(1999,8,11,false), //这个人对象的生日，最后的这个true/false，如果是过公历生日就写false，如果是过农历生日写true
                LocalDate.of(2020,7,8),//这个人的纪念日
                "江苏省南京市玄武区",//这个人的详细地址
                "南京",//这个人在的城市
                "微信消息模板ID"));//要给这个人发送的模板ID

    }

```

微信消息模板，复制到[微信公众号测试平台](https://mp.weixin.qq.com/debug/cgi-bin/sandboxinfo?action=showinfo&t=sandbox/index)的模板里面
友情提示：复制到微信平台里面后，格式可能会乱哦，记得整理一下^_^
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

如果你要自定义模板的动态内容，那么你就需要修改`MessageService`中的`sendMessage`方法

在里面加一个`setMap(map,"命名","值","颜色十六进制")`，**这个命名写英文**
这个命名就是上面消息模板的 {{命名.DATA}}
```java
public class MessageService {
    public void sendMessage(User user){
        //省略前面的内容
      
        //加一个setMap，比如：
        setMap(map,"myself", "hi! 我是自定义的内容","#FFCCCC");
        
       
        //省略后面的内容
    }
```
然后你就把模板改成
```
{{userName.DATA}}，
今天是我们在一起的{{holdDay.DATA}}天
你的生日还有{{yourBirthDay.DATA}}天
我的生日还有{{myBirthDay.DATA}}天
距离我们的下一次纪念还有{{loveDay.DATA}}天
今天白天{{weatherDay.DATA}}，温度{{temperatureDay.DATA}}℃
今天晚上{{weatherNight.DATA}}，温度{{temperatureNight.DATA}}℃
我自定义的内容是{{myself.DATA}}

最后，开心每一天！
```

对你有帮助的话，记得一健三连支持一下哦~
