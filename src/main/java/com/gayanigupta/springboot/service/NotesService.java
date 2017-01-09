package com.gayanigupta.springboot.service;


import java.util.List;

import com.gayanigupta.springboot.model.Notes;

public interface NotesService {
	
	Notes findById(long id);
	
	Notes findByBody(String body);
	
	void saveNote(Notes note);
	
	void updateNote(Notes note);
	
	void deleteNoteById(long id);

	List<Notes> findAllNotes();
	
	void deleteAllNotes();
	
	boolean isNoteExist(Notes note);
	
}
