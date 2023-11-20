package com.gz.example.one;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @Description: TODO
 * @Author: zguo
 * @CreateTime: 2023-11-20  16:12
 * @Version: 1.0
 */
/*@Data
@NoArgsConstructor
@AllArgsConstructor*/
public class User {

    private Long id;

    private String username;

    @LogCompar("真实姓名")
    private String realName;

    @LogCompar(value = "性别", readConverterExp = "0=男,1=女,2=未知")
    private Integer sex;

    @LogCompar("邮箱")
    private String email;

    @LogCompar(value = "薪水")
    private BigDecimal salary;

    @LogCompar(value = "创建时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    public User() {
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", realName='" + realName + '\'' +
                ", sex=" + sex +
                ", email='" + email + '\'' +
                ", salary=" + salary +
                ", createTime=" + createTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(realName, user.realName) && Objects.equals(sex, user.sex) && Objects.equals(email, user.email) && Objects.equals(salary, user.salary) && Objects.equals(createTime, user.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, realName, sex, email, salary, createTime);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public User(Long id, String username, String realName, Integer sex, String email, BigDecimal salary, LocalDateTime createTime) {
        this.id = id;
        this.username = username;
        this.realName = realName;
        this.sex = sex;
        this.email = email;
        this.salary = salary;
        this.createTime = createTime;
    }
}
