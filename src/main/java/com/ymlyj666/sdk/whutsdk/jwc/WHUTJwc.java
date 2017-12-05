package com.ymlyj666.sdk.whutsdk.jwc;

/**
 * Created by 19110 on 2016/12/31.
 */

import com.ymlyj666.sdk.whutsdk.exception.LoginException;
import com.ymlyj666.sdk.whutsdk.jwc.model.BasicStudentInfo;
import com.ymlyj666.sdk.whutsdk.jwc.model.Course;
import com.ymlyj666.sdk.whutsdk.jwc.model.Score;
import com.ymlyj666.sdk.whutsdk.jwc.model.XFTJ;
import com.ymlyj666.sdk.whutsdk.util.HttpUtil;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 武汉理工教务处
 */
public class WHUTJwc {
    private static final Logger log = LoggerFactory.getLogger(WHUTJwc.class);

    private int tryTimes;//网络错误重试次数
    private String uid;//账号
    private String pswd;//密码

    private boolean loggedIn;//是否成功登陆
    private Map<String, String> loginCookies;
    private Map<String, Map<String, String>> cookiesCache;

    public WHUTJwc(String uid, String pswd) {
        this(uid, pswd, 10);
    }

    public WHUTJwc(String uid, String pswd, int tryTimes) {
        this.tryTimes = tryTimes;
        this.uid = uid;
        this.pswd = pswd;
        loggedIn = false;
        cookiesCache = new HashMap<String, Map<String, String>>();
    }

    /**
     * 执行登录操作
     *
     * @return 是否登录成功
     * @throws IOException
     */
    public boolean login() throws IOException {
        if (loggedIn) {
            return true;
        }
        Connection connection = Jsoup.connect("http://sso.jwc.whut.edu.cn/Certification//login.do")
                .method(Connection.Method.POST)
                .data("userName", uid)
                .data("password", pswd)
                .data("type", "xs");
        Connection.Response response = HttpUtil.tryForResponse(connection, tryTimes);

        String body = response.body();
        log.trace(body);

        if (!body.contains("loginForm")) {
            loginCookies = response.cookies();
            cookiesCache.clear();
            loggedIn = true;
        }

        return loggedIn;
    }

    /**
     * 获取默认课表
     *
     * @return
     * @throws IOException
     */
    public Course[][] getCourses() throws IOException {
        return getCourses("");
    }

    /**
     * 根据学年查询课表
     *
     * @param schoolYearFrom 学年起始年
     * @param schoolYearTo   学年终止年
     * @param term           学期 （1、2）
     * @return
     */
    public Course[][] getCourses(int schoolYearFrom, int schoolYearTo, int term) throws IOException, IllegalArgumentException {
        if (schoolYearTo - schoolYearFrom != 1 || schoolYearFrom < 2010 || schoolYearFrom > 2100) {
            throw new IllegalArgumentException("The value of ( schoolYearTo - schoolYearFrom ) should be equal to 1 and schoolYearFrom should be between 2010 and 2100.");
        }
        if (term != 1 && term != 2) {
            throw new IllegalArgumentException("The term should be 1 or 2.");
        }
        return getCourses(schoolYearFrom + "-" + schoolYearTo + "-" + term);
    }

    /**
     * 查询指定学年学期的课表
     *
     * @param xnxq 学年学期，例如： 2016-2017-1
     * @return
     * @throws IOException
     */
    public Course[][] getCourses(String xnxq) throws IOException {
        Document document = connectWithCertification("http://202.114.90.180/Course/grkbList.do?xnxq=" + xnxq);
        Course[][] courses = new Course[7][5];
        for (int i = 1; i < 8; i++) {
            for (int j = 0; j < 5; j++) {
                Element element = document.getElementById("t" + j * 2 + i);
                String h = element.html().trim();
                if (!h.equals("")) {
                    courses[i - 1][j] = new Course(element.html());
                }
            }
        }
        return courses;
    }

    /**
     * 获取成绩
     *
     * @return 成绩列表
     * @throws IOException
     */
    public List<Score> getScores() throws IOException {
        Document document = connectWithCertification("http://202.114.90.180/Score/lscjList.do?pageNum=1&numPerPage=10000");
//        log.info(document.toString());
        Elements elements = document.getElementsByAttributeValue("target", "sid_cj_id");
        List<Score> scores = new ArrayList<Score>();
        for (Element element : elements) {
            Score score = new Score();
            int i = 0;
            score.setTerm(element.child(i++).html());
            score.setCourseId(element.child(i++).html());
            score.setCourseName(element.child(i++).html());
            score.setCourseType(element.child(i++).html());
            score.setGraduateCourseType(element.child(i++).html());
            score.setCredit(element.child(i++).html());
            score.setScore(element.child(i++).html());
            score.setScoreVerifyType(element.child(i++).html());

            score.setMaxScore(element.child(i++).html());
            score.setFirstScore(element.child(i++).html());

            score.setExamStatus(element.child(i++).html());
            score.setScoreType(element.child(i++).html());
            score.setIsRevamp(element.child(i++).html());
            score.setGp(element.child(i++).html());
            scores.add(score);
        }
        return scores;
    }

