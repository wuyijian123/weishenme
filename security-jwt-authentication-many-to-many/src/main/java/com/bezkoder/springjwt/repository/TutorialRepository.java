package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.Tutorial;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TutorialRepository extends JpaRepository<Tutorial, Long> {

	/*
	我们不用定义也可以使用JpaRepository的方法:
	save()， findOne()， findById()， findAll()，
	count()， delete()， deleteById()
	*/
    //我们也可以定义以下接口方法
	List<Tutorial> findByPublished(boolean published);
	List<Tutorial> findByTitleContaining(String title);
	List<Tutorial> findTutorialsByTagsId(Long tagId);
}
