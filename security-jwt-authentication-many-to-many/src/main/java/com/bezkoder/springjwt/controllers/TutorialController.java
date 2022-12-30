package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.exception.ResourceNotFoundException;
import com.bezkoder.springjwt.models.Tutorial;
import com.bezkoder.springjwt.repository.TutorialRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class TutorialController {


	 final TutorialRepository tutorialRepository;

	public TutorialController(TutorialRepository tutorialRepository) {
		this.tutorialRepository = tutorialRepository;
	}

	@GetMapping("/tutorials")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<List<Tutorial>> getAllTutorials(@RequestParam(required = false) String title) {
        //创建教程列表，初始化为ArrayList（）
		List<Tutorial> tutorials = new ArrayList<Tutorial>();
		//判断 title参数是否为空，如果是查询所有，否则模糊查询
		if (title == null)
			tutorialRepository.findAll().forEach(tutorials::add);
		else
			tutorialRepository.findByTitleContaining(title).forEach(tutorials::add);
		//如果数据库中教程是空的，返回204
		if (tutorials.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		//如果一切正常，返回列表和状态码
		return new ResponseEntity<>(tutorials, HttpStatus.OK);
	}

	@GetMapping("/tutorials/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") long id) {
		Tutorial tutorial = tutorialRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + id));

		return new ResponseEntity<>(tutorial, HttpStatus.OK);
	}

	@PostMapping("/tutorials")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial) {
		Tutorial _tutorial = tutorialRepository.save(new Tutorial(tutorial.getTitle(), tutorial.getDescription(), true));
		return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);
	}

	@PutMapping("/tutorials/{id}")
	@PreAuthorize("hasAnyRole('MODERATOR')")
	public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id") long id, @RequestBody Tutorial tutorial) {
		Tutorial _tutorial = tutorialRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + id));

		_tutorial.setTitle(tutorial.getTitle());
		_tutorial.setDescription(tutorial.getDescription());
		_tutorial.setPublished(tutorial.isPublished());

		return new ResponseEntity<>(tutorialRepository.save(_tutorial), HttpStatus.OK);
	}

	@DeleteMapping("/tutorials/{id}")
	public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
		tutorialRepository.deleteById(id);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/tutorials")
	public ResponseEntity<HttpStatus> deleteAllTutorials() {
		tutorialRepository.deleteAll();

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/tutorials/published")
	public ResponseEntity<List<Tutorial>> findByPublished() {
		List<Tutorial> tutorials = tutorialRepository.findByPublished(true);

		if (tutorials.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(tutorials, HttpStatus.OK);
	}
}