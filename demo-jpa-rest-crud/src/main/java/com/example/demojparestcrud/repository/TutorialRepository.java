package com.example.demojparestcrud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demojparestcrud.model.Tutorial;

public interface TutorialRepository extends JpaRepository<Tutorial, Long> {

	/*
	现在我们可以使用JpaRepository的方法:
	save()， findOne()， findById()， findAll()，
	count()， delete()， deleteById()
	*/
    //我们也可以定义以下接口方法
	List<Tutorial> findByPublished(boolean published);
	List<Tutorial> findByTitleContaining(String title);
}
