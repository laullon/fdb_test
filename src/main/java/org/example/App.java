package org.example;

import com.apple.foundationdb.Database;
import com.apple.foundationdb.FDB;
import com.apple.foundationdb.tuple.Tuple;

import java.io.File;

/**
 * Hello world!
 *
 */
public class App {
  public static void main( String[] args ) throws InterruptedException {
    File path = new File(System.getProperty("user.home"), "fdb_multi_client/" + (args.length>0 ? args[0] : ""));
    System.out.println("fdb_multi_client: -> "+path.getAbsolutePath());

    FDB fdb = FDB.selectAPIVersion(600);
    fdb.options().setExternalClientDirectory(path.getAbsolutePath());

    try(Database db = fdb.open()) {
      while (true){
      db.run(tr -> {
        tr.set(Tuple.from("hello").pack(), Tuple.from("world").pack());
        return null;
      });

      String hello = db.run(tr -> {
        byte[] result = tr.get(Tuple.from("hello").pack()).join();
        return Tuple.fromBytes(result).getString(0);
      });
      System.out.println("Hello " + hello);
      Thread.sleep(5000);}
    }
  }
}

