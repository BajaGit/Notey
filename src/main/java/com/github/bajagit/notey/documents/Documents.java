package com.github.bajagit.notey.documents;

import static org.joor.Reflect.accessible;
import static org.joor.Reflect.on;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;
import org.jooq.lambda.Seq;
import org.jooq.lambda.Unchecked;
import com.github.bajagit.notey.exception.NoteyRuntimeException;
import com.github.bajagit.notey.projections.DocumentProjection;
import com.github.bajagit.notey.util.NoteyDocumentWrapper;
import com.github.bajagit.notey.util.NoteyUtils;
import lotus.domino.Database;
import lotus.domino.Document;

public final class Documents {

  private Documents() {
    throw new UnsupportedOperationException("no");
  }


  public static Optional<Document> fromProjection(final Database db, final DocumentProjection proj) {
    try {
      return Optional.ofNullable(NoteyUtils.mapWith(db, notes -> notes.getDocumentByUNID(proj.getDocumentUNID())));
    } catch (final NoteyRuntimeException nr) {
      return Optional.empty();
    }
  }

  public <Type extends DocumentMirror> Optional<Type> load(final Database db, final DocumentProjection proj, final Class<Type> type) {

    on(type);

    Documents.fromProjection(db, proj)
        .map(doc -> {
          try (NoteyDocumentWrapper wrap = NoteyUtils.wrap(doc)) {
            final Type nu = type.getConstructor().newInstance();
            Seq.of(type.getDeclaredFields())
                .forEach(field -> {
                  if (wrap.hasItem(field.getName())) {
                    Fields.setFromDoc(wrap, nu, field);
                  }
                });
          } catch (final Exception e) {
            e.printStackTrace();
          }

          return null;
        });



    return Optional.empty();
  }

  private static class Fields {
    public static void setFromDoc(final NoteyDocumentWrapper wrap, final Object o, final Field field) {
      accessible(field);
      try {
        switch (field.getType().toString()) {
          case "java.lang.String":
            wrap.getString(field.getName())
                .ifPresent(Unchecked.consumer(val -> field.set(o, val)));
            break;
          case "java.util.List":
            final Type listType = ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
            // only string lists? or lists of all kinds of shit?
            break;
        };
      } catch (final Exception e) {
        e.printStackTrace();
      }
    }
  }

}
