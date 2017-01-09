package com.gayanigupta.springboot.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.gayanigupta.springboot.model.Notes;
import com.gayanigupta.springboot.service.NotesService;
import com.gayanigupta.springboot.util.CustomErrorType;

@RestController
@RequestMapping("/api")
public class RestApiController {

	public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

	@Autowired
	NotesService noteService;
	
	@RequestMapping(value = "/notes", method = RequestMethod.GET)
	public ResponseEntity<List<Notes>> listAllNotes() {
		List<Notes> notes = noteService.findAllNotes();
			if (notes.isEmpty()) {
				return new ResponseEntity(HttpStatus.NO_CONTENT);
			}
		return new ResponseEntity<List<Notes>>(notes, HttpStatus.OK);
	}


	@RequestMapping(value = "/notes/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getNote(@PathVariable("id") long id) {
		logger.info("Fetching Note with id {}", id);
		Notes Note = noteService.findById(id);
			if (Note == null) {
				logger.error("Note with id {} not found.", id);
				return new ResponseEntity(new CustomErrorType("Note with id " + id 
						+ " not found"), HttpStatus.NOT_FOUND);
			}
		return new ResponseEntity<Notes>(Note, HttpStatus.OK);
	}


	@RequestMapping(value = "/notes", method = RequestMethod.POST)
	public ResponseEntity<?> createNote(@RequestBody Notes note, UriComponentsBuilder ucBuilder) {
		logger.info("Creating Note : {}", note);

			if (noteService.isNoteExist(note)) {
				logger.error("Unable to create. A Note with name {} already exist", note.getBody());
				return new ResponseEntity(new CustomErrorType("Unable to create. A Note with name " + 
				note.getBody() + " already exist."),HttpStatus.CONFLICT);
			}
		noteService.saveNote(note);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/notes/{id}").buildAndExpand(note.getId()).toUri());

		return new ResponseEntity<Notes>(note, HttpStatus.OK);
	}

	
	@RequestMapping(value = "/notes/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateNote(@PathVariable("id") long id, @RequestBody Notes note) {
		logger.info("Updating Note with id {}", id);

		Notes currentNote = noteService.findById(id);

			if (currentNote == null) {
				logger.error("Unable to update. Note with id {} not found.", id);
				return new ResponseEntity(new CustomErrorType("Unable to upate. Note with id " + id + " not found."),
						HttpStatus.NOT_FOUND);
			}

		currentNote.setBody(note.getBody());
		
		noteService.updateNote(currentNote);
		return new ResponseEntity<Notes>(currentNote, HttpStatus.OK);
	}

	@RequestMapping(value = "/notes/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteNote(@PathVariable("id") long id) {
		logger.info("Fetching & Deleting Note with id {}", id);

		Notes Note = noteService.findById(id);
		if (Note == null) {
			logger.error("Unable to delete. Note with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to delete. Note with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		noteService.deleteNoteById(id);
		return new ResponseEntity<Notes>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/notes", method = RequestMethod.DELETE)
	public ResponseEntity<Notes> deleteAllNotes() {
		logger.info("Deleting All Notes");

		noteService.deleteAllNotes();
		return new ResponseEntity<Notes>(HttpStatus.NO_CONTENT);
	}

}