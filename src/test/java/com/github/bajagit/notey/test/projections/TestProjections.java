package com.github.bajagit.notey.test.projections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.junit.Test;

import com.github.bajagit.notey.projections.Projections;

import lotus.domino.Database;
import lotus.domino.NotesException;
import lotus.domino.View;
import lotus.domino.ViewEntry;
import lotus.domino.ViewEntryCollection;

public class TestProjections {
	
	
	@Test
	public void testAll() throws NotesException {
		Database db = mock(Database.class);
		View vw = mock(View.class);
		when(db.getView("view-name"))
			.thenReturn(vw);
		
		ViewEntryCollection col = mock(ViewEntryCollection.class);
		when(vw.getAllEntries())
			.thenReturn(col);
		
		ViewEntry entry = mock(ViewEntry.class);
		Vector<String> values = new Vector<>(1);
		values.add("name");
		when(entry.getColumnValues())
			.thenReturn(values);
		
		when(col.getNextEntry())
			.thenReturn(entry, (ViewEntry) null);
		
		List<Pojo> pojo = Projections.all(db, Pojo.class);
		assertThat(pojo).isNotNull();
		System.out.println(pojo.size());
		assertThat(pojo.size()).isEqualTo(1);
		assertThat(pojo.get(0).getVal()).isEqualTo("name");
	}
	
	@Test
	public void testAll_WrongType() throws NotesException {
		Database db = mock(Database.class);
		View vw = mock(View.class);
		when(db.getView("view-name"))
			.thenReturn(vw);
		
		ViewEntryCollection col = mock(ViewEntryCollection.class);
		when(vw.getAllEntries())
			.thenReturn(col);
		
		ViewEntry entry = mock(ViewEntry.class);
		Vector<Object> values = new Vector<>(1);
		values.add(new Date());
		when(entry.getColumnValues())
			.thenReturn(values);
		
		when(col.getNextEntry())
			.thenReturn(entry, (ViewEntry) null);
		
		List<Pojo> pojo = Projections.all(db, Pojo.class);
		assertThat(pojo).isNotNull();
		System.out.println(pojo.size());
		assertThat(pojo.size()).isEqualTo(1);
		assertThat(pojo.get(0).getVal()).isNull();
	}
	
	
	
	
	
}
