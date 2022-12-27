package com.example.demojparestcrud.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {
  //JPA主键的4个生成策略
  //TABLE：使用一个特定的数据库表格来保存主键。
  //SEQUENCE：根据底层数据库的序列来生成主键，条件是数据库支持序列。
  //IDENTITY：主键由数据库自动生成（主要是自动增长型）
  //AUTO：主键由程序控制。
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_generator")
  private Long id;

  @Lob
  private String content;

  @ManyToOne(fetch = FetchType.LAZY,optional = false)
  @JoinColumn(name = "tutorial_id",nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JsonIgnore
  private Tutorial tutorial;

  public Tutorial getTutorial() {
    return tutorial;
  }

  public void setTutorial(Tutorial tutorial) {
    this.tutorial = tutorial;
  }


  public Long getId() {
    return id;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  @Override
  public String toString() {
    return "Comment [id=" + id + ", content=" + content + "]";
  }

}
