package com.ymlyj666.sdk.whutsdk.util;

import org.jsoup.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * Created by 19110 on 2016/12/31.
 */
public class HttpUtil {
    private static final Logger log = LoggerFactory.getLogger(HttpUtil.class);

    private HttpUtil() {
        throw new IllegalAccessError(this.getClass().getName() + " Can't be created!");
    }


    /**
     * 多次请求避免一次连接失败
     *
     * @param connection
     * @param tryTimes
     * @return
     * @throws IOException
     */
    public static Connection.Response tryForResponse(Connection connection, int tryTimes) throws IOException {
        Connection.Response response;
        for (int i = 0; i < tryTimes; i++) {
            response = connection.execute();

//            Map<String, String> cookies = response.cookies();
//            for (Map.Entry<String, String> entry : cookies.entrySet()) {
//                log.debug(entry.getKey() + "\t=\t" + entry.getValue());
//            }
//
//            Map<String, String> headers = response.headers();
//            for (Map.Entry<String, String> entry : headers.entrySet()) {
//                log.debug(entry.getKey() + "\t=\t" + entry.getValue());
//            }

            if (response.bodyAsBytes().length != 0 || response.statusCode() != 200) {
                return response;
            }
            log.info("Retrying……");
        }
        throw new IOException("Cannot connect to : " + connection.request().url());
    }

}
