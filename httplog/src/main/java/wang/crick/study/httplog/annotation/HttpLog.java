package wang.crick.study.httplog.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于打印http请求访问日志
 * <p>
 * Created by hemo on 2017/11/18.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HttpLog {
    /**
     * 忽略参数，避免文件or无意义参数打印
     *
     * @return 忽略参数数组
     */
    String[] exclude() default {};

    /**
     * 需要打印的header参数
     *
     * @return header参数名数组
     */
    String[] headerParams() default {};

    boolean ignoreResponse() default false;
}
