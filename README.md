# 微信模板消息推送 2.6.0

+ [x] master模式

+ [x] key消息分裂

+ [x] 中英文周

+ [x] 随机API消息模式

+ [x] 随机字体颜色

+ [x] 额外消息自定义

+ [x] 完整双天气源（高德地图、和风天气）

+ [x] 历史上的今天数量自定义

效果图：
![092075d64a0aaf2440ed537851f9487](https://user-images.githubusercontent.com/56298636/231202157-6cf27e30-26ab-4fbb-a6f9-8e200e27a373.jpg)


## 快速开始
### 第一步：复制本项目到你的仓库，设置为私有项目
点击复制项目地址

![点击复制项目地址](https://user-images.githubusercontent.com/56298636/190580174-32b7c197-866f-4e94-b886-36b817e40b03.png)

右上角导入按钮

![image](https://user-images.githubusercontent.com/56298636/190580243-f0b4b8ef-9eb3-4ac2-ab5b-d48aa435e0e7.png)

完成导入

![image](https://user-images.githubusercontent.com/56298636/190580561-fb0cc938-6999-4430-aee2-1616362f6857.png)

### 第二步：开启Actions
如果项目上方没有Actions,就要手动开启Actions，步骤为：

1. 点击项目上方的Settings
2. 点击页面左边Actions
3. 点击Actions下面的General
4. 点击页面中间的Allow all actions and rausabel workflows
5. 点击save保持设置

![image](https://user-images.githubusercontent.com/56298636/190582555-5d576513-eac7-43fd-a248-c62d72b7a03a.png)

### 第三步：配置微信测试平台信息
只需要在`com.pt.vx.config.WechatConfig`中配置好`VxAppId`和`VxAppSecret`以及`用户信息`即可，此时你就拥有了一个基础的微信消息，你将可以使用<a href="#baseMb">基础模板</a>。

关于如何获取`VxAppId`和`VxAppSecret`以及`用户信息`，可以查看<a href="#vxTemplateInfo">微信测试号信息操作</a>

如果要使用更多内容，需要到`WeatherConfig`或者`MainCOnfig`中配置。

```java
public class WechatConfig {
    /**
     * 你的微信的APPID
     * appId
     */
    public static final String VxAppId = "appId";

    /**
     * 你的微信的密钥
     * appSecret
     */
    public static final String VxAppSecret = "appSecret";

    public static final List<User> userList = new ArrayList<>();

    static {
        userList.add(getUser(
                "这个人的微信号", //扫码关注你的测试号以后，测试平台会出现TA的微信号
                "模板ID", //要给这个人发送的模板ID
                "pt", //咋称呼这个人
                "江苏省南京市玄武区", //这个人的详细地址
                "南京", //这个人在的城市
                new BirthDay(1999,2,15,true,false,"pt生日快乐！！"),
                new BirthDay(1999,8,11,false,false,"生日快乐哦~~"),
                new BirthDay(2020,7,8,true,true),
                new BirthDay(2020,7,8,true,false,"周年快乐！！！")
        ));

        userList.add(getUser(
                "这个人扫码后的微信号",
                "微信消息模板ID",
                "这个人的称呼",
                "江苏省南京市玄武区",
                "南京",
                new BirthDay(1999,8,11,false,false,"生日快乐哦~~"),
                new BirthDay(1999,2,15,true,false,"pt生日快乐！！"),
                new BirthDay(2020,7,8,true,true),
                new BirthDay(2020,7,8,true,false,"周年快乐！！！")
        ));


    }
```

### 第四步：手动执行Action
当配置好前面的后，我们可以手动执行一次Action测试一下结果。
按下面的4个步骤来手动运行
![image](https://user-images.githubusercontent.com/56298636/190579413-042157f1-ad42-48bd-b84b-814dd6179edb.png)
稍等一下，1分钟左右如果收到了消息则证明成功了。
否则就失败了，需要查看失败的原因，进行<a href="#debug">问题排查</a>


## 更多内容

### 获取天气

到`WeatherConfig`中进行配置。

1. 开启天气功能，将`OPEN`设置为`true`
2. 获取对应的天气源的key并且填入`weatherSourceKey`，并且将天气源设置`weatherSourceType`设置成对应的天气源
3. 选择需要的天气类型`getWeatherType`

具体如何获取配置信息到<a href="gaode">高德地图信息获取</a>或者<a href="hefeng">和风天气信息获取</a>查看。



### 自定义模板

<span id="diy"></span>

如果要自定义模板，那么就需要到`KeyConfig`中29个内置的key查看需要的内容。

比如日出时间，new KeyDTO右边第一个参数是关键字key，第二个参数是颜色，第三个参数是是否开启

```java
	/**
     * 日出时间
     */
    public static final KeyDTO KEY_SUN_RISE = new KeyDTO("sunrise","#FFFFFF",true);
```

了解这个以后，就可以自定义模板了，然后将自定义的模板到微信测试平台添加即可。

```java
{{userName.DATA}}， 
{{date.DATA}} 周{{week.DATA}} 
今天日出的时间是{{sunrise.DATA}}
```



> 注意：对于日出（sunrise）和日落（sunset）两个key，只有切换天气源为和风天气才有。

### 排查问题
<span id="debug"></span>
遇到没有正常执行的情况，我们需要到Action中排查一下问题
1. 点击你出问题的那个·Java Ci With Maven· ，最上面的为最新的一次执行。
![第一步](https://user-images.githubusercontent.com/56298636/231198637-77e7c025-9f2b-460c-b9a0-fd5c0783271a.png)

2. 点击`build`
![第二步](https://user-images.githubusercontent.com/56298636/231198655-067b5ab0-b88c-4f92-87e9-aa2d6e7f271f.png)

3. 点击`Run Java`就可以看到执行的情况，就可以根据错误日志进行排查了。你可以自行百度错误的原因，或者B站私信我。
![第三步](https://user-images.githubusercontent.com/56298636/231198759-8d8debf8-4c37-4c55-9a32-6d1f59fe900a.png)



### 其他内容

到`MainConfig`中自行查看即可，注释写的很清楚。



## 信息获取

###  微信测试号信息获取

<span id="vxTemplateInfo" ></span>

[微信公众号测试平台](https://mp.weixin.qq.com/debug/cgi-bin/sandboxinfo?action=showinfo&t=sandbox/index)

    1. AppID和appSecret在微信公众号测试平台网站最上方

![image](https://user-images.githubusercontent.com/56298636/190580833-949247b1-2ac0-4399-8ec4-b94abbbed0ce.png)

    2. 模板ID在添加模板后生成

![image](https://user-images.githubusercontent.com/56298636/190581136-d03d102c-b668-47af-96f6-89e0a79e88c1.png)

    3. 用户ID在扫码关注后生产

![image](https://user-images.githubusercontent.com/56298636/190581072-9b14c1b3-6564-498e-8546-3b0b93bdeaed.png)

### 高德地图信息获取

<span id="gaode" ></span>

[高德地图开发者平台](https://lbs.amap.com/api/webservice/guide/create-project/get-key)

1. 创建一个新应用

![image](https://user-images.githubusercontent.com/56298636/190583327-dfae0cd2-9450-4b2b-8f3c-dbd0999959f0.png)

2.给应用起个名字

![image](https://user-images.githubusercontent.com/56298636/190583508-09e3c4d6-0063-4dab-9097-a167a594be38.png)

3. 新增一个key
   ![image](https://user-images.githubusercontent.com/56298636/190583577-0d804449-6cba-41f1-a169-f9c5af4c6bfd.png)

4. 配置一下
   ![image](https://user-images.githubusercontent.com/56298636/190583666-ab4fefa1-e560-46cf-acaa-addabbc748ea.png)

5. 获得key

![image](https://user-images.githubusercontent.com/56298636/190584096-2e34f62c-b4d5-4263-bc3e-24727422586d.png)



### 和风天气信息获取

<span id="hefeng" ></span>

可以到[控制台 | 和风天气](https://console.qweather.com/#/console)进行配置。
配置完后就能拿到key了，基本和高德地图一致。

![image](https://user-images.githubusercontent.com/56298636/197333335-bd7d15ae-71bf-46f9-ab06-409d93844ba9.png)



## 模板

这里我提供一些模板，你也可以<a href="#diy">自定模板</a>。

<span id="baseMb">基础模板</span>

```
{{userName.DATA}}， 
{{date.DATA}} 周{{week.DATA}} 
今天是我们在一起的{{birthDay3.DATA}}天 
你的生日还有{{birthDay.DATA}}天 
我的生日还有{{birthDay1.DATA}}天 
距离我们下一次纪念还有{{birthDay2.DATA}}天 
{{otherInfo.DATA}}{{otherInfoSplit.DATA}} 

{{randomInfo.DATA}}{{randomInfoSplit.DATA}}

最后，开心每一天！
```



有天气的模板

```
{{userName.DATA}}， 
{{date.DATA}} 周{{week.DATA}} 
今天是我们在一起的{{birthDay3.DATA}}天 
你的生日还有{{birthDay.DATA}}天 
我的生日还有{{birthDay1.DATA}}天 
距离我们下一次纪念还有{{birthDay2.DATA}}天 
今天白天{{weatherDay.DATA}}，温度{{temperatureDay.DATA}}℃ 
今天晚上{{weatherNight.DATA}}，温度{{temperatureNight.DATA}}℃ 
明天白天{{weatherDay1.DATA}}，温度{{temperatureDay1.DATA}}℃ 
明天晚上{{weatherNight1.DATA}}，温度{{temperatureNight1.DATA}}℃ 
{{otherInfo.DATA}}{{otherInfoSplit.DATA}}

{{randomInfo.DATA}}{{randomInfoSplit.DATA}}

最后，开心每一天！
```

