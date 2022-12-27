package com.example.webdemo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
//@Table(name = "web_user")

@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {
    private String nickName;
    private String userName;
    private String email;
    private String phone;
    private String password;
    private String avatar;
    private int type;
    private String salt;
    private boolean applyTeacher;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    @OneToMany(mappedBy = "teacher")
    private List<Course> courses;
    @ManyToMany
    private List<Course> enrolledCourses;
    @OneToMany(mappedBy = "student")
    private List<Result> results;
    @OneToMany(mappedBy = "student")
    private List<Applicant> applicants;

    @JsonManagedReference
    public List<Applicant> getApplicants() {
        return applicants;
    }

    public void setApplicants(List<Applicant> applicants) {
        this.applicants = applicants;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public boolean isApplyTeacher() {
        return applyTeacher;
    }

    public void setApplyTeacher(boolean applyTeacher) {
        this.applyTeacher = applyTeacher;
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

    @JsonManagedReference
    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    @JsonIgnore
    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void setEnrolledCourses(List<Course> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }
    public void setResults(List<Result> results) {
        this.results = results;
    }
    @JsonManagedReference
    public List<Result> getResults() {
        return results;
    }


    public User(UUID id, String nickName, String userName, String email, String phone, String password, String avatar, int type, String salt, boolean applyTeacher) {
        super(id);
        this.nickName = nickName;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.avatar = avatar;
        this.type = type;
        this.salt = salt;
        this.applyTeacher = applyTeacher;
        this.setCreateTime(new Date());
        this.updateTime=createTime;
    }
}
