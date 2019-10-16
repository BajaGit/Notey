package com.github.bajagit.notey.documents;

import lombok.Getter;
import lombok.Setter;

public abstract class DocumentReference {
  @Getter
  @Setter
  private String documentUNID = null;
}
