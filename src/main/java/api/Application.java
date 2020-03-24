package api;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StopWatch;


/**
 * @author wangtiexiang
 * @Description springboot 启动主类
 * @Datetime 2020/3/23 3:38 下午
 */

@SpringBootApplication
public class Application {

    public static void main(String[] args) {

        StopWatch watch = new StopWatch();

        watch.start();
        SpringApplication.run(Application.class, args);
        watch.stop();

        System.out.println(String.format("启动花费：%s s",watch.getTotalTimeSeconds()));
    }
}
