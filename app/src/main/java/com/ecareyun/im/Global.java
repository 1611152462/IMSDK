package com.ecareyun.im;

import android.content.Context;
import android.os.Bundle;

public class Global {
    public static final int SUCCESS_CODE = 200;    //网络请求成功返回码

    public static final String APP_START_UP_COUNT = "start_up";  //启动次数
    public static final String APP_IMEI = "imei";//设备唯一标识
    public static final String USERID = "userId";//本地用户ID
    public static final String USER_NOTES = "notes";//备注

    public static final String KEY_TITLE = "title";//标题
    public static final String KEY_VALUE = "valueBean";//价值详情
    public static final String KEY_LOCATION = "location";//地址
    public static final String RESULT_CONTENT = "resultContent";//返回内容
    public static final String SELECT_LABEL = "selectLabel";//标签
    public static final String KEY_CONTENT = "requestContent";
    public static final String KEY_USER_SEX = "userSex";
    public static final String KEY_USER_NAME = "userName";
    public static final String KEY_USER_INTRO = "userIntro";
    public static final String USER_NAME = "loginUserName";
    public static final String KEY_USER_AGE = "uAge";
    public static final String KEY_USER_TYPE = "userType";
    public static final String KEY_USER_CONST = "uConstellation";
    public static final String KEY_LABEL = "userLabel";
    public static final String IS_SINGLE = "isSingle";//标签是否单选
    public static final String KEY_STATUS = "status";//是否已设置支付密码
    public static final String KEY_MOBILE = "mobile";//手机号
    public static final String KEY_ORDER_INFO = "orderInfo";//订单详情
    public static final String WEB_URL = "web";//web链接
    public static final String ORDER_ID = "orderId";
    public static final String KEY_OBJ = "versionInfo";//版本信息

    public static final String KEY_SEARCH_TYPE = "searchType";//通讯类搜索关系类型

    public static final String LOCATION_FILENAME = "locationFileName";//发送位置截图文件名
    public static final String LOCATION = "location";//发送位置坐标  序列化实体
    public static final String LOCATION_NAME = "locationName";//发送位置  位置名称

    public static final String VALUE_CHANGE_TYPE = "valueChangeType";//修改价值
    public static final String DYNAMIC_ID = "dynamicId";//动态ID
    public static final String VALUE_ID = "valueId";//价值ID

    public static final String MINE_BILL_DATA = "mineBillDataBean";//账单实体数据
    public static final String MINE_EXCHANGE = "MineExchange";//兑换原始股
    public static final String MINE_TRANSACTION_ORDER = "mine_transaction_order";//交易订单
    public static final String MINE_TRANSACTION_USER_TYPE = "userType";//交易用户类型（买/卖家）

    public static final String SELECT_MAX_COUNT = "selCount";//最大数量
    public static final String SELECT_TYPE = "selectType";//行业类型
    public static final String SCHOOL_SMALL = "smallSchool";//小学
    public static final String SCHOOL_MEDIUM = "mediumSchool";//初中
    public static final String SCHOOL_HIGH = "highSchool";//高中
    public static final String SCHOOL_LARGE = "largeSchool";//大学

    public static final String KEY_LOCAL_TASK = "localTask";
    public static final String TASK_ISONLOCAL = "taskIsLocal";

    public static final String appPackageName = "com.cloudwallet";//云钱包包名
    public static final String activityName = "com.cloudwallet.ui.activity.idcard.IDCardVerifySelectActivity";//云钱包实名认证页面


    public static final int LOGIN_USER_ID = 11001;//登录用户ID
    public static final int MPUSH_MESSAGE = 11002;//push消息
    public static final int MPUSHSET_MESSAGE = 11102;//从设置返回聊天界面
    public static final int CHAT_ING = 11003;//进入当前聊天
    public static final int CHAT_EXIT = 11004;//推出当前聊天界面
    public static final int CHAT_SETTING = 11005;//聊天设置界面
    public static final int CHAT_CONTENT_SEARCH = 11006;//聊天内容查找
    public static final int FEEDBACK_DETAILS = 11007;//聊天内容查找
    public static final int FROM_SEARCH_RESULT_RETURN_CHAT_LIST = 11008;//聊天内容查找
    public static final int CHAT_MESSAGE_DELETED = 11009;//删除聊天内容
    public static final int TO_REFRESH_MESSAGE_NUM = 11010;//
    public static final int TO_REFRESH_USERNAME_NOTE = 11011;//更新备注名
    public static final int TO_REFRESH_SESSION_LIST = 11012;//

    public static final int GROUP_SETTINGEXIT_CODE = 663;//退出并删除群聊
    public static final int GROUP_CHAT_CODE = 664;//群聊
    public static final int GROUP_CHANGEQZ = 665;//转移群主
    public static final int GROUP_CHANGEQZ_UPDATEUI = 666;//转移群主之后刷新设置界面
    public static final int GROUP_CHOICECARD_CODE = 766;//选择发送的好友名片
    public static final int SEND_CODE = 767;//选择发送的好友名片

    public static final int DYNAMIC = 0;//动态码
    public static final int VALUE = 1;//价值交换码
    public static final int USER = 2;//用户码
    public static final int COMMENT = 3;//评论码
    public static final int ORDER = 4;//订单

    public static final int HOME_TITLE_VISIBLE = 0;//首页标题栏显示
    public static final int HOME_TITLE_GONE = 1;//首页标题栏隐藏

