package com.github.bajagit.notey.util;

import java.util.Optional;
import lotus.domino.Document;

public class NoteyDocumentWrapper implements AutoCloseable {

  private final Document doc;

  public NoteyDocumentWrapper(final Document doc) {
    this.doc = doc;
  }

  public boolean hasItem(final String name) {
    try {
      return this.doc.hasItem(name);
    } catch (final Exception e) {
      return false;
    }
  }



  @Override
  public void close() {
    NoteyUtils.recycle(this.doc);
  }

  public Optional<String> getString(final String name) {
    try {
      return Optional.ofNullable(this.doc.getItemValueString(name));
    } catch (final Exception e) {
      e.printStackTrace();
    }
    return Optional.empty();
  }

}
