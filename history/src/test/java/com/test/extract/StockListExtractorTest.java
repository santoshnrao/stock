package com.test.extract;

import com.stock.integrate.StockListExtractor;
import org.json.JSONArray;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Santosh on 7/1/15.
 * Test the stock list extraction information
 */
public class StockListExtractorTest {

//    @Test
    public void getStockAndPersistTest(){
        JSONArray stockList = new StockListExtractor().getStockList();
        Assert.assertNotNull(stockList);
    }

}
