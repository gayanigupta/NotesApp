package com.gayanigupta.springboot.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.gayanigupta.springboot.model.Notes;



@Service("notesService")
public class NoteServiceImpl implements NotesService{
	
	private static final AtomicLong counter = new AtomicLong();
	
	private static List<Notes> notes;
	
	static{
		notes= populateDummyNotes();
	}

	public List<Notes> findAllNotes() {
		return notes;
	}
	
	public Notes findById(long id) {
		for(Notes note : notes){
			if(note.getId() == id){
				return note;
			}
		}
		return null;
	}
	
	public Notes findByBody(String name) {
		for(Notes note : notes){
			if(note.getBody().equalsIgnoreCase(name)){
				return note;
			}
		}
		return null;
	}
	
	public void saveNote(Notes note) {
		note.setId(counter.incrementAndGet());
		notes.add(note);
	}

	public void updateNote(Notes note) {
		int index = notes.indexOf(note);
		notes.set(index, note);
	}

	public void deleteNoteById(long id) {
		
		for (Iterator<Notes> iterator = notes.iterator(); iterator.hasNext(); ) {
		    Notes user = iterator.next();
		    if (user.getId() == id) {
		        iterator.remove();
		    }
		}
	}

	public boolean isNoteExist(Notes user) {
		return findByBody(user.getBody())!=null;
	}
	
	public void deleteAllNotes(){
		notes.clear();
	}

	private static List<Notes> populateDummyNotes(){
		List<Notes> notes = new ArrayList<Notes>();
		notes.add(new Notes(counter.incrementAndGet(),"Pick up Milk"));
		notes.add(new Notes(counter.incrementAndGet(),"Shop Ice cream"));
		notes.add(new Notes(counter.incrementAndGet(),"Learn Java"));
		notes.add(new Notes(counter.incrementAndGet(),"Watch a movie"));
		return notes;
	}



}
