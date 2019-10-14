package com.github.bajagit.notey.util;

@FunctionalInterface
public interface NotesFunction1<T, R> {
	R apply(T t) throws Exception;
}