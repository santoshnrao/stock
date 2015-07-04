package com.stock.integrate;

import com.db.mongo.ManageCollection;
import com.db.mongo.MongoConnect;
import com.ftp.Ftp;
import com.stock.provider.IStockList;
import com.stock.provider.nasdaq.NasdaqStockUpdates;
import org.json.JSONArray;
import org.json.JSONObject;


/**
 * Created by Santosh on 7/1/15.
 * Get the stock list
 */
public class StockListExtractor {

    private IStockList nasdaqStockUpdates  = new NasdaqStockUpdates();

    public JSONArray getStockList(){

        JSONArray stockList = nasdaqStockUpdates.getStockList();
        return stockList;

    }

}
