package com.github.bajagit.notey;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

public class Main {


  private static class Ref {

  }


  private static class Proj<T extends Full> extends Ref {

    @SuppressWarnings("unchecked")
    public Class<T> token() {
      return (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }


  }


  private static class Full extends Ref {

  }

  // @NoteyDocument FormName
  private static class Pojo extends Full {

  }

  // @NoteyProjection ViewName = "asd", doctype =
  private static class ProjoProj extends Proj<Pojo> {

  }

  public static void main(final String[] args) throws NoSuchFieldException, SecurityException {
    // TODO Auto-generated method stub
    // System.out.println(new Proj().token());
    // final ProjoProj var = new ProjoProj();

    System.out.println("hi");
    class Stuff {
      public String a;
      public Date b;
      public List<String> c;
    }

    final Field c = new Stuff().getClass().getDeclaredField("c");

    System.out.println(c.getType());
    System.out.println(c.getType().getGenericSuperclass());
    final Type t = c.getGenericType();
    final ParameterizedType pt = (ParameterizedType) t;
    System.out.println(pt.getActualTypeArguments()[0]);

    // Seq.seq(on(new Stuff()).fields())
    // .forEach(t -> {
    // System.out.println(t.v1);
    // System.out.println(t.v2.type() + "");
    //
    // });

    System.out.println("bye");
  }

}
