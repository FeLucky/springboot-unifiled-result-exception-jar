package api.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.time.Instant;

/**
 * @author wangtiexiang
 * @Description
 * @Datetime 2020/3/23 5:25 下午
 */
@Data
public class RestReturn {
    /**
     * 是否成功标志
     */
    private boolean success;
    /**
     * code错误码
     */
    private Integer code;
    /**
     * 外带数据信息
     */
    private Object data;
    /**
     * 前端进行页面展示的信息
     */
    private Object message;
    /**
     * 返回时间戳
     */
    private Long currentTime;

    /**
    *   @Description   构造函数（无参数）
    *   @Params     []
    *   @Return
    *   @author     wangtiexiang
    *   @Datetime   2020/3/24 10:15 上午
    */
    public RestReturn() {
        //毫秒
        this.currentTime = Instant.now().toEpochMilli();
    }

    /**
    *   @Description    构造函数（有参数）
    *   @Params     [success, code, data, message]
    *   @Return
    *   @author     wangtiexiang
    *   @Datetime   2020/3/24 10:15 上午
    */
    public RestReturn(boolean success, Integer code, Object data, Object message) {
        this.success = success;
        this.code = code;
        this.data = data;
        this.message = message;
        //毫秒
        this.currentTime = Instant.now().toEpochMilli();
    }
    @Override
    public String toString() {
        return "RestReturn{" +
                "success=" + success +
                ",code='" + code + '\'' +
                ",data=" + data +
                ",message=" + message +
                ",currentTime=" + currentTime +
                '}';
    }

    public RestReturn success(Integer code, Object data, Object message) {
        this.success = true;
        this.code = code;
        this.data = data;
        this.message = message;
        //毫秒
        this.currentTime = Instant.now().toEpochMilli();

        return this;
    }

    public RestReturn error(Integer code, Object data, Object message) {
        this.success = false;
        this.code = code;
        this.data = data;
        this.message = message;

        return this;
    }

    public boolean isRestReturnJson(String data) {
        //临时实现先判定下字符串的格式和字段
        try {
            //ObjectMapper支持从byte[]、File、InputStream、字符串等数据的JSON反序列化。
            ObjectMapper mapper = new ObjectMapper();
            RestReturn dataRestReturn = mapper.readValue(data, RestReturn.class);
            //比较两个类的字段,如果一致返回为真，不一致返回为假
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
