import com.gz.example.one.RecordUtils;
import com.gz.example.one.User;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Description: TODO
 * @Author: zguo
 * @CreateTime: 2023-11-20  16:13
 * @Version: 1.0
 */
public class Test01 {
    @Test
    public void test01(){
        User oldUser = new User(1L, "admin", "张三", 1, "123@qq.com", BigDecimal.valueOf(12), LocalDateTime.now());
        User newUser = new User(2L, "admin", "李四", 2, null, BigDecimal.valueOf(12.00), LocalDateTime.of(2022, 2, 12, 5, 12, 5));
        System.out.println(RecordUtils.addChangeRecord(oldUser, newUser));
    }
    @Test
    public void test02(){
        //String date = "2023-11-20 17:29:08";
        Date date = new Date();
        String time = String.format("%tc", date);
        String form = String.format("%tF", date);
        String form2 = String.format("%tD", date);
        String form3 = String.format("%tr", date);
        String form4 = String.format("%tT", date);
        String form5 = String.format("%tR", date);
        System.out.println("全部的时间信息是：" + time);
        System.out.println("年-月-日格式：" + form);
        System.out.println("年/月/日格式：" + form2);
        System.out.println("时：分：秒 PM(AM)格式：" + form3);
        System.out.println("时：分：秒格式：" + form4);
        System.out.println("时：分格式：" + form5);
    }
}
