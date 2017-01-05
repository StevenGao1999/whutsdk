package com.ymlyj666.sdk.whutsdk.id;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by 19110 on 2017/1/5.
 */
public class WHUTIDTest {
    private static final Logger log = LoggerFactory.getLogger(WHUTIDTest.class);


    private WHUTID whutid;

    @Before
    public void setup() {
        whutid = new WHUTID("237375", "???");
    }

    @Test
    public void login() throws IOException {
        boolean loggedIn = whutid.login();
        log.info("loggedIn = " + loggedIn);
    }

}
