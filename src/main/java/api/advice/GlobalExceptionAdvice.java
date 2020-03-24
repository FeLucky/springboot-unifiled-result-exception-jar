package api.advice;

import api.common.RestReturn;
import api.common.RestReturnEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;

/**
 * @author wangtiexiang
 * @Description
 * @Datetime 2020/3/23 5:28 下午
 */
@ControllerAdvice
public class GlobalExceptionAdvice {
    /**
     * 将抛出的异常写到error文件
     */
    Logger logger = LoggerFactory.getLogger(GlobalExceptionAdvice.class);
    /**
    *   @Description    处理所有不可知的异常
    *   @Params     [request, e]
    *   @Return     api.common.RestReturn
    *   @author     wangtiexiang
    *   @Datetime   2020/3/24 9:35 上午
    */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public RestReturn defaultException(HttpServletRequest request, Exception e){
        logger.error(e.getMessage(),e);
        return new RestReturn(
                false,
                RestReturnEnum.EXCEPTION.getCode(),
                e.getMessage(),
                RestReturnEnum.EXCEPTION.getMessage()
        );
    }

    /**
    *   @Description    处理 接口无权访问异常AccessDeniedException FORBIDDEN(403, "Forbidden"),
    *   @Params     [e]
    *   @Return     api.common.RestReturn
    *   @author     wangtiexiang
    *   @Datetime   2020/3/24 9:36 上午
    */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public RestReturn accessDeniedException(AccessDeniedException e){
        logger.error(e.getMessage(),e);
        return new RestReturn(
                false,
                RestReturnEnum.FORBIDDEN.getCode(),
                e.getMessage(),
                RestReturnEnum.FORBIDDEN.getMessage()
        );
    }

    /**
    *   @Description 处理bad请求异常
    *   @Params     [e]
    *   @Return     api.common.RestReturn
    *   @author     wangtiexiang
    *   @Datetime   2020/3/24 9:36 上午
    */
    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public RestReturn badRequestException(RuntimeException e) {
        logger.error(e.getMessage(),e);
        return new RestReturn(
                false,
                RestReturnEnum.BAD_REQUEST.getCode(),
                e.getMessage(),
                RestReturnEnum.BAD_REQUEST.getMessage()
        );
    }
    /**
    *   @Description    处理 EntityNotFound 数据库数据未找到
    *   @Params     [e]
    *   @Return     api.common.RestReturn
    *   @author     wangtiexiang
    *   @Datetime   2020/3/24 9:36 上午
    */
    @ExceptionHandler(value = ClassNotFoundException.class)
    @ResponseBody
    public RestReturn entityNotFoundException(ClassNotFoundException e) {
        logger.error(e.getMessage(),e);
        return new RestReturn(
                false,
                RestReturnEnum.NOT_FOUND.getCode(),
                e.getMessage(),
                RestReturnEnum.NOT_FOUND.getMessage()
        );
    }
}
