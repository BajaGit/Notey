package com.github.bajagit.notey.test.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Vector;

import org.junit.Test;

import com.github.bajagit.notey.exception.NoteyRuntimeException;
import com.github.bajagit.notey.util.NoteyUtils;

import lotus.domino.Base;
import lotus.domino.NotesException;

public class TestNoteyUtils {

	class MockBase implements Base {

		private final String name;
		private int recycleCounter = 0;
		
		public MockBase(String name) {
			this.name = name;
		}
		
		@Override
		public void recycle() throws NotesException {
			++recycleCounter;
		}

		@SuppressWarnings("rawtypes")
		@Override
		public void recycle(Vector arg0) throws NotesException {}
		
	}
	
	@Test
	public void testWith() {
		NoteyUtils.with( new MockBase("hello"), with -> assertThat(with.name).isEqualTo("hello"));
	}
	
	@Test(expected = NoteyRuntimeException.class)
	public void testWith_wrapsExceptions() {
		NoteyUtils.with( new MockBase("hello"),
				with -> {throw new Exception("waht");});
	}
	@Test
	public void testMapWith() {
		String mapped = NoteyUtils.mapWith( new MockBase("hello"), with -> with.name + "Mapped");
		assertThat(mapped).isEqualTo("helloMapped");
	}
	
	@Test(expected = NoteyRuntimeException.class)
	public void testMapWith_wrapsExceptions() {
		NoteyUtils.mapWith( new MockBase("hello"),
				with -> {throw new Exception("waht");});
	}
	
	@Test
	public void testTyped() {
		Vector<?> vecIn = new Vector<>(1);
		// if the typing failed, we could not call .add() here
		NoteyUtils.typed(vecIn).add("String");
		assertThat(vecIn.get(0)).isEqualTo("String");
	}
	
	@Test
	public void testTyped_nullVector() {
		Vector<Object> vecIn = NoteyUtils.typed(null);
		assertThat(vecIn).isNull();
	}
	
	@Test
	public void testTyped_nullType() {
		// Null Generic arguments default to Object, so this code still works
		Vector<?> vecIn = new Vector<>(1);
		// if the typing failed, we could not call .add() here
		NoteyUtils.typed(vecIn).add("String");
		assertThat(vecIn.get(0)).isEqualTo("String");
	}
	
	@Test
	public void testRecycle() {
		MockBase mock = new MockBase("mock");
		MockBase mock1 = new MockBase("mock1");
		
		NoteyUtils.recycle(mock);
		assertThat(mock.recycleCounter).isEqualTo(1);
		NoteyUtils.recycle(mock, mock1);
		assertThat(mock.recycleCounter).isEqualTo(2);
		assertThat(mock1.recycleCounter).isEqualTo(1);
	}
}
