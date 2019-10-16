package com.github.bajagit.notey.util;

@FunctionalInterface
public interface NotesConsumer<T> {
  void accept(T t) throws Exception;
}
