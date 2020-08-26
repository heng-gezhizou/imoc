package com.adtec.imoc.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantImocUtil implements InitializingBean {

    @Value("${huawei.imoc.akid}")
    private String akId;

    @Value("${huawei.imoc.akvalue}")
    private String akValue;

    @Value("${huawei.imoc.skid}")
    private String skId;

    @Value("${huawei.imoc.user}")
    private String user;

    @Value("${huawei.imoc.tenant}")
    private String tenant;

    public static String HUAWEI_IMOC_AKID;
    public static String HUAWEI_IMOC_AKVALUE;
    public static String HUAWEI_IMOC_SKID;
    public static String HUAWEI_IMOC_USER;
    public static String HUAWEI_IMOC_TENANT;


    @Override
    public void afterPropertiesSet() throws Exception {
        HUAWEI_IMOC_AKID = akId;
        HUAWEI_IMOC_AKVALUE = akValue;
        HUAWEI_IMOC_SKID = skId;
        HUAWEI_IMOC_USER = user;
        HUAWEI_IMOC_TENANT = tenant;
    }
}
