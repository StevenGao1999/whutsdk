package com.ymlyj666.sdk.whutsdk.id;

/**
 * Created by 19110 on 2016/12/31.
 */

import com.ymlyj666.sdk.whutsdk.util.HttpUtil;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 武汉理工电子身份证
 */
public class WHUTID {
    private static final Logger log = LoggerFactory.getLogger(WHUTID.class);

    private int tryTimes;//网络错误重试次数
    private String uid;//账号
    private String pswd;//密码

    private boolean loggedIn;//是否成功登陆
    private Map<String, String> loginCookies;

    public WHUTID(String uid, String pswd) {
        this(uid, pswd, 10);
    }

    public WHUTID(String uid, String pswd, int tryTimes) {
        this.tryTimes = tryTimes;
        this.uid = uid;
        this.pswd = pswd;
        loggedIn = false;
    }

    /**
     * continueurl=&requestType=2&userExtendBean.ex_key=&userBaseBean.cardno=237375&userSafetyBean.password=72767911whut&x=393&y=22
     *
     * @return
     * @throws IOException
     */

    public boolean login() throws IOException {
        if (loggedIn) {
            return true;
        }

        Connection connection = Jsoup.connect("http://sso.whut.edu.cn:8080/ias/login")
                .data("requestType", "2")
                .data("sysid", "portal")
                .data("userBaseBean.cardno", uid)
                .data("userSafetyBean.password", pswd);
        Connection.Response response = HttpUtil.tryForResponse(connection, tryTimes);
        Document document = response.parse();
        Element ssoticketidElement = document.getElementById("ssoticketid");
        if (ssoticketidElement != null) {
            String ssoticketid = ssoticketidElement.attr("value");
            log.info("ssoticketid = " + ssoticketid);

            connection = Jsoup.connect("http://my.whut.edu.cn/c/portal/login")
                    .data("ssoticketid", ssoticketid);
            response = HttpUtil.tryForResponse(connection, tryTimes);
            log.info(response.body());

            loginCookies = response.cookies();
            loggedIn = true;

        }
        return loggedIn;
    }
}
