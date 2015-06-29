package com.test.db;

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

    @Before
    public void setupMongoTest(){

        _mongoConnect = new MongoConnect();


    }
    @Test
    public void testStorage(){
        boolean storeSuccess =  _mongoConnect.storeData();
        Assert.assertEquals(storeSuccess,true);
    }

    @Test

    public void convertToDocument(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.append("stock_id", "APPL");
        jsonObject.append("high", 20.11);
        Document document =  _mongoConnect.convertToDocument(jsonObject);
        Assert.assertNotNull(document);
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
        Assert.assertEquals(status,true );
    }

}
