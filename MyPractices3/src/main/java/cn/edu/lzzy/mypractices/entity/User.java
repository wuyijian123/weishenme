package cn.edu.lzzy.mypractices.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

//注解为一个实体
@Entity
public class User extends BaseEntity {
    //@Transient 注解来修饰某个属性并非一个到数据库表的字段的映射，
    // ORM 框架将忽略该属性。
    @Transient
    public static final int TYPE_BANNED = -1; //禁用账号类型
    @Transient
    public static final int TYPE_ADMIN = 0;//管理员类型
    @Transient
    public static final int TYPE_TEACHER = 1;//教师类型
    @Transient
    public static final int TYPE_STUDENT = 2;//学生类型
    @Transient
    public static final int COUNT_TYPE = 3;//类型数量
    private String nickName; //昵称
    private String userName;//用户名
    private String phone;//电话号码
    private String password;//密码
    private String email;//邮箱
    private String avatar;//头像
    private String salt;//登录验证时加密时添加盐
    private int type; //-1禁用， 0管理员，1教师，2学生 与17-24行对应

    private boolean applyTeacher; //是否申请教师

    //Temporal注解从数据库获取带有日期和时间的值，
    // 若为DATE则仅带日期不带时间，若为TIME则仅带时间不带日期
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime; //注册时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime; //更新时间

    //一名老师可以对应多个课程，每个课程对象使用teatcher字段关联
    //用户类为次，课程类为主
    @OneToMany(mappedBy = "teacher")
    private List<Course> courses = new ArrayList<>();

    //学生与课程的多对多关系
    //1个学生，可以注册多门课程
    //1个课程，可以被多个学生注册
    //用户类为次，课程类为主。主动发关联对象时才会保存到数据库
    @ManyToMany(mappedBy = "students")
    private List<Course> enrolledCourses = new ArrayList<>();

    //构造函数，创建用户时设置创建时间和更新时间
    public User(){
        createTime = new Date();
        updateTime = createTime;
    }
    //重写实例判等函数
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id.equals(user.getId());
    }
    //重写hashcode函数
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    //各属性的getter和setter
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setApplyTeacher(boolean applyTeacher) {
        this.applyTeacher = applyTeacher;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
    //json 序列化时，关联的字段会造成无限递归，
    // 对于1:n的关系，在1方（teacher）的getter上设置@JsonBackReference 注解
    // 同时在n方（ courses ）字段的getter 上设置@JsonManagedReference 注解
    @JsonManagedReference
    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    //json 序列化时，关联的字段会造成无限递归
    //对于n:n的关系，在两方的getter 上设置@JsonIgnore 即可
    //也可直接加在字段上，则对序列化和反序列化都起作用， 加在getter 上对序列化起作用。
    @JsonIgnore
    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void setEnrolledCourses(List<Course> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }
}
