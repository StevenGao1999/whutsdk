#WHUT SDK     
武汉理工大学相关网站SDK
- 教务处
- 理工电子身份证

作者：杨明亮  
邮箱：1911045919@qq.com  
有问题欢迎邮件联系我。

##使用说明     
1. 导入本项目bin目录中的*whutsdk-${版本号}.jar*
2. 导入项目依赖:<br>
<pre>compile group: 'org.jsoup', name: 'jsoup', version: '1.10.1'
compile group: 'org.slf4j', name: 'slf4j-api', version: '1.7.22'</pre>
3. 编写代码。

##代码示例（更多代码细节参照单元测试)     
###教务处     
<!--lang:java-->
    WHUTJwc whutJwc = new WHUTJwc("学号", "密码");
    if (whutJwc.login()){
        //success
        //获取学生基本信息
        BasicStudentInfo basicStudentInfo = whutJwc.getBasicStudentInfo();
        //获取课表
        Course[][] courses = whutJwc.getCourses();
        //获取成绩
        List<Score> scores = whutJwc.getScores();
        //获取学分统计（绩点，排名，学分状态）
        XFTJ xftj = whutjwc.getXFTJ();
        //other codes
        
    }else{
        //error codes here
    }
###理工电子身份证     
<!--lang:java-->
    WHUTID whutId = new WHUTJwc("校园卡号", "密码");
    if (whutId.login()){
        //success
        
    }else{
        //error codes here
    }

##其它     
非Java环境，欢迎使用 *武汉理工大学相关网站API* :   
https://github.com/ymlgithub/whutapi