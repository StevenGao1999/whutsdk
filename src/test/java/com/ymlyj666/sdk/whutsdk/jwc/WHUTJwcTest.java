package com.ymlyj666.sdk.whutsdk.jwc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ymlyj666.sdk.whutsdk.jwc.model.BasicStudentInfo;
import com.ymlyj666.sdk.whutsdk.jwc.model.Course;
import com.ymlyj666.sdk.whutsdk.jwc.model.Score;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * Created by 19110 on 2016/12/31.
 */
public class WHUTJwcTest {

    private static final Logger log = LoggerFactory.getLogger(WHUTJwcTest.class);

    private WHUTJwc whutJwc;

    @Before
    public void setup() {
        whutJwc = new WHUTJwc("0121410870416", "???");
        log.trace("setup()");
    }

    @Test
    @Before
    public void login() throws IOException {
        boolean login = whutJwc.login();
        if (login) {
            log.info("success");
        } else {
            log.error("fail");
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void getCourses() throws IOException {
        Course[][] courses = whutJwc.getCourses();
        String s1 = JSON.toJSONString(courses, SerializerFeature.PrettyFormat);
        log.info(s1);

        courses = whutJwc.getCourses("2016-2017-1");
        String s2 = JSON.toJSONString(courses, SerializerFeature.PrettyFormat);
        log.info(s2);

        Assert.assertNotEquals(s1, s2);

        courses = whutJwc.getCourses(2016, 2017, 1);
        String s3 = JSON.toJSONString(courses, SerializerFeature.PrettyFormat);
        log.info(s3);

        Assert.assertEquals(s2, s3);

        whutJwc.getCourses(1997, 1997, 1);
        whutJwc.getCourses(2016, 2017, 3);


    }

    @Test
    public void getScores() throws IOException {
        List<Score> scores = whutJwc.getScores();
        String s = JSON.toJSONString(scores, SerializerFeature.PrettyFormat);
        log.info(s);
    }

    @Test
    public void getBasicStudentInfo() throws IOException{
        BasicStudentInfo basicStudentInfo = whutJwc.getBasicStudent();
        String s = JSON.toJSONString(basicStudentInfo, SerializerFeature.PrettyFormat);
        log.info(s);
    }


}
