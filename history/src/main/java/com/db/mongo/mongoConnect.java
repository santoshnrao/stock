package com.db.mongo;

/**
 * Created by i029249 on 6/28/15.
 * Wrapper on Mongo db java library classes
 *
 */

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class MongoConnect {


    private MongoDatabase _db = null;
    private MongoClient _mongoClient = null;

    //Constants
    private  final String _DB_NAME = "test";

    private static MongoConnect oneInstance  ;

    /**
     * constructor
     */
    private MongoConnect(){
        connectMongo();
        oneInstance = this;
    }

    /**
     * connect to DB
     */
    private void connectMongo(){

        _mongoClient = new MongoClient( "localhost" , 27017 );

        _db = _mongoClient.getDatabase(_DB_NAME);

    }

    public static MongoConnect getMongoConnect(){
        if(oneInstance == null){
            oneInstance = new MongoConnect();
        }
        return oneInstance;
    }

    /**
     * find the object a key value
     * @param collectionName Collection Name
     * @param key Key Value
     * @param value Value
     * @return Found Table in JsonArray format
     */
    public JSONArray findByKey(String collectionName, String key, String value){

        FindIterable<Document>  queryResult = findCollectionByKey(collectionName, key, value);

        return  convertDocumentListToJsonArray(queryResult);
    }

    /**
     * Find a collection by key value
     * @param collectionName
     * @param key
     * @param value
     * @return
     */
    private FindIterable<Document> findCollectionByKey(String collectionName,String key, String value){

        MongoCollection table = _db.getCollection(collectionName);
        FindIterable<Document>  queryResult ;

        if(key != null & value != null ){
            BasicDBObject query = new BasicDBObject(key, value);
            queryResult = table.find(query);

        }else{
            queryResult = table.find();
        }

        return queryResult;

    }

    /**
     * Fidn a collection by Query Parameter
     * @param collectionName
     * @param query
     * @return
     */
    private FindIterable<Document> findCollecitonByQuery(String collectionName, BasicDBObject query){

        MongoCollection table = _db.getCollection(collectionName);
        FindIterable<Document> queryResult;
        queryResult = table.find(query);

        return queryResult;


    }

    /**
     * udpate a collection by the key provided
     * @param collectionName Collection Name
     * @param key Key
     * @param jsonObject document in JSON Format
     * @return Success or failure
     */
    public boolean updateCollectionByKey(String collectionName, String key, JSONObject jsonObject){

        try {

            MongoCollection table = _db.getCollection(collectionName);
            Document updateDocument = convertToDocument(jsonObject);
            String value = jsonObject.get(key).toString();

            Document searchQuery = new Document();
            searchQuery.put(key, value);
            table.findOneAndReplace(searchQuery,updateDocument);

        }catch (Exception ex){
            ex.printStackTrace();
            return  false;
        }
        return true;
    }

    /**
     * convert Document to JSONArray
     * @param documentList
     * @return
     */
    private JSONArray convertDocumentListToJsonArray(FindIterable<Document> documentList){

        if(documentList == null){
            return null;
        }

        JSONArray jsonArray = new JSONArray();

        for(Document doc:documentList){
            JSONObject jsonObject = new JSONObject();
            Set<String> keySet = doc.keySet();
            for(String key:keySet){
                jsonObject.put(key,doc.get(key));
            }
            jsonArray.put(jsonObject);
        }
        return jsonArray;
    }

    public boolean upsertDocument(String collectionName , String key, JSONArray jsonArray){
        // Get Collection
        com.mongodb.DBCollection collection = _mongoClient.getDB(_DB_NAME).getCollection(collectionName);

        // Get  BulkWriteOperation by accessing the mongodb com.mongodb.DBCollection class on mycol //Collection

        BulkWriteOperation  bulkWriteOperation= collection.initializeUnorderedBulkOperation();
        BulkWriteRequestBuilder bulkWriteRequestBuilder ;
        BulkUpdateRequestBuilder updateReq ;

        //perform the upsert  operation in the loop to add objects for bulk execution

        for ( int i=0 ; i< jsonArray.length(); i++)
        {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            // get a bulkWriteRequestBuilder by issuing find on the mycol with _id

            bulkWriteRequestBuilder=bulkWriteOperation.find(new BasicDBObject(key,jsonObject.get(key)));

            // get hold of upsert operation from bulkWriteRequestBuilder
            updateReq=  bulkWriteRequestBuilder.upsert();
            BasicDBObject updateDocument = convertToDBObject(jsonObject);

            updateReq.replaceOne(updateDocument);

        }
        // execute bulk operation on mycol collection
        BulkWriteResult result =bulkWriteOperation.execute();
        //System.out.print(result.toString());
        return  true;
    }

    /**
     * Insert to a db collection
     * @param collectionName
     * @param jsonArray
     * @return
     */
    public boolean dbInsert(String collectionName, JSONArray jsonArray){

        MongoCollection table = _db.getCollection(collectionName);

        List<Document> listDocument = convertJsonArrayToDocumentList(jsonArray);

        try {
            table.insertMany(listDocument);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;

    }

    /**
     * drop a collection
     * @param collectionName
     * @return
     */
    public boolean dropCollection(String collectionName){
        _db.getCollection(collectionName).drop();
        return  true;
    }

    /**
     * convert json array to document array
     * @param jsonArray
     * @return
     */
    private List<Document> convertJsonArrayToDocumentList(JSONArray jsonArray){

        List<Document> listDocument = new ArrayList<Document>();

        for(int i = 0 ;i <jsonArray.length();i++){

            listDocument.add(convertToDocument(jsonArray.getJSONObject(i)));

        }

        return  listDocument;

    }

    /**
     * convert json to document
     * @param jsonObject
     * @return document
     */
    private Document convertToDocument(JSONObject jsonObject){
        Document document = new Document();
        Iterator<String> jsonKeys = jsonObject.keys();
        while(jsonKeys.hasNext()){
            String key = jsonKeys.next();
            document.append(key, jsonObject.get(key).toString()) ;
        }

        return document;

    }

    /**
     * Convert a json object to DB Object
     * @param jsonObject
     * @return BasicDBObject
     */
    private BasicDBObject convertToDBObject(JSONObject jsonObject){
        BasicDBObject document = new BasicDBObject();

        for(String key: jsonObject.keySet()){
            document.put(key,jsonObject.get(key));
        }
        return  document;

    }


}