    /**
     * 获取学分统计
     *
     * @return 学分统计页面
     * @throws IOException
     */
    public XFTJ getXFTJ() throws IOException {
        Document document = connectWithCertification("http://202.114.90.180/Score/xftjList.do");
        Elements inputs = document.getElementsByTag("input");
        Elements rows = document.select("tbody tr");
        XFTJ xftj = new XFTJ();
        xftj.setClassRank(inputs.get(0).attr("value"));
        xftj.setSchoolYearGPA(inputs.get(1).attr("value"));
        xftj.setFacultyRank(inputs.get(2).attr("value"));
        xftj.setTotalGPA(inputs.get(3).attr("value"));
        List<XFTJ.CreditStatus> creditStatuses = new ArrayList<XFTJ.CreditStatus>();
        for (Element row : rows) {
            Elements cols = row.getElementsByTag("td");
            XFTJ.CreditStatus status = new XFTJ.CreditStatus();
            status.setCourseNature(cols.get(0).html());
            status.setRequired(cols.get(1).html());
            status.setAcquired(cols.get(2).html());
            creditStatuses.add(status);
        }
        xftj.setCreditStatuses(creditStatuses);
        return xftj;
    }

    /**
     * 获取学生基本信息
     *
     * @return
     * @throws IOException
     */
    public BasicStudentInfo getBasicStudentInfo() throws IOException {
        Document document = connectWithCertification("http://202.114.90.180/SchoolRoll/xjxxList.do");
        Elements inputs = document.getElementsByTag("input");
        BasicStudentInfo studentInfo = new BasicStudentInfo();
        studentInfo.setUid(uid);
        studentInfo.setName(inputs.get(2).attr("value"));
        studentInfo.setSex(inputs.get(3).attr("value"));
        studentInfo.setIdcard(inputs.get(9).attr("value"));
        studentInfo.setNation(inputs.get(5).attr("value"));
        studentInfo.setFaculty(inputs.get(17).attr("value"));

        studentInfo.setMajor(inputs.get(21).attr("value"));
        studentInfo.setEduLevel(inputs.get(14).attr("value"));
        studentInfo.setEnterYear(inputs.get(18).attr("value"));
        studentInfo.setSchoolZone(inputs.get(22).attr("value"));

        studentInfo.setClazz(inputs.get(20).attr("value"));
        studentInfo.setLengthOfSchooling(inputs.get(23).attr("value"));
        studentInfo.setProvince(inputs.get(10).attr("value"));
        studentInfo.setCounty(inputs.get(13).attr("value"));
        return studentInfo;
    }


    /**
     * 登录认证系统
     *
     * @param systemURL 被认证系统的URL
     * @return 认证后产生的cookies
     * @throws IOException
     */
    private Map<String, String> certificate(String systemURL) throws IOException {
        if (!loggedIn) {
            throw new LoginException();
        }

        if (cookiesCache.containsKey(systemURL)) {
            return cookiesCache.get(systemURL);
        }

        Connection connection = Jsoup.connect(systemURL)
                .followRedirects(false)
                .cookies(loginCookies);
        Connection.Response response = HttpUtil.tryForResponse(connection, tryTimes);
        Map<String, String> cookies = response.cookies();
        String location = response.header("Location");
        log.debug("Location = " + location);

        // http://sso.jwc.whut.edu.cn/certification.do
        connection = Jsoup.connect(location)
                .followRedirects(false)
                .cookies(loginCookies);
        response = HttpUtil.tryForResponse(connection, tryTimes);
        location = response.header("Location");
        log.debug("Location = " + location);

        // http://sso.jwc.whut.edu.cn/Certification//certification.do
        connection = Jsoup.connect(location)
                .followRedirects(false)
                .cookies(loginCookies);
        response = HttpUtil.tryForResponse(connection, tryTimes);
        location = response.header("Location");
        log.debug("Location = " + location);

        // {systemURL}/login.do
        connection = Jsoup.connect(location)
                .followRedirects(false)
                .cookies(cookies);
        response = HttpUtil.tryForResponse(connection, tryTimes);
//        String body = response.body();
//        log.trace(body);

        if (systemURL.endsWith("/Score/")) {
//            log.info(response.body());
            Document document = response.parse();
            String url = document.select("[href*=snkey]").attr("href");
            String snkey = url.substring(url.lastIndexOf("=") + 1);
            cookies.put("snkey", snkey);
        }

        cookiesCache.put(systemURL, cookies);
        return cookies;
    }

    /**
     * 认证后连接
     *
     * @param url
     * @return 连接结果
     * @throws IOException
     */
    private Document connectWithCertification(String url) throws IOException {
        Connection connection = Jsoup.connect(url);
        int pQuery = url.indexOf('?');
        if (pQuery > 0) {//去除Query部分
            url = url.substring(0, pQuery);
        }
        String systemURL = url.substring(0, url.lastIndexOf('/') + 1);
        log.info("systemURL=" + systemURL);
        Map<String, String> certCookies = certificate(systemURL);
        connection.cookies(certCookies);
        if (systemURL.endsWith("/Score/")) {
            connection.data("snkey", certCookies.get("snkey"));
        }
        Connection.Response response = HttpUtil.tryForResponse(connection, tryTimes);
//        log.info(response.body());
        return response.parse();
    }

    public String getUid() {
        return uid;
    }

}
