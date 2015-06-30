package com.extract;

import com.db.mongo.MongoConnect;
import com.stock.integrate.StockExtractor;
import org.json.JSONArray;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Santosh on 6/29/15.
 */
public class Extractor {

    private StockExtractor _stockExtractor = new StockExtractor();
    private final Calendar _endDate  = new GregorianCalendar(1900,0,1);
    private MongoConnect _mongoConnect = new MongoConnect();

    public boolean updateToCurrentDate(String StockID, Calendar lastExtractionDate){

        //Time scale is in the reverse direction.


        Calendar startDate = (Calendar) lastExtractionDate.clone();
        Calendar currentDate = new GregorianCalendar();

        Calendar nextInterval = (Calendar) startDate.clone();

        while(startDate.getTimeInMillis() <= currentDate.getTimeInMillis()){


            nextInterval.add(Calendar.MONTH,1);

            if(nextInterval.getTimeInMillis() > currentDate.getTimeInMillis()){
                nextInterval = currentDate;
            }

            JSONArray quotesMonthly = getQuote(StockID,startDate,nextInterval);
            if(quotesMonthly != null){
                _mongoConnect.storeJsonArrray(quotesMonthly);
            }

            startDate.add(Calendar.MONTH,1);

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
