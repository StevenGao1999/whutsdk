#WHUT SDK
武汉理工大学相关网站SDK
- 教务处
- 理工电子身份证

##使用说明
1. 导入本项目bin目录中的whutsdk-${版本号}.jar
2. 导入项目依赖
>     compile group: 'org.jsoup', name: 'jsoup', version: '1.10.1'
>     compile group: 'org.slf4j', name: 'slf4j-api', version: '1.7.22'
3. 编写代码。

##代码示例（更多代码细节参照单元测试)
###教务处
<!--lang:java-->
    WHUTJwc whutJwc = new WHUTJwc("学号", "密码");
    if (whutJwc.login()){
        //success
        //获取学生基本信息
        BasicStudentInfo basicStudentInfo = whutJwc.getBasicStudent();
        //获取课表
        Course[][] courses = whutJwc.getCourses();
        //获取历史成绩
        List<Score> scores = whutJwc.getScores();
        //other codes
        
    }else{
        //error codes here
    }
###理工电子身份证

