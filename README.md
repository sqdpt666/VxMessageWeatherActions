# VxMessageActions
微信消息推送Gitub Actions版本
> 注意
> actions上设置的时间并不是准时的。
> 比如设置8.08，系统会帮你在8.08的时候进行执行排队,轮到你才会执行。
> 人多的时候可能会排队1个小时左右，甚至更久。所以通常不要设置的热门时间点，如每个小时的整点。

1. 复制本项目到你的仓库，设置为私有项目
2. 修改`VxUtil`和`MessageService`中对应的地方替换成你自己从[微信公众号测试平台](https://mp.weixin.qq.com/debug/cgi-bin/sandboxinfo?action=showinfo&t=sandbox/index)
获取的内容即可
3. 如果项目上方没有Actions,就要手动开启Actions，步骤为：
    1. 点击项目上方的Settings
    2. 点击页面左边Actions
    3. 点击Actions下面的General
    4. 点击页面中间的Allow all actions and rausabel workflows
    5. 点击save保持设置

> 关于微信公众号测试平台的内容的说明：
>  1. AppID和appSecret在微信公众号测试平台网站最上方
>  2. 模板ID在添加模板后生成
>  3. 用户ID在扫码关注后生产


在`VxUtil`中需要修改的部分
```java

public class VxUtil {

    private static final String AppID = "你的AppID";

    private static final String appSecret= "你的appSecret";
    
```    


在`MessageService`中需要修改的部分

```java
  public void sendMessage(){
        VxMessageDto dto = new VxMessageDto();
        dto.setTemplate_id("修改成你的模板ID");  //修改成你的模板ID
        dto.setTouser("修改成你的用户ID"); //修改成你的用户ID
        HashMap<String, DataInfo> map = new HashMap<>();
        setMap(map,"userName","改成她的名字","#FFCCCC"); //改成她的名字
        setAndSend(dto,map);
    }
    
   private void setAndSend(VxMessageDto dto,HashMap<String, DataInfo> map){
        setMap(map,"holdDay", DateUtil.passDay(2020,7,8),"#FFCCCC"); //改成你在一起的时间
        setMap(map,"yourBirthDay",DateUtil.getNextBirthDay(8,11),"#FFCCCC"); //改成她的生日
        setMap(map,"myBirthDay", DateUtil.getNextChineseBirthDay(2,15),"#FFCCCC"); //改成你的生日
        setMap(map,"loveDay",DateUtil.getNextBirthDay(7,8),"#FFCCCC"); //改成你在一起的时间
        dto.setData(map);
        String message = JSONUtil.toJsonStr(dto);
        VxUtil.sendMessage(message);
    }  

```

微信消息模板，复制到[微信公众号测试平台](https://mp.weixin.qq.com/debug/cgi-bin/sandboxinfo?action=showinfo&t=sandbox/index)的模板里面
```
      	{{userName.DATA}}，
      	今天是我们在一起的{{holdDay.DATA}}天
      	你的生日还有{{yourBirthDay.DATA}}天
      	我的生日还有{{myBirthDay.DATA}}天
      	距离我们的下一次纪念还有{{loveDay.DATA}}天

        最后，开心每一天！
```


对你有帮助的话，记得一健三连支持一下哦~
