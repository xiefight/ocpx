package huihuang.proxy.ocpx.channel.toutiao;

/**
 * @Description: 头条事件类型枚举
 * https://open.oceanengine.com/labels/7/docs/1696710656359439
 * @Author: xietao
 * @Date: 2023-04-24 10:03
 **/
public enum ToutiaoEventTypeEnum {

    ACTIVE("0", "激活"),
    REGISTER("1", "注册"),
    PAY("2", "付费"),
    FORM("3", "表单"),
    ONLINE_ASK("4", "在线咨询"),
    EFFECT_ASK("5", "有效咨询"),
    SECOND_OPEN("6", "次留-用户激活后次日联网环境下打开应用"),
    APP_ORDER("20", "app内下单-在应用内完成一次订单提交，例如：点击立即下单"),
    APP_ACCESS("21", "app内访问-用户成功打开访问应用"),
    APP_SHOPPING_CART("22", "app内添加购物车-在应用内成功将商品加入购物车，例如：点击加入购物车"),
    APP_PAY("23", "app内付费-在应用内完成一次订单付费。目前主要是电商行业使用，常规建议使用付费事件"),

    KEY_BEHAVIOR("25", "关键行为"),
    EMPOWER("28", "授权-完成授权电商/支付/社交等账号登陆"),
    APP_UV("29", "app内详情页到站uv-成功访问应用内指定页面的UV数"),

    CLICK_GOODS("179", "点击商品-从多商品页点击某一商品，进入商品详情页，例如：点击某一商品，表达兴趣"),
    COLLECT("128", "加入收藏/心愿单"),
    GET_COUPON("213", "领取优惠券"),
    BUY("175", "立即购买-用户直接购买商品"),
    ADD_ADDRESS_PHONE("212", "添加/选定收货信息、电话"),
    ADD_PAY_INFO("127", "添加/选定支付信息，绑定支付宝、微信、银行卡"),
    SUBMIT_ORDER("176", "提交订单-比收藏意图更强，点击后跳转至支付页面"),
    CONFIRM_GOODS("214", "提交订单，确认收货"),

    LIVEROOM_INTO("202", "进入直播间"),
    LIVEROOM_INTEREST("204", "直播间内点击关注按钮"),
    LIVEROOM_REMARK("205", "直播间内评论"),
    LIVEROOM_REWARD("206", "直播间内打赏"),
    LIVEROOM_SHOPPING_CART("207", "直播间内点击购物车按钮"),
    LIVEROOM_CLICK_GOODS("208", "直播间内商品点击"),
    LIVEROOM_THIRD("209", "直播间进入种草页跳转到第三方"),
    LIVEROOM_ADD_BUY("210", "直播-加购"),
    LIVEROOM_ORDER("211", "直播-下单"),

    ;

    private String code;
    private String desc;

    ToutiaoEventTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
