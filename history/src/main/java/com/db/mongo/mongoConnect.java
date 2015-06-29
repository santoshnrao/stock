package com.db.mongo;

/**
 * Created by i029249 on 6/28/15.
 */

import com.mongodb.BasicDBObject;
import com.mongodb.BulkWriteOperation;
import com.mongodb.BulkWriteResult;
import com.mongodb.Cursor;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ParallelScanOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

import static java.util.concurrent.TimeUnit.SECONDS;

public class MongoConnect {


    private MongoDatabase _db = null;
    private MongoClient _mongoClient = null;

    //Constants
    private  final String _DB_NAME = "test";
    private final String _DB_STOCK_TABLE = "stock";

    public MongoConnect(){
        connectMongo();
    }

    private void connectMongo(){

        _mongoClient = new MongoClient( "localhost" , 27017 );

        _db = _mongoClient.getDatabase(_DB_NAME);

    }

    private String getStockByID(String stockID){
        _db.getCollection("stock");
        return stockID;
    }

    public boolean storeData(){
        _storeStockData("sampleData");
        return  true;
    }

    private boolean _storeStockData(String sStockData){

        MongoCollection table = _db.getCollection(_DB_STOCK_TABLE);
        Document doc = new Document("stockid", "APPL");
        doc.append("date", "1980-01-01");
        doc.append("open", 1);
        doc.append("high", 2);
        doc.append("low", 2);
        doc.append("close", 2);
        doc.append("volume", 2);
        doc.append("adj_vol", 2);



        table.insertOne(doc);

        return true;
    }

    public boolean storeJsonArrray(JSONArray jsonArray){

        MongoCollection table = _db.getCollection(_DB_STOCK_TABLE);

        List<Document> listDocument = new ArrayList<Document>();

        for(int i = 0 ;i <jsonArray.length();i++){

            listDocument.add(convertToDocument(jsonArray.getJSONObject(i)));

        }

        try {
            table.insertMany(listDocument);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }



        return true;

    }

    public Document convertToDocument(JSONObject jsonObject){
        Document document = new Document();
        Iterator<String> jsonKeys = jsonObject.keys();
        while(jsonKeys.hasNext()){
            String key = jsonKeys.next();
            document.append(key, jsonObject.get(key).toString()) ;
        }

        return document;

    }


}
