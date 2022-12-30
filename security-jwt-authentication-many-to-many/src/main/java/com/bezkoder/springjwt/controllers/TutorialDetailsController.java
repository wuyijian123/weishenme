package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.exception.ResourceNotFoundException;
import com.bezkoder.springjwt.models.Tutorial;
import com.bezkoder.springjwt.models.TutorialDetails;
import com.bezkoder.springjwt.repository.TutorialDetailsRepository;
import com.bezkoder.springjwt.repository.TutorialRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class TutorialDetailsController {

  private final TutorialDetailsRepository detailsRepository;

  private final TutorialRepository tutorialRepository;

  public TutorialDetailsController(TutorialDetailsRepository detailsRepository, TutorialRepository tutorialRepository) {
    this.detailsRepository = detailsRepository;
    this.tutorialRepository = tutorialRepository;
  }

  @GetMapping({ "/details/{id}", "/tutorials/{id}/details" })
  public ResponseEntity<TutorialDetails> getDetailsById(@PathVariable(value = "id") Long id) {
    TutorialDetails details = detailsRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("没有找到id="+ id+"的教程详细信息" ));

    return new ResponseEntity<>(details, HttpStatus.OK);
  }

  @PostMapping("/tutorials/{tutorialId}/details")
  public ResponseEntity<TutorialDetails> createDetails(@PathVariable(value = "tutorialId") Long tutorialId,
      @RequestBody TutorialDetails detailsRequest) {
    Tutorial tutorial = tutorialRepository.findById(tutorialId)
        .orElseThrow(() -> new ResourceNotFoundException("没有找到id="+ tutorialId+"的教程详细信息"));

    detailsRequest.setCreatedOn(new java.util.Date());
    detailsRequest.setTutorial(tutorial);
    TutorialDetails details = detailsRepository.save(detailsRequest);

    return new ResponseEntity<>(details, HttpStatus.CREATED);
  }

  @PutMapping("/details/{id}")
  public ResponseEntity<TutorialDetails> updateDetails(@PathVariable("id") long id,
      @RequestBody TutorialDetails detailsRequest) {
    TutorialDetails details = detailsRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("没有找到id："+ id));

    details.setCreatedBy(detailsRequest.getCreatedBy());

    return new ResponseEntity<>(detailsRepository.save(details), HttpStatus.OK);
  }

  @DeleteMapping("/details/{id}")
  public ResponseEntity<HttpStatus> deleteDetails(@PathVariable("id") long id) {
    detailsRepository.deleteById(id);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping("/tutorials/{tutorialId}/details")
  public ResponseEntity<TutorialDetails> deleteDetailsOfTutorial(@PathVariable(value = "tutorialId") Long tutorialId) {
    if (!tutorialRepository.existsById(tutorialId)) {
      throw new ResourceNotFoundException("没有找到tutorialId="+ tutorialId );
    }

    detailsRepository.deleteByTutorialId(tutorialId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
