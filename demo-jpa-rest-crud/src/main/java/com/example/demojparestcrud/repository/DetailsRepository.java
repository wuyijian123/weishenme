package com.example.demojparestcrud.repository;


import com.example.demojparestcrud.model.Details;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface DetailsRepository extends JpaRepository<Details,Long> {
    @Transactional
   void  deleteById(long dId);

    // deleteByTutorialId():删除由tutorialId指定的教程详细信息。
    @Transactional
    void deleteByTutorialId(long tutorialId);

}
