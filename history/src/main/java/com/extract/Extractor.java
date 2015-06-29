package com.extract;

import com.stock.integrate.StockExtractor;
import org.json.JSONArray;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by i029249 on 6/29/15.
 */
public class Extractor {

    private StockExtractor _stockExtractor = new StockExtractor();
    private final Calendar _endDate  = new GregorianCalendar(1900,0,1);

    public boolean updateToCurrentDate(String StockID, Calendar lastExtractionDate){
        Calendar endDate = null;

        if(lastExtractionDate == null){
            endDate = _endDate;
        }

        Calendar currentDate = new GregorianCalendar();
        Calendar curDate = currentDate ;

        Calendar nextMonth = currentDate;
        nextMonth.add(Calendar.MONTH, -1);

        while(endDate.getTimeInMillis() > nextMonth.getTimeInMillis()){

            JSONArray quotesMontly = getQuote(StockID,nextMonth,curDate);

        }



    return true;

    }

    private JSONArray getQuote(String StockID, Calendar startDate, Calendar endDate){

        return _stockExtractor.getHistoricStockData(StockID,startDate,endDate);

    }

    private boolean _storeData(JSONArray jsonArray){
        return true;
    }






}
