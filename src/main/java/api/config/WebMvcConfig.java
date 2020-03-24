package api.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author wangtiexiang
 * @Description
 * @Datetime 2020/3/23 9:58 下午
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    /**
    *   @Description    这里需要注意，因为默认的convert是String，如果方法返回的是String，那么将会被提前返回，所以需要做一下兼容
    *   @Params     [converters]
    *   @Return     void
    *   @author     wangtiexiang
    *   @Datetime   2020/3/24 10:21 上午
    */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(0, new StringOrMappingJackson2HttpMessageConverter());
        super.extendMessageConverters(converters);
    }

    /**
     * 兼容String和对象类型
     *
     * @author Wang.ch
     * @date 2019/12/12
     */
    public static class StringOrMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

        private static final StringHttpMessageConverter SHMC = new StringHttpMessageConverter();

        @Override
        public boolean canWrite(Class<?> clazz, MediaType mediaType) {
            boolean canWrite = super.canWrite(clazz, mediaType);
            if (!canWrite) {
                canWrite = clazz.isAssignableFrom(String.class);
            }
            return canWrite;
        }

        @Override
        protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage)
                throws IOException, HttpMessageNotWritableException {
            if (object != null && object instanceof String) {
                outputMessage.getHeaders().setContentType(MediaType.TEXT_PLAIN);;
                SHMC.write((String)object, MediaType.TEXT_PLAIN, outputMessage);
                return;
            }
            outputMessage.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
            super.writeInternal(object, object != null ? object.getClass() : null, outputMessage);
        }
    }

    @Override
    public void configureDefaultServletHandling( DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

}
