package cn.edu.lzzy.mypractices.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lzzy on 2022/11/7.
 * Description:
 */
//注解为持久化实体
@Entity
public class Course extends BaseEntity {
    private String name;//课程名称
    private String description; //课程描述
    private String cover;//课程封面
    //注解从数据库获取带有日期和时间的值
    //若为DATE 则仅带日期不带时间，若为TIME 则仅带时间不带日期
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;//创建日期
    //注解从数据库获取带有日期和时间的值
    //若为DATE 则仅带日期不带时间，若为TIME 则仅带时间不带日期
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;//更新日期
    private boolean open;//是否开放该课程

    //课程教师关系，多对一关系
    //多个课程都由1个教师开设。
    //在多方设置1个
    @ManyToOne
    private User teacher;


    //课程学生关系：多对多关系
    // 一个课程有多个学生注册
    // 一个学生可注册多门课程
    @ManyToMany
    private List<User> students = new ArrayList<>();

    //无参构造函数，创建课程的时候，设置课程的创建时间和更新时间
    public Course(){
        createTime = new Date();
        updateTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    //json 序列化时，关联的字段会造成无限递归，
    // 对于1:n的关系，在1方（teacher）的getter上设置@JsonBackReference 注解
    // 同时在n方（ courses ）字段的getter 上设置@JsonManagedReference 注解
    @JsonBackReference
    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User user) {
        this.teacher = user;
    }

    //json 序列化时，关联的字段会造成无限递归
    //对于n:n的关系，在两方的getter 上设置@JsonIgnore 即可
    //也可直接加在字段上，则对序列化和反序列化都起作用， 加在getter 上对序列化起作用。
    @JsonIgnore
    public List<User> getStudents() {
        return students;
    }

    public void setStudents(List<User> students) {
        this.students = students;
    }


}
