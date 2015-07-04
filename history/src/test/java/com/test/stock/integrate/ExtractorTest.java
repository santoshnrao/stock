package com.test.stock.integrate;

import com.stock.integrate.StockListExtractor;
import junit.framework.Assert;
import org.json.JSONArray;
import org.junit.Test;

/**
 * Created by i029249 on 7/3/15.
 */
public class ExtractorTest {


    public void updateStockListInformation(){

        StockListExtractor stockListExtractor = new StockListExtractor();
        JSONArray jsonArray =  stockListExtractor.getStockList();
        Assert.assertNotNull(jsonArray);

    }
}
