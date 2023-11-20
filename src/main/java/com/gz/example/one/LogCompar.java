package com.gz.example.one;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Describe 自定义注解类  注释在要比较变更的对象的属性上
 * @Author zguo
 * @Date 2023/11/20
 * @version 1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogCompar {
    //属性名称
    String value();

    //日期格式 如: yyyy-MM-dd
    String dateFormat() default "";

    //读取内容转换表达式 (如: 0=男,1=女,2=未知)
    String readConverterExp() default "";

    //分隔符
    String separator() default ",";

    //值为空的默认值
    String defaultValue() default "null";


}
