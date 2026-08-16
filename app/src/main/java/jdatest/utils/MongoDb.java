package jdatest.utils;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import io.github.cdimascio.dotenv.Dotenv;
import jdatest.utils.SLF4J.logModes;

public class MongoDb {
  static Dotenv env = Dotenv.load();
  private static MongoClient dbClient = null;
  private static final String Uri = "mongodb+srv://jaxonhulksmash_db_user:%s@cluster0.emt6lfh.mongodb.net/?appName=Cluster0";
  
  public static MongoClient get() {
    if (dbClient == null) {
      dbClient = MongoClients.create(Uri.formatted(env.get("MONGODB_CLUSTER_PASSWORD")));
      SLF4J.Log("Opened new database client @ Cluster0", logModes.INFO);
    }
    return dbClient;
  }
  public static MongoDatabase getDb(String dbName) {
    return get().getDatabase(dbName);
  }
  public static void close() {
    if (dbClient != null) {
      dbClient.close();
    }
  }
}