    public static final int REQUEST_CODE_CHOOSE = 100;//图片多选请求码
    public static final int PREVIEW_RESULT_CODE = 201;//图片预览删除响应码
    public static final int NOTES_RESULT_CODE = 302;//备注回传响应码

    //关系 0：好友  1：关注  2：粉丝
    public static final int MAIL_TYPE_FRIEND = 0;
    public static final int MAIL_TYPE_FOLLOW = 1;
    public static final int MAIL_TYPE_FANS = 2;

    //星座筛选： 0：所有  1-12：水瓶座-摩羯座
    public static final int CONSTELLATION_ALL = 0;
    public static final int CONSTELLATION_SHUIPING = 1;
    public static final int CONSTELLATION_SHUANGYU = 2;
    public static final int CONSTELLATION_BAIYANG = 3;
    public static final int CONSTELLATION_JINNIU = 4;
    public static final int CONSTELLATION_SHUANGZI = 5;
    public static final int CONSTELLATION_JUXIE = 6;
    public static final int CONSTELLATION_SHIZI = 7;
    public static final int CONSTELLATION_CHUNV = 8;
    public static final int CONSTELLATION_TIANCHENG = 9;
    public static final int CONSTELLATION_TIANXIE = 10;
    public static final int CONSTELLATION_SHESHOU = 11;
    public static final int CONSTELLATION_MOJIE = 12;

    //(0:关注/取消关注 1：拉黑)关注：1  取消关注：0
    public static final int PEOPLE_TO_BLOCK = 1;
    public static final int PEOPLE_TO_FOLLOW = 1;
    public static final int PEOPLE_TO_FANS = 0;
    public static final int DYNAMIC_SHOW_HUIFU = 1;
    public static final int DYNAMIC_SHOW_COMMENT = 0;

    //价值分类 0：书籍   1：玩具
    public static final int VALUE_BOOK = 0;
    public static final int VALUE_TOYS = 1;

    //价值审核分类
    public static final int VALUE_UPPER = 0;//已上架
    public static final int VALUE_EXAMINE = 1;//审核中
    public static final int VALUE_LOWER = 3;//已下架
    public static final int VALUE_FAILED = 2;//审核失败

    //价值查询分类
    public static final int MINE_BUY = 0;
    public static final int MINE_SELL = 1;

    //价值交换进度分类
    public static final int MINE_LIST_ALL = 0;//全部
    public static final int MINE_LIST_IN = 1;//交易中
    public static final int MINE_LIST_COMPLETED = 4;//已完成
    public static final int MINE_LIST_CLOSE = 5;//已关闭

    //标签
    public static final int LABEL_FILM = 10001;//电影
    public static final int LABEL_BOOK = 10002;//书籍
    public static final int LABEL_MUSIC = 10003;//音乐
    public static final int LABEL_INDUSTRY = 10004;//行业
    public static final int LABEL_VALUE = 10005;//价值
    public static final int LABEL_VALUEALL = 10006;//待筛选的
    public static final int LABEL_GENESIS_INDUSTRY = 10007;//加入团队行业

    //编辑用户信息
    public static final int MINE_PERSONAL_SCHOOL = 405;//学校
    public static final int MINE_PERSONAL_LOCATION = 406;//故乡
    public static final int MINE_PERSONAL_ADDRESS = 407;//居住地
    public static final int MINE_PERSONAL_NAME = 408;//用户名
    public static final int MINE_PERSONAL_INTRO = 409;//个性签名

    public static final int REQUEST_CODE = 1001;//请求码
    public static final int RESULT_CODE = 1002;//响应码
    public static final int REQUEST_LOCATION = 10003;//选择位置
    public static final int LOCATION_RESULT = 10004;//发送位置返回码
    public static final int YQB_REQUEST_CODE = 1005;//云钱包请求码

    public static final int RESOURCE = 0;//来源
    public static final int REQUEST_CAMERA_CODE = 10201;//相机拍照
    public static final int REQUEST_CORP_CODE = 10202;//相机裁剪
    public static final String KEY_STATU = "isVersion";//是否更新

    public static final int CHANGE_AVATAR = 1001;//修改头像
    public static final int LOCATION_SUCCESS = 0x201;//定位成功

    public static final int DYNAMIC_AUTOREFRESH = 0x302;//刷新动态列表
    public static final int MINE_PERSONAL_INFO = 0x300;//刷新我的页面数据

    public static final int GROUP_CHAT_CHANGE_NAME = 1;//修改群名称
    public static final int GROUP_CHAT_CHANGE_ANNOUNCEMENT = 2;//修改群公告
    public static final int GROUP_CHAT_CHANGE_AVATAR = 3;//修改群头像

    public static final int CHANGE_GROUP_CHAT_NAME = 0x400;//群名称
    public static final int CHANGE_GROUP_CHAT_ANNOUNCEMENT = 0x401;//群公告
    public static final int CHANGE_GROUP_CHAT_AVATAR = 0x402;//群头像
    public static final int GROUP_CHAT_APPLY = 0x403;//群申请

    public static final int GENESIS_SUCCESS = 0X501;//登记成功

    public static int LABEL_SELECT = -1;//标签选中索引

    //加人或者踢人
    public static final int ADDORT_CODE = 66;
    //刷新群设置信息
    public static final int UPDATE_GROUPINFO = 67;

    public static String ALLOC_URL = "";
    public static String API_URL = "";
}
