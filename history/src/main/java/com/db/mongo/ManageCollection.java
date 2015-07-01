package com.db.mongo;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.inject.Inject;

/**
 * Created by Santosh on 6/30/15.
 */
public class ManageCollection {


    private MongoConnect mongoConnect = new MongoConnect();

    private final String _TABLE_STOCK = "stock";
    private final String _TABLE_STOCK_INFO = "stock_info";

    private final String _COLUMN_STOCK_SYMBOL = "Symbol";

    public boolean insertStock(JSONArray jsonArray){

        return mongoConnect.dbInsert(_TABLE_STOCK,jsonArray);
    }

    public boolean insertStockInfo(JSONArray jsonArray){
        return mongoConnect.dbInsert(_TABLE_STOCK_INFO,jsonArray);
    }

    public JSONArray getStockInfo(String stockID){
        JSONArray jsonArray =  mongoConnect.findByKey(_TABLE_STOCK_INFO, _COLUMN_STOCK_SYMBOL, stockID);
        return  jsonArray;
    }

    public boolean updateStockInfo(JSONObject jsonObject){

        return mongoConnect.updateCollectionByKey(_TABLE_STOCK_INFO, _COLUMN_STOCK_SYMBOL, jsonObject);

    }

}
