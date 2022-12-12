package com.example.demojparestcrud.controller;

import com.example.demojparestcrud.exception.ResourceNotFoundException;
import com.example.demojparestcrud.model.Details;
import com.example.demojparestcrud.model.Tutorial;

import com.example.demojparestcrud.repository.DetailsRepository;
import com.example.demojparestcrud.repository.TutorialRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class TutorialDetailsController {
 private  final DetailsRepository detailsRepository;
 private  final TutorialRepository tutorialRepository;

  public TutorialDetailsController(DetailsRepository detailsRepository, TutorialRepository tutorialRepository) {
    this.detailsRepository = detailsRepository;
    this.tutorialRepository = tutorialRepository;
  }

  @GetMapping({ "/details/{id}", "/tutorials/{id}/details" })
  public ResponseEntity<Details> getDetailsById(@PathVariable(value = "id") Long id) {
    Details details = detailsRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("没有找到id="+ id+"的教程详细信息" ));

    return new ResponseEntity<>(details, HttpStatus.OK);
  }

  @PostMapping("/tutorials/{tutorialId}/details")
  public ResponseEntity<Details> createDetails(@PathVariable(value = "tutorialId") Long tutorialId,
      @RequestBody Details detailsRequest) {
    Tutorial tutorial = tutorialRepository.findById(tutorialId)
        .orElseThrow(() -> new ResourceNotFoundException("没有找到id="+ tutorialId+"的教程详细信息"));
    detailsRequest.setCreatedTimeOn(new java.util.Date());
    detailsRequest.setTutorialId(tutorial);
    Details details = detailsRepository.save(detailsRequest);

    return new ResponseEntity<>(details, HttpStatus.CREATED);
  }

  @PutMapping("/details/{id}")
  public ResponseEntity<Details> updateDetails(@PathVariable("id") long id,
      @RequestBody Details detailsRequest) {
    Details details = detailsRepository.findById(id)
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
  public ResponseEntity<Details> deleteDetailsOfTutorial(@PathVariable(value = "tutorialId") Long tutorialId) {
    if (!tutorialRepository.existsById(tutorialId)) {
      throw new ResourceNotFoundException("没有找到tutorialId="+ tutorialId );
    }
    detailsRepository.deleteByTutorialId(tutorialId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
