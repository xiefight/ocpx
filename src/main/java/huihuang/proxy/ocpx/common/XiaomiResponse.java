package huihuang.proxy.ocpx.common;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-04-27 14:33
 **/
public class XiaomiResponse extends Response {

    private String failMsg;

    public XiaomiResponse(int code, String message, Object data, String failMsg) {
        super(code, message, data);
        this.failMsg = failMsg;
    }

    public String getFailMsg() {
        return failMsg;
    }

    public void setFailMsg(String failMsg) {
        this.failMsg = failMsg;
    }
}
