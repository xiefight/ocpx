package huihuang.proxy.ocpx.common;

/**
 * 快手激活事件响应，才能次留响应
 * @Author: xietao
 * @Date: 2023/6/15 13:10
 */
public class KuaishouResponse extends Response {

    private int ret;
    private String msg;
    private Object data;

    public KuaishouResponse(int ret, String msg, Object data) {
        this.ret = ret;
        this.msg = msg;
        this.data = data;
    }


    public KuaishouResponse() {
    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public Object getData() {
        return data;
    }

    @Override
    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "KuaishouResponse{" +
                "ret=" + ret +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
