package huihuang.proxy.ocpx.common;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-04-20 21:23
 **/
public class BasicResult {

    public static Response getSuccessResponse() {
        return getSuccessResponse(null);
    }

    public static Response getSuccessResponse(Object data) {
        return new Response(ResultStatus.success.status, ResultStatus.success.enDetail, data);
    }

    public static Response getResponse(ResultStatus status) {
        return getResponse(status, status.enDetail);
    }

    public static Response getResponse(ResultStatus status, String message) {
        return new Response(status.status, message, null);
    }


    public static Response getFailResponse(String message) {
        return getFailResponse(message, null);
    }


    public static Response getFailResponse(String message, Object data) {
        return getResponse(ResultStatus.failure.status, message, data);
    }

    public static Response getResponse(int status, String message) {
        return getResponse(status, message, null);
    }

    public static Response getResponse(int status, String message, Object data) {
        return new Response(status, message, data);
    }

}
