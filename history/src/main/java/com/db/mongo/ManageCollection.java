package com.db.mongo;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.inject.Inject;

/**
 * Created by Santosh on 6/30/15.
 */
public class ManageCollection {


    private MongoConnect mongoConnect = new MongoConnect();

    public static final String TABLE_STOCK = "stock";
    public static final String TABLE_STOCK_INFO = "stock_info";


    public static final String COLUMN_STOCK_SYMBOL = "Symbol";
    public static final String COLUMN_STOCK_END_DATE = "End_Date";
    public static final String COLUMN_STOCK_START_DATE = "Start_Date";


    public boolean insertStock(JSONArray jsonArray){

        return mongoConnect.dbInsert(TABLE_STOCK,jsonArray);
    }

    public boolean insertStockInfo(JSONArray jsonArray){
        return mongoConnect.dbInsert(TABLE_STOCK_INFO,jsonArray);
    }

    public JSONArray getStockInfo(String stockID){
        JSONArray jsonArray =  mongoConnect.findByKey(TABLE_STOCK_INFO, COLUMN_STOCK_SYMBOL, stockID);
        return  jsonArray;
    }

    public boolean updateStockInfo(JSONObject jsonObject){

        return mongoConnect.updateCollectionByKey(TABLE_STOCK_INFO, COLUMN_STOCK_SYMBOL, jsonObject);

    }

    public JSONArray getAllStocksInfo(){
        JSONArray jsonArray = mongoConnect.findByKey(TABLE_STOCK_INFO,null, null);
        return  jsonArray;
    }

    public boolean upsertStockInfo(JSONArray jsonArray){
        return mongoConnect.upsertDocument(TABLE_STOCK_INFO,COLUMN_STOCK_SYMBOL,jsonArray);
    }

    public boolean dropStockInfo(){
        return mongoConnect.dropCollection(TABLE_STOCK_INFO);
    }

    public boolean dropStockData(){
        return mongoConnect.dropCollection(TABLE_STOCK);
    }

}
