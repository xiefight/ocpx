package huihuang.proxy.ocpx.common;

/**
 * @Description: 统一返回
 * @Author: xietao
 * @Date: 2023-04-20 21:21
 **/
public class Response {

    private int code;
    private String message;
    private Object data;

    public Response(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }


    public Response() {
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }


    public String toString() {
        return "ResponseObject [code=" + this.code + ", message=" + this.message + ", data=" + this.data + "]";
    }

}
