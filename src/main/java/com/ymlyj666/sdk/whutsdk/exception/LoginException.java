package com.ymlyj666.sdk.whutsdk.exception;

import java.io.IOException;

/**
 * Created by 19110 on 2016/12/31.
 */
public class LoginException extends IOException {
    public LoginException() {
        super("Login failed !");
    }
}
