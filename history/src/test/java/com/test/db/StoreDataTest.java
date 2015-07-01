package com.test.db;

import com.db.mongo.ManageCollection;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import junit.framework.Assert;

import com.db.mongo.MongoConnect;

/**
 * Created by Santosh on 6/28/15.
 */
public class StoreDataTest {

    private  MongoConnect _mongoConnect = null;
    private ManageCollection manageCollection = null;

    @Before
    public void setupMongoTest(){

        _mongoConnect = new MongoConnect();
        manageCollection = new ManageCollection();


    }
    @Test
    public void testStorage(){
        boolean storeSuccess =  _mongoConnect.storeData();
        Assert.assertEquals(storeSuccess, true);
    }

    @Test

    public  void  storeJsonArray(){

        JSONArray jsonArray = new JSONArray();

        JSONObject jsonObject = new JSONObject();
        jsonObject.append("stock_id", "APPL");
        jsonObject.append("high", 20.11);
        jsonArray.put(jsonObject);

        jsonObject = new JSONObject();
        jsonObject.append("stock_id", "YHOO");
        jsonObject.append("high", 20.11);

        jsonArray.put(jsonObject);


        boolean status =  _mongoConnect.storeJsonArrray(jsonArray);
        Assert.assertEquals(status, true);
    }


    @Test
    public void insertStockInfoTest(){
        JSONArray jsonArray = new JSONArray();
        JSONObject stockInfo = new JSONObject();
        stockInfo.put("Symbol", "YHOO");
        stockInfo.put("Start_Date", "2000-01-01");
        stockInfo.put("End_Date", "2010-01-01");
        jsonArray.put(stockInfo);

        stockInfo = new JSONObject();
        stockInfo.put("Symbol", "APPL");
        stockInfo.put("Start_Date", "2000-01-01");
        stockInfo.put("End_Date", "2010-01-01");
        jsonArray.put(stockInfo);

        boolean status =  manageCollection.insertStockInfo(jsonArray);
        Assert.assertEquals(status, true);
    }

    @Test
    public void getByID(){

        JSONArray stockInfo =  manageCollection.getStockInfo("YHOO");
        System.out.println( stockInfo.toString() );
        Assert.assertNotNull(stockInfo);

    }

    @Test

    public void udpateStockInfoTest(){
        JSONObject stockInfo = new JSONObject();
        stockInfo.put("Symbol", "APPL");
        stockInfo.put("Start_Date", "2000-01-01");
        stockInfo.put("End_Date", "2012-01-01");

        boolean status = manageCollection.updateStockInfo(stockInfo);
        Assert.assertEquals(status,true);
    }


}
