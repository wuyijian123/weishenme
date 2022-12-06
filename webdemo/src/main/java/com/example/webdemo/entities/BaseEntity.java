package com.example.webdemo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;


//@Entity
@MappedSuperclass//mappedsuperclass 使这个类成为父类，用entity注解会有不同那个结果(其他除了关联表之外的表全整合在一个表里面)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2",strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

}
