package com.example.webdemo.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Applicant extends BaseEntity {
    @ManyToOne
//    @JoinColumn(name = "student_id")
    private  User student;
    @ManyToOne
//    @JoinColumn(name = "course_id")
    private  Course course;
    private  int status;
    @Temporal(TemporalType.TIMESTAMP)
    private Date applyTime;
    @JsonBackReference
    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }
    @JsonBackReference
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }
}
