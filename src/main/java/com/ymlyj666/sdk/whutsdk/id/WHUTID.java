package com.ymlyj666.sdk.whutsdk.id;

/**
 * Created by 19110 on 2016/12/31.
 */

/**
 * 武汉理工电子身份证
 */
public class WHUTID {

    private int tryTimes;//网络错误重试次数
    private String uid;//账号
    private String pswd;//密码

    public WHUTID(String uid, String pswd) {
        this(uid, pswd, 10);
    }

    public WHUTID(String uid, String pswd, int tryTimes) {
        this.tryTimes = tryTimes;
        this.uid = uid;
        this.pswd = pswd;
    }
}
