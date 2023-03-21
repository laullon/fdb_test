package org.example;

import com.apple.foundationdb.Database;
import com.apple.foundationdb.FDB;
import com.apple.foundationdb.tuple.Tuple;

/**
 * Hello world!
 *
 */
public class App {
  public static void main( String[] args ) {
    FDB fdb = FDB.selectAPIVersion(600);
    fdb.options().setExternalClientDirectory("/Users/glaullon/wavefront/fdb_multi_client/6.0.18/");

    try(Database db = fdb.open()) {
      db.run(tr -> {
        tr.set(Tuple.from("hello").pack(), Tuple.from("world").pack());
        return null;
      });

      String hello = db.run(tr -> {
        byte[] result = tr.get(Tuple.from("hello").pack()).join();
        return Tuple.fromBytes(result).getString(0);
      });
      System.out.println("Hello " + hello);
    }
    System.out.println("Success!\n");
  }
}
