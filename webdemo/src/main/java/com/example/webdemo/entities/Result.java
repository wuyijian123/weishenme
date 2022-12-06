package com.example.webdemo.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Result extends BaseEntity {
    @Temporal(TemporalType.TIMESTAMP)
    private Date commitTime;
    @ManyToOne
//    @JoinColumn(name = "student_id")
    private  User student;
    @ManyToOne
//    @JoinColumn(name = "question_id")
    private  Question question;
    @ManyToMany
    private List<Option> options;


    @Transient
    private int correct = 0;
    @Transient
    private int inAMoment = 1;
    @Transient
    private int multipleChoices= 2;
    @Transient
    private  int misSelection=3;
    @Transient
    private  int notDone=4;





    public Date getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(Date commitTime) {
        this.commitTime = commitTime;
    }
    @JsonBackReference
    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }
    @JsonBackReference
    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
    @JsonIgnore
    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }
}
