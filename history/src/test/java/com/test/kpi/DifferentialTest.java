package com.test.kpi;

import com.db.mongo.ManageCollection;
import com.kpi.Differential;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

/**
 * Created by i029249 on 7/15/15.
 */
public class DifferentialTest {

    @Test
    public void getDifferential(){

        Differential differential = new Differential();
        ManageCollection manageCollection = new ManageCollection();
        JSONArray stockHistory =  manageCollection.getStockHistory("N", ManageCollection.COLUMN_DATE);
        JSONObject stockDifferential =  differential.extractDifferential(stockHistory);

        System.out.println(stockDifferential.toString());


    }
}
