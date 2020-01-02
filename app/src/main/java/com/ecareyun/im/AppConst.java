package com.ecareyun.im;


public class AppConst {
    public static final String TEST_SERVER_BASE_ADDRESS = "http://192.168.31.116:8000";//本地服务器地址
    public static String SERVER_BASE_ADDRESS = "";//生产服务器地址
    public static boolean isTestServer = true;//当前连接服务器是否为测试环境
    public static String DB_NAME = "YouTime.db";//数据库名称
    public static String ALLCOK_SERVER = "http://dev.ytime365.com/alloc/outer";//allock服务器
    public static String OSS_CONFIG = TEST_SERVER_BASE_ADDRESS + "/member/generateSign";//oss签名配置
    /**
     * 测试环境桶名：youtime-test
     * 域名：youtime-test.oss-cn-beijing.aliyuncs.com
     * <p>
     * 灰度环境桶名：youtime-dev
     * 域名：youtime-dev.oss-cn-beijing.aliyuncs.com
     * <p>
     * 现网环境桶名：public-tbank
     * 域名：public-tbank.oss-cn-beijing.aliyuncs.com
     */
    public static String OSS_TNAME = "youtime-test";
    public static String BASE_URL_OSS = "https://youtime-test.oss-cn-beijing.aliyuncs.com/";//创世纪地址
}
