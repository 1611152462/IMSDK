package com.ecareyun.im.network;

/**
 * 类名称：ServerInterfaceMethod
 * 类描述：请求接口url
 * 创建人：谢征
 * 创建时间：2016/5/18 10:56
 */
public class ServerInterfaceMethod {

    /**
     * 设备初始化
     */
    public static final String OPT_DEVICE_INIT = "/poscloud-web/api/poslogin/posInit";

    /**
     * 设备注册
     */
    public static final String OPT_DEVICE_REGISTER = "/poscloud-web/api/poslogin/saveMacAddress";

    /**
     * 用户登录
     */
    public static final String OPT_USER_LOGIN = "/poscloud-web/api/poslogin/userlogin";

    /**
     * 账务日期
     */
    public static final String OPT_ACCOUNT_DATE = "/poscloud-web/api/poslogin/getAccountDate";

    /**
     * 获取流水号
     */
    public static final String OPT_GET_SERIAL_NO = "/poscloud-web/api/pos/getOrderSerialNo";

    /**
     * 会员信息
     */
    public static final String OPT_GET_VIP_INFO = "/poscloud-web/api/pos/getMemberInfo";

    /**
     * 扫码添加商品(实时计算优惠)
     */
    public static final String OPT_SCAN_PRODUCT = "/poscloud-web/api/pos/scanProduct";

    /**
     * 购物车合计（取消商品，增加商品数量，获取整单明细都调此接口，返回购物车数据及优惠）
     */
    public static final String OPT_CART_TOTALED = "/poscloud-web/api/pos/getShoppingCar";

    /**
     * 结算-生成订单
     */
    public static final String OPT_CREATE_ORDER = "/poscloud-web/api/pos/payRequest";

    /**
     * 取消结算-撤销订单
     */
    public static final String OPT_CANCEL_ORDER = "/poscloud-web/api/pos/cancelPay";

    /**
     * 支付方式按键列表
     */
    public static final String OPT_PAY_BUTTONS_LIST = "/poscloud-web/api/poslogin/getPaymentKey";

    /**
     * 付款，支付(微信/支付宝/现金、券)
     */
    public static final String OPT_ORDER_PAY = "/poscloud-web/api/pos/payingOrder";

    /**
     * 付款完成（更改订单支付状态）
     */
    public static final String OPT_PAY_FINISH = "/poscloud-web/api/pos/payFinish";

    /**
     * 支付查询
     */
    public static final String OPT_QUERY_PAY_RESULT = "/poscloud-web/api/pos/queryPayResult";

    /**
     * 获取订单信息
     */
    public static final String GET_ORDERINFO_LIST = "/index";

    /**
     * 退货接口
     */
    public static final String RETURN_GOODS_LIST = "/poscloud-web/api/pos/returnOrder";

    /**
     * 加油记录
     */
    public static final String OPT_OIL_RECORDS_LIST = "/poscloud-web/api/posOil/oilOrderList";

}
