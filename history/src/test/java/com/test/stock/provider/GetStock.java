package com.test.stock.provider;


import com.stock.provider.yahoo.YahooQL;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import sun.util.calendar.BaseCalendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 * Created by i029249 on 6/28/15.
 */
public class GetStock {

    private YahooQL yQL = null ;
    private final  String _TEST_STOCK_SYMBOL = "YHOO";

    @Before
    public void setup(){
        yQL = new YahooQL();
    }

    @Test
    public void getYahooQuote(){

        String quoteResonse = yQL.getCurrentStockValue(_TEST_STOCK_SYMBOL);
        //System.out.println(quoteResonse);
        Assert.assertNotNull(quoteResonse);

    }

    @Test
    public  void getStockHistory(){
        //Set Start Date and End Date
        Calendar startDate = new GregorianCalendar(2012,0,1);
        Calendar endDate = new GregorianCalendar(2012,1,1);

        System.out.println("Staring get Stock History");
        String quoteHistoricData = null;
        quoteHistoricData  = yQL.getHistoricStock( _TEST_STOCK_SYMBOL,startDate,endDate);

        System.out.println(quoteHistoricData);
        Assert.assertNotNull(quoteHistoricData);
    }

}
