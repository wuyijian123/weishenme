package com.example.demojparestcrud.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tutorial_details")
public class TutorialDetails {
  @Id
  private Long id;

  @Column
  private Date createdOn;

  @Column
  private String createdBy;

 //TutorialDetails类有@OneToOne注释，用于与Tutorial实体的一对一关系
  @OneToOne(fetch = FetchType.LAZY)
  //@MapsId注释，它使id字段同时充当主键和外键(共享主键)。
  @MapsId
  //@JoinColumn 注解用来定义主键字段和外键字段的对应关系
  @JoinColumn(name = "tutorial_id")
  private Tutorial tutorial;

  public TutorialDetails() {
  }

  public TutorialDetails(String createdBy) {
    this.createdOn = new Date();
    this.createdBy = createdBy;
  }

  public Date getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(Date createdOn) {
    this.createdOn = createdOn;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public Tutorial getTutorial() {
    return tutorial;
  }

  public void setTutorial(Tutorial tutorial) {
    this.tutorial = tutorial;
  }

}
