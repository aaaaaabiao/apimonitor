## 前言
apimonitor是一个api接口监控项目，可以模拟探测http接口、http页面，通过请求耗时和响应结果来判断系统接口的可用性和正确性。

## 实现功能
- 支持单个API和多个API调用链的探测。
- 支持页面探测，可以模拟页面操作过程，比如打开登陆页面，登陆，跳转到登陆后页面；
- 支持HTTP和HTTPS，请求类型可以是GET，POST，HEADER，PUT，DELETE
- 支持通用的HTTP认证，比如BASIC认证，COOKIES认证等
- 调用API可以设置请求头部，请求参数。在API调用链过程中，上一个API的返回结果可以作为下一个API的请求参数；
- 可以自定义探测结果判定，比如接口返回状态码为200，返回内容包括/不包括exception等；
- 可以自定义监控频率，比如30分钟/次，1小时/次
- 监控日志定期清理
- 支持导入postman脚本，可以大幅度减低配置复杂页面监控的工作量。使用postman工具捕捉HTTP请求，生成postman脚本，然后导入到监控系统。


## 使用技术
- 技术框架：maven、Spring Boot、Mybatis、SpringMVC
- 技术组件：HttpClient、Quartz、fastjson、dom4j
- UI技术：sitemesh、thymeleaf、bootstrap、adminlte

## 运行环境
- jdk8
- tomcat
- mysql

## 如何运行
首先，需要准备好数据库
1. 创建mysql数据库，数据库名为`apimonitor`；
2. 执行db/db.sql脚本，初始化表；

然后，你可以选择使用jar包运行，或者直接运行源码。
#### 运行jar包
1. jar包在dist/apimonitor-0.0.1-SNAPSHOT.jar；
2. 打开jar包，修改/BOOT-INF/classes目录下的application.properties文件，将url、username、password改为你自己的；
```
spring.datasource.url=jdbc:mysql://192.168.171.238:3306/apimonitor?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true
spring.datasource.username=root
spring.datasource.password=123456
```
3. 直接使用命令`java -jar apimonitor-0.0.1-SNAPSHOT.jar`启动项目（你的系统必须安装了jdk1.8并配置了环境变量）；

#### 运行源码
1. 以eclipse为例，将apimonitor作为maven项目导入（eclipse要提前安装配置maven）
2. 修改src/main/resources目录下的application.properties文件，将url、username、password改为你自己的；
```
spring.datasource.url=jdbc:mysql://192.168.171.238:3306/apimonitor?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true
spring.datasource.username=root
spring.datasource.password=123456
```
3. 运行com.ecar.apm.MainApplication类，项目将启动

## 系统界面
项目启动后，直接在浏览器输入http://localhost:8080 ，就可以访问。

系统截图如下：
![输入图片说明](https://images.gitee.com/uploads/images/2018/0830/092447_0d0e5881_1908188.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2018/0830/092518_1a041766_1908188.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2018/0830/092541_ebfdfe7f_1908188.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2018/0830/092800_987ee579_1908188.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2018/0830/092555_8ad14e3a_1908188.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2018/0830/092949_51757304_1908188.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2018/0830/092907_9715767c_1908188.png "屏幕截图.png")

## 感谢
- 项目部分代码借鉴gitee上面的开源项目[HeartBeat](https://demo.gitee.com/mkk/HeartBeat)，希望开源能传递下去。
- 功能设计灵感来源于著名的apm系统-监控宝

## 联系
请给我留言[jection](https://demo.gitee.com/jection)

## 版权
Apache v2 License


