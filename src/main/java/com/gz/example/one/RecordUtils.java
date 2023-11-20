package com.gz.example.one;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description: 添加变更记录工具类
 * @Author: zguo
 * @CreateTime: 2023-11-20  14:55
 * @Version: 1.0
 */
public class RecordUtils {

    //注解列表(添加了@LogCompar的属性集合，数组的第一位是属性Field，第二位是注解LogCompar)
    private static List<Object[]> fields;

    /**
     * @description: 比较变更前后的数据，记录下添加了@LogCompar注解的属性，由原来的什么值变为了现在的什么值
     * @author: zguo 
     * @date: 2023/11/20 15:06
     * @param: oldObj 原数据对象 newObj 新数据对象
     * @return: java.lang.String
    **/
    public static String addChangeRecord(Object oldObj,Object newObj){
        //获取class对象
        Class<?> oldclazz = oldObj.getClass();
        Class<?> newclazz = newObj.getClass();
        if(!oldclazz.equals(newclazz)){
            throw new RuntimeException("请传入两个类型相同的实体类对象！");
        }

        //获取添加了注解的属性集合
        fields = getField(oldclazz);
        if (fields.isEmpty()) {
            throw new RuntimeException("未找到要对比的属性！");
        }
        //定义变量-变更信息描述
        StringBuilder info = new StringBuilder();
        //根据注解列表循环比较属性是否发生变化
        for (Object[] obj :
                fields) {
            Field field = (Field) obj[0];
            LogCompar logCompar = (LogCompar) obj[1];

            try {
                //获取属性的值
                Object oldValue = field.get(oldObj);
                Object newValue = field.get(newObj);
                if (newValue == null) {
                    newValue = logCompar.defaultValue();
                }
                if (oldValue == null) {
                    oldValue = logCompar.defaultValue();
                }
                //如果新值和旧值相等，则跳过
                if (oldValue == newValue || newValue.equals(oldValue)){
                    continue;
                }

                //若不相等，需要compareTo方法判断是否相等,相等则跳过
                if(field.getType() == BigDecimal.class && ((BigDecimal)newValue).compareTo((BigDecimal)oldValue)!=0){
                    continue;
                }
                // 若属性值有变化，则判断是否有码值，
                if (StringUtils.isNotBlank(logCompar.readConverterExp())) {
                    oldValue = convertByExp(oldValue.toString(), logCompar.readConverterExp(), logCompar.separator());
                    newValue = convertByExp(newValue.toString(), logCompar.readConverterExp(), logCompar.separator());
                } else if (StringUtils.isNotBlank(logCompar.dateFormat())) {
                    oldValue = DateHelper.format(oldValue, logCompar.dateFormat());
                    newValue = DateHelper.format(newValue, logCompar.dateFormat());
                }
                info.append(String.format("%s:由%s 变为了 %s;", logCompar.value(), oldValue, newValue));
                info.append("\r\n");
            } catch (IllegalAccessException e) {
                e.getMessage();
            }
        }
        return info.toString();
    }

    /**
     * 解析导出值 0=男,1=女,2=未知
     *
     * @param propertyValue 参数值
     * @param converterExp  翻译注解
     * @param separator     分隔符
     * @return 解析后值
     */
    private static String convertByExp(String propertyValue, String converterExp, String separator) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] convertSource = converterExp.split(",");
        for (String item : convertSource) {
            String[] itemArray = item.split("=");
            if (StringUtils.containsAny(separator, propertyValue)) {
                for (String value : propertyValue.split(separator)) {
                    if (itemArray[0].equals(value)) {
                        stringBuilder.append(itemArray[1] + separator);
                        break;
                    }
                }
            } else if (itemArray[0].equals(propertyValue)) {
                return itemArray[1];
            }
        }
        return StringUtils.stripEnd(stringBuilder.toString(), separator);
    }

    /**
     * @description: 获取添加注解的属性信息
     * @author: zguo
     * @date: 2023/11/20 15:20
     * @param: [oldclazz]
     * @return: java.util.List<java.lang.Object>
    **/
    private static List<Object[]> getField(Class<?> oldclazz) {
        //定义添加了@LogCompar注解的属性集合
        List<Object[]> fields = new ArrayList<>();
        //定义临时的属性集合，包含当前类和父类的所有属性
        List<Field> tempFields = new ArrayList<>();
        tempFields.addAll(Arrays.asList(oldclazz.getDeclaredFields()));
        tempFields.addAll(Arrays.asList(oldclazz.getSuperclass().getDeclaredFields()));
        //遍历临时属性集合，判断当前属性是否添加@LogCompar注解，若有，则将当前属性添加到fields中
        for (Field field :
                tempFields) {
            //若当前属性添加了@LogCompar注解，则isAnnotationParsent()方法返回true
            if (field.isAnnotationPresent(LogCompar.class)){
                LogCompar logCompar = field.getAnnotation(LogCompar.class);
                if (logCompar != null) {
                    //设置可以访问私有属性
                    field.setAccessible(true);
                    fields.add(new Object[]{field,logCompar});
                }
            }
        }
        return fields;
    }
}
