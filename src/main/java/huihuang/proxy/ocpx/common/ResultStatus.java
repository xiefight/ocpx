package huihuang.proxy.ocpx.common;

/**
 * @Description: 返回状态
 * @Author: xietao
 * @Date: 2023-04-20 21:25
 **/
public enum ResultStatus {

    success(0, "处理成功", "success"),
    query_success(0, "查询成功", "Query success"),
    update_success(0, "修改成功", "Modify the success"),
    delete_success(0, "删除成功", "Delete the success"),
    insert_success(0, "添加成功", "Insert the success"),
    login_success(0, "登录成功", "Login the success"),

    failure(1, "处理失败", "fail"),
    input_error(10001, "输入参数不合法", "Input Parameter Invalid"),
    inner_error(10002, "系统内部错误", "Inner Error"),
    unknow_error(10003, "未知错误", "Unknow Error"),
    query_error(10004, "查询失败", "Query error"),
    update_error(10005, "修改失败", "Modify the error"),
    delete_error(10006, "删除失败", "Delete the error"),
    insert_error(10007, "添加失败", "Insert the error"),
    user_not_exist(10008, "用户不存在", "User does not exist"),
    third_user_not_exist(10008, "第三方用户不存在", "Third user does not exist"),
    password_error(10009, "密码错误", "Password error"),
    no_access(10010, "没有权限", "No access"),
    no_login(30000, "没有登陆", "No login");
    public int status;
    public String detail;
    public String enDetail;

    ResultStatus(int status, String detail, String enDetail) {
        this.status = status;
        this.detail = detail;
        this.enDetail = enDetail;
    }


    public String toString() {
        return "{status:" + this.status + ",detail:" + this.detail + "}";
    }

}
