package huihuang.proxy.ocpx.channel.huawei;

/**
 * baidu事件类型枚举
 * @Author: xietao
 * @Date: 2023/5/9 20:31
 */
public enum HuaweiEventTypeEnum {

    ACTIVE("activate", "用户首次打开 app"),
    REGISTER("register", "用户产生注册行为"),
    RETAIN("retain", "用户激活后次日内打开 app"),
    PAID("paid", "用户产生付费行为"),
    BROWSE("browse", "浏览"),
    COLLECTION("collection", "收藏"),
    PRE_ORDER("preOrder", "用户产生下单行为"),
    SUBSCRIBE("subscribe", "用户完成某项服务/频道订阅行为"),
    LOGIN("login", "用户完成登陆行为"),
    UPDATE("update", "用于追踪更新事件"),
    RESERVATION("reservation", "预约服务"),
    ADD_TO_CART("addToCart", "加入购物车"),
    THREE_DAY_RETAIN("threeDayRetain", "用户在激活后 3 天内打开 APP"),
    SEVEN_DAY_RETAIN("sevenDayRetain", "用户在激活后 7 天内打开 APP"),
    DELIVER("deliver", "订单发货"),
    ORDER_SIGNING("orderSigning", "订单签收"),
    FIRST_PURCHASE("firstPurchase", "首次购买"),
    PURCHASE_MEMBER_CARD("purchaseMemberCard", "购买会员"),
    ADD_QUICK_APP("addQuickApp", "快应用添加"),
    ADD_TO_WISHLIST("addToWishlist", "添加到心愿清单"),
    OPENED_FROM_PUSH_NOTIFICATION("openedFromPushNotification", "从推送通知打开"),
    RE_ENGAGE("reEngage", "用户唤醒--打开 app"),
    FORM_SUBMIT("form_submit", "表单提交"),
    CONSULT("consult", "有效咨询"),
    EFFECTIVE_LEADS_FORM("effectiveLeadsForm", "广告主投放的表单线索回拨后确认为有效线索"),
    POTENTIAL_CUSTOMER_FORM("potentialCustomerForm", "广告主投放的表单线索回拨后确认为潜在客户"),
    CUSTOM_ACQUISIT("custom_acquisit", "获取有效客户"),
    BOOK("book", "获取有效预定"),
    CONSULT_ONLINE("consultOnline", "用户在网页进行咨询操作"),
    EFFECTIVE_LEADS_ONLINE("effectiveLeadsOnline", "广告主投放的咨询组件确认发生咨询的对话"),
    POTENTIAL_CUSTOMER_ONLINE("potentialCustomerOnline", "广告主投放的咨询组件确认发生留咨的行为"),
    PHONE_DIALING("phoneDialing", "用户点击拨打电话按钮"),
    EFFECTIVE_LEADS_PHONE("effectiveLeadsPhone", "广告主投放的电话组件确认接通"),
    POTENTIAL_CUSTOMER_PHONE("potentialCustomerPhone", "广告主投放的电话组件接通后确认为潜在客户"),
    FOLLOW_SCAN("followScan", "跳转到落地页后，产生的扫码关注次数"),
    LEADS_LOTTERY("leadsLottery", "在抽奖后，提交手机号并成功上报"),
    ADD_PAYMENT_INFO("addPaymentInfo", "添加付款信息"),
    START_TRIAL("startTrial", "开始试用"),
    INITIATED_CHECKOUT("initiatedCheckout", "发起结账"),
    INVITE("invite", "邀请"),
    SEARCH("search", "搜索"),
    SHARE("share", "分享"),
    TRAVEL_BOOKING("travelBooking", "旅行预订"),
    RATE("rate", "用于追踪商品/应用评级事件"),
    CONTENT_VIEW("contentView", "内容视图"),
    CUSTOM("custom", "用于追踪自定义转化事件"),
    CUSTOM_LANDING_PAGE("custom_landingpage", "用于追踪落地页内自定义转化事件"),
    LANGDING_PAGE_CLICK("landingpageClick", "用户点击按钮 button"),
    COUPON("coupon", "卡券领取"),
    NAVIGATE("navigate", "门店导航"),
    LOTTERY("lottery", "抽奖"),
    VOTE("vote", "投票"),
    REDIRECT("redirect", "页面跳转"),
    GAME_PACKAGE_REDEMPTION("gamePackageRedemption", "礼包兑换"),
    GAME_PACKAGE_CLAIMING("gamePackageClaiming", "礼包领取"),
    CREATE_ROLE("createRole", "创建角色"),
    AUTHORIZE("authorize", "游戏授权"),
    TUTORIAL_COMPLETION("tutorialCompletion", "完成新手教程（游戏）"),
    ACHIEVEMENT_UNLOCKED("achievementUnlocked", "解锁成就"),
    SPENT_CREDITS("spentCredits", "花掉积分"),
    LEVEL_ACHIEVED("levelAchieved", "达到级别"),
    LOAN_COMPLETION("loanCompletion", "用户进行相关服务完件"),
    PRE_CREDIT("preCredit", "预授信"),
    CREDIT("credit", "授信"),
    FOLLOW("follow", "关注"),
    FORWARD("forward", "转发"),
    READ("read", "阅读"),
    LIKE("like", "点赞"),
    COMMENT("comment", "评论"),
    QUALITY_ACTIVATE("qualityActivate", "优质激活"),

    ;

    private String code;
    private String desc;

    HuaweiEventTypeEnum(String code, String desc) {
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
