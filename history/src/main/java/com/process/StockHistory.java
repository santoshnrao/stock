package com.process;

import com.db.mongo.ManageCollection;
import com.db.mongo.MongoConnect;
import com.stock.integrate.StockExtractor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Santosh on 6/29/15.
 * Get the latest stock history information and update
 */
public class StockHistory implements Runnable{

    private int extractionIntervalUnit = Calendar.YEAR;
    private int extractionInterval = 1; // Max of one year is supported :-(


    private String _stockID ;
    private Calendar _lastExtractionDate;

    public StockHistory(String stockID, Calendar lastExtractionDate){
        _stockID = stockID ;
        _lastExtractionDate = lastExtractionDate;

    }

    public void updateStockInfo(String StockID){

        SimpleDateFormat dateFormatter = new SimpleDateFormat("YYYY-MM-dd");

        Calendar currentDate = new GregorianCalendar();

        ManageCollection manageCollection = new ManageCollection();
        JSONArray stockList = manageCollection.getStockInfo(StockID);
        if(stockList.length() > 0){
            JSONObject stockInfo = stockList.getJSONObject(0);
            stockInfo.remove(ManageCollection.COLUMN_ID);
            stockInfo.put(ManageCollection.COLUMN_STOCK_END_DATE, dateFormatter.format(currentDate.getTime()));
            manageCollection.updateStockInfo(stockInfo);
        }


    }

    /**
     * udpate a stock to current date
     * @param StockID
     * @param lastExtractionDate
     * @return true;
     */
    private boolean updateToCurrentDate(String StockID, Calendar lastExtractionDate){

        /************ Initialize date times ****************/

        Calendar startDate = new GregorianCalendar();
        Calendar endDate = (Calendar) lastExtractionDate.clone();
        Calendar nextInterval = (Calendar) startDate.clone();

        while(nextInterval.getTimeInMillis() >= endDate.getTimeInMillis()){

            /************ determine next interval - Interval + 1 day ****************/
            nextInterval.add(extractionIntervalUnit,(-1*extractionInterval));
            nextInterval.add(Calendar.DATE, 1);
            if(nextInterval.getTimeInMillis() <= endDate.getTimeInMillis()){
                nextInterval = endDate;
            }

            /************ Get  stock data ****************/
            JSONArray stockPrices = getStockPrices(StockID,nextInterval,startDate);

            /************ Update records ****************/
            if(stockPrices != null){
                ManageCollection manageCollection = new ManageCollection();
                manageCollection.insertStock(stockPrices);
            }else{
                /************ If there are no further information exit ****************/
                return true;
            }

            /************ move to next interval ****************/
            startDate.add(extractionIntervalUnit,-1);
            nextInterval = (Calendar) startDate.clone();
        }

    return true;

    }

    /**
     * get Stock proces
     * @param StockID
     * @param startDate
     * @param endDate
     * @return stockPrices
     */
    private JSONArray getStockPrices(String StockID, Calendar startDate, Calendar endDate){
        SimpleDateFormat dateFormatter = new SimpleDateFormat("YYYY-MM-dd");
        String output = "Extraction .. " + StockID + " : " +
                        dateFormatter.format(startDate.getTime()) + " - " +
                        dateFormatter.format(endDate.getTime());
        System.out.println(output);
        StockExtractor _stockExtractor = new StockExtractor();

        return _stockExtractor.getHistoricStockData(StockID,startDate,endDate);

    }


    @Override
    public void run() {
        updateToCurrentDate(_stockID, _lastExtractionDate);
        updateStockInfo(_stockID);
    }
}
