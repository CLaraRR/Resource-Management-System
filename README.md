# Resource-Management-System
#### 教学资源管理系统，By Java

平台是eclipse+mysql，服务端是用最基本的jsp/servlet写的，不涉及到SSH框架，前端则是套用一个简单的模板，适合刚开始接触J2EE的初学者学习

列出一些注意事项，这些是我这段时间收到的一些问题的解决方法，如果遇到这些问题的话可以参考一下我给出的解决方案，如果还不能解决的话就继续百度吧

1、运行前请先自行在mysql中新建一个名为resourcemanagementsystem的数据库，然后导入WebRoot目录下的sql文件

2、运行前请修改DBBean.java里面的password为你的数据库密码！（这个问题不要再问我了）

3、因为我用的eclipse是mars版本，很久以前做的项目了，现在的eclipse做J2EE项目的话WebRoot变成了WebContent,所以请注意在使用之前把WebRoot重命名为WebContent

4、如果jsp文件有红叉叉且打开文件后标红的地方提示说Multiple annotations found at this line: 	- The superclass "javax.servlet.http.HttpServlet" was not found on the Java Build Path的话，解决方案在这https://blog.csdn.net/github_37473774/article/details/71524450

5、如果运行项目的时候弹出提示框说Server Tomcat v8.5 Server at localhost failed to start.那么就要注意查看一下build path里面的jar包是不是有冲突

6、这个项目有一个很诡异的问题我现在也没有得到很好的解决，就是当我试图在admin的main.jsp点击添加用户按钮跳转到addUser.jsp的时候，如果是在eclipse内置的浏览器打开网页的话跳转就没有问题，如果是在外部浏览器如谷歌浏览器跳转的话就会出现路径错误

7、如果src目录下的文件标红，那么要注意看对应的jar包导进去了没，还有把项目添加到tomcat下


暂时只收集到这些问题，以后还会继续更新，如果报错的话请先自己尝试解决，不要什么（低级）错误都发邮件给我，很多情况是自己电脑的环境配置出了问题，或者是jdk版本出问题等，这些问题是可以自行上网根据自己的情况搜索方案解决的，若是代码错误请邮件ningrun4228@163.com，十分感谢
