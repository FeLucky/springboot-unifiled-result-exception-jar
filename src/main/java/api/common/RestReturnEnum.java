package api.common;

/**
 * @author wangtiexiang
 * @Description
 * @Datetime 2020/3/23 5:28 下午
 */
public enum RestReturnEnum {
    /**
     * 返回字典枚举值
     */
    SUCCESS(200, "成功"),
    FAIL(-1, "失败"),
    BAD_REQUEST(400, "错误请求"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "未找到"),
    EXCEPTION(500, "系统异常");

    /**
     * code错误码
     */
    private Integer code;
    /**
     * 前端进行页面展示的信息
     */
    private String message;

    private RestReturnEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }
}
