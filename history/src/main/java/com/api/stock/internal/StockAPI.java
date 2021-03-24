package com.api.stock.internal;

import com.db.mongo.ManageCollection;
import org.json.JSONArray;

/**
 * Created by Santosh on 7/13/15.
 * Internal Stock API
 */
public class StockAPI {

    private static ManageCollection manageCollection = new ManageCollection();

    public String getStockHistory(String stockID){

        JSONArray stockHistory =  manageCollection.getStockHistory(stockID);
        return  stockHistory.toString();

        //return stockID;
    }
}
