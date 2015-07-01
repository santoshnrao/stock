package com.extract;

import com.db.mongo.ManageCollection;
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
    private int extractionInterval = Calendar.YEAR;
    private final Calendar _endDate  = new GregorianCalendar(1900,0,1);
    private ManageCollection manageCollection = new ManageCollection();

    public void setExtractionInterval(int interval){
        extractionInterval = interval;

    }

    public boolean updateToCurrentDate(String StockID, Calendar lastExtractionDate){

        //Time scale is in the reverse direction.


        Calendar startDate = (Calendar) lastExtractionDate.clone();
        Calendar currentDate = new GregorianCalendar();

        Calendar nextInterval = (Calendar) startDate.clone();

        while(startDate.getTimeInMillis() <= currentDate.getTimeInMillis()){


            nextInterval.add(extractionInterval,1);

            if(nextInterval.getTimeInMillis() > currentDate.getTimeInMillis()){
                nextInterval = currentDate;
            }

            JSONArray stockPrices = getStockPrices(StockID,startDate,nextInterval);
            if(stockPrices != null){
                manageCollection.insertStock(stockPrices);
            }

            startDate.add(extractionInterval,1);

        }



    return true;

    }

    private JSONArray getStockPrices(String StockID, Calendar startDate, Calendar endDate){

        return _stockExtractor.getHistoricStockData(StockID,startDate,endDate);

    }



}
