package com.example.demojparestcrud.repository;

import com.example.demojparestcrud.model.TutorialDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface TutorialDetailsRepository extends JpaRepository<TutorialDetails, Long> {
 //deleteById():删除id指定的详细信息。
  @Transactional
  void deleteById(long id);

  // deleteByTutorialId():删除由tutorialId指定的教程详细信息。
  @Transactional
  void deleteByTutorialId(long tutorialId);



}
