package net.impulse.lib.database;

import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

public class MongoConnection{

    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    private MongoCollection<Document> cloud;
    private final Map<String, MongoCollection<Document>> collections = new HashMap<>();

    static{
        System.setProperty( "DEBUG.MONGO", "false" );
        System.setProperty( "DB.TRACE", "false" );
    }

    private void connect( String connectionString, String database ){
        this.mongoClient = MongoClients.create( connectionString );
        this.mongoDatabase = this.mongoClient.getDatabase( database );
        this.cloud = this.mongoDatabase.getCollection( "cloud" );
    }

    public void connect( String ip, int port, String database ){
        this.connect( String.format( "mongodb://%s:%d/%s", ip, port, database ), database );
        System.out.println( "Mongodb connected" );
    }

    public void close(){
        if ( this.mongoClient != null ){
            this.mongoClient.close();
        }
    }

    public MongoCollection<Document> getCollection( String collectionName ){
        if ( this.collections.containsKey( collectionName ) ){
            return this.collections.get( collectionName );
        } else{
            registerCollection( collectionName );
            return getCollection( collectionName );
        }
    }

    public void registerCollection( String collectionName ){
        if ( !this.collections.containsKey( collectionName ) ){
            this.collections.put( collectionName, this.mongoDatabase.getCollection( collectionName ) );
        }
    }
}
