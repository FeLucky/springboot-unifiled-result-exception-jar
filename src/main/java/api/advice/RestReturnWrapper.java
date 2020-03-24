package api.advice;

import api.annotation.RestReturn;
import api.common.RestReturnEnum;
import org.springframework.core.MethodParameter;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author wangtiexiang
 * @Description
 * @Datetime 2020/3/23 5:26 下午
 */
@ControllerAdvice
public class RestReturnWrapper implements ResponseBodyAdvice<Object> {
    /**
    *   @Description    判定哪些请求要执行beforeBodyWrite，返回true执行，返回false不执行
    *   @Params     [methodParameter, converterType]
    *   @Return     boolean
    *   @author     wangtiexiang
    *   @Datetime   2020/3/23 7:17 下午
    */
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> converterType) {
        /**
         * methodParameter获取当前处理请求的controller的方法
         * 判断方法所在类是否存在RestReturnAnnotation注解，存在则格式化结果，否则不格式化
         * 这里可以加入很多判定，如果在白名单的List里面，是否拦截
         */
        return methodParameter.getDeclaringClass().isAnnotationPresent(RestReturn.class);

    }


    /**
    *   @Description    返回前对body，request，response等请求做处理
    *   @Params     [body, methodParameter, mediaType, httpMessageConverter, serverHttpRequest, serverHttpResponse]
    *   @Return     java.lang.Object
    *   @author     wangtiexiang
    *   @Datetime   2020/3/23 7:18 下午
    */
    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter methodParameter,
                                  MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> httpMessageConverter,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {
        /**
         * 返回值
         */

        //具体返回值处理
        //情况1 如果返回的body为null
        if(body == null){
            if (mediaType == MediaType.APPLICATION_JSON) {
                //返回是json个格式类型，无body内容
                api.common.RestReturn restReturn = new api.common.RestReturn();
                return restReturn.success(RestReturnEnum.SUCCESS.getCode(), body,RestReturnEnum.SUCCESS.getMessage());
            } else {
                return null;
            }
        } else {
            //情况2 文件上传下载，不需要改动，直接返回
            if (body instanceof Resource) {
                return body;
            } else if (body instanceof String) {
                // 返回的是 String，
                api.common.RestReturn restReturn = new api.common.RestReturn();
                try {
                    if (restReturn.isRestReturnJson((String) body)) {
                        // 情况3 已经是RestReturn格式的json 字符串不做统一格式封装
                        return body;
                    } else {
                        //情况4 普通的返回，需要统一格式，把数据赋值回去即可。若没有在mvcconfig中添加转换器，则使用前一种方法return
                        /*return JSON.toJSONString(restReturn.success(RestReturnEnum.SUCCESS.getCode(),body.toString(),RestReturnEnum.SUCCESS.getMessage()));*/
                        return restReturn.success(RestReturnEnum.SUCCESS.getCode(),body.toString(),RestReturnEnum.SUCCESS.getMessage());
                    }
                } catch (Exception e) {
                    // 因为 API返回值为String，理论上不会走到这个分支。
                    return restReturn.error(RestReturnEnum.FAIL.getCode(), body,body);
                }
            } else {
                //返回的是非字符串格式，实际上很多时候用都是是在应用程返回的对象居多
                if(body instanceof api.common.RestReturn){
                    //情况5 如果已经封装成RestReturn,直接return
                    return body;
                }else{
                    //情况6 非字符串非统一格式的返回，需要统一格式
                    //需要判定是否是抛出的异常返回（统一到错误输出）
                    api.common.RestReturn restReturn = new api.common.RestReturn();
                    return restReturn.success(RestReturnEnum.SUCCESS.getCode(),body,RestReturnEnum.SUCCESS.getMessage());
                }
            }
        }
    }
}
