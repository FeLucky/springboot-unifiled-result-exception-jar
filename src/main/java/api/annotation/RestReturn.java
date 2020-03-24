package api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wangtiexiang
 * @Description
 * @Datetime 2020/3/23 11:03 下午
 */
@Retention(RetentionPolicy.RUNTIME)
//指定注解只能放在"类型"上
@Target({ElementType.TYPE})
public @interface RestReturn {
}
