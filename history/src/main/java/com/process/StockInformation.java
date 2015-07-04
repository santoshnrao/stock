package com.process;

import com.db.mongo.ManageCollection;
import com.stock.integrate.StockListExtractor;
import org.json.JSONArray;

/**
 * Created by Santosh on 7/3/15.
 * update Stock list data extractor
 */
public class StockInformation {

    public boolean updateStockListData(){

        /******** Get the Stock List *************/
        StockListExtractor stockListExtractor = new StockListExtractor();
        JSONArray stockList = stockListExtractor.getStockList();

        /********* Update stock information *****************/

        ManageCollection manageCollection = new ManageCollection();
        manageCollection.upsertStockInfo(stockList);

        return  true;

    }
}
