package com.github.bajagit.notey.documents;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Attaches a Formname to a FullDocument
 * 
 * @author z00p
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface NoteyDocument {
  /**
   * The notes formname
   * 
   * @return the form-name
   */
  String value();
}
