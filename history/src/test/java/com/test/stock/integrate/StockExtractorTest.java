package com.test.stock.integrate;

import com.stock.integrate.StockExtractor;
import com.transform.StockToDB;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Santosh on 6/29/15.
 */
public class StockExtractorTest {

    private StockExtractor stockExtractor = new StockExtractor();
    private final  String _TEST_STOCK_SYMBOL = "YHOO";
    private Calendar startDate = new GregorianCalendar(2012,0,1);
    private Calendar endDate = new GregorianCalendar(2012,1,1);


//    @Before
    public void setup(){
        String a  = "tt";
    }

//    @Test
    public  void  getStockAndConvert(){

        JSONArray stockData =  stockExtractor.getHistoricStockData(_TEST_STOCK_SYMBOL,startDate,endDate);

        Assert.assertNotNull(stockData);
    }
}
