package com.test.process;

import com.db.mongo.ManageCollection;
import com.process.StockHistory;
import com.process.StockHistoryRun;
import com.process.StockInformation;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Santosh on 7/3/15.
 * Extraction of Stock History
 */
public class StockInfoUpdateTest {

//    @Test
    public void stockInfoUpdateTest(){
        boolean status = new StockInformation().updateStockListData();
        Assert.assertEquals(status,true);
    }

//    @Test
    public void stockHistoryUpdateTest(){

        ManageCollection manageCollection = new ManageCollection();
        // Clear data
        manageCollection.dropStockInfo();
        manageCollection.dropStockData();

        JSONArray testStockInfoData = new JSONArray();
        JSONObject yahooStock = new JSONObject();
        yahooStock.put("Symbol" , "YHOO");
        testStockInfoData.put(yahooStock);

        yahooStock = new JSONObject();
        yahooStock.put("Symbol" , "AAPL");
        testStockInfoData.put(yahooStock);


        yahooStock = new JSONObject();
        yahooStock.put("Symbol" , "N");
        testStockInfoData.put(yahooStock);

        yahooStock = new JSONObject();
        yahooStock.put("Symbol" , "AMBA");
        testStockInfoData.put(yahooStock);

        yahooStock = new JSONObject();
        yahooStock.put("Symbol" , "DATA");
        testStockInfoData.put(yahooStock);

        yahooStock = new JSONObject();
        yahooStock.put("Symbol" , "FB");
        testStockInfoData.put(yahooStock);

        manageCollection.upsertStockInfo(testStockInfoData);

        boolean status = new StockHistoryRun().updateAllStocksToCurrentDate();
        Assert.assertEquals(status,true);
    }

//    @Test
    public  void stockHistoryExtractForAll(){

        ManageCollection manageCollection = new ManageCollection();
        // Clear data
        //manageCollection.dropStockInfo();
        //manageCollection.dropStockData();

        boolean status = new StockInformation().updateStockListData();
         status = new StockHistoryRun().updateAllStocksToCurrentDate();

        Assert.assertEquals(status,true);

    }

}
