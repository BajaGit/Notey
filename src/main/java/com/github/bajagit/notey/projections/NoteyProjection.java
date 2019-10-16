package com.github.bajagit.notey.projections;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a class as a NoteyProjection type.
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface NoteyProjection {
  /**
   * The Notes viewname to project
   * 
   * @return Notes viewname
   */
  String value();
}
