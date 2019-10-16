package com.github.bajagit.notey.projections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import org.jooq.lambda.Seq;
import org.jooq.lambda.Unchecked;

import com.github.bajagit.notey.util.NoteyUtils;

import lotus.domino.Database;
import lotus.domino.View;
import lotus.domino.ViewEntry;
import lotus.domino.ViewEntryCollection;

public final class Projections {

  private Projections() {
    throw new UnsupportedOperationException("no");
  }

  public static <Type extends DocumentProjection> List<Type> all(Database database, Class<Type> type) {
    return Projections.byKey(database, type, null);
  }

  public static <Type extends DocumentProjection> List<Type> byKey(Database database, Class<Type> type, Object key) {
    Vector<Object> keys = new Vector<>(1);
    keys.add(key);
    return Projections.byKey(database, type, keys);
  }

  public static <Type extends DocumentProjection> List<Type> byKey(Database database, Class<Type> type, Vector<Object> keys) {
    NoteyProjection viewDef = type.getAnnotation(NoteyProjection.class);
    if (viewDef == null) {
      return Collections.emptyList();
    }

    final List<Type> result = new ArrayList<>();
    NoteyUtils.with(database, db -> {
      View view = db.getView(viewDef.value());
      ViewEntryCollection col;
      if (keys != null) {
        col = view.getAllEntriesByKey(keys);
      } else {
        col = view.getAllEntries();
      }
      ViewEntry entry;
      while ((entry = col.getNextEntry()) != null) {
        Type nu = type.getConstructor().newInstance();
        Seq.seq(NoteyUtils.typed(entry.getColumnValues()))
            .zip(
                Seq.of(type.getDeclaredFields()))
            .forEach(Unchecked.consumer(
                t -> {
                  t.v2.setAccessible(true);
                  // TODO: unmatched value types are currently discarded, fallback possible?
                  if (t.v2.getType().equals(t.v1.getClass())) {
                    t.v2.set(nu, t.v1);
                  }
                }));
        nu.setDocumentUNID(entry.getUniversalID());
        result.add(nu);
        NoteyUtils.recycle(entry);
      }
      NoteyUtils.recycle(col, view);
    });
    return result;
  }

}
