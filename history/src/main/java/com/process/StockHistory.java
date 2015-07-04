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

    private StockExtractor _stockExtractor = new StockExtractor();
    private int extractionIntervalUnit = Calendar.YEAR;
    private int extractionInterval = 1; // Max of one year is supported :-(
    private ManageCollection manageCollection = new ManageCollection();

    private SimpleDateFormat dateFormatter = new SimpleDateFormat("YYYY-MM-dd");


    private String _stockID ;
    private Calendar _lastExtractionDate;

    public StockHistory(String stockID, Calendar lastExtractionDate){
        _stockID = stockID ;
        _lastExtractionDate = lastExtractionDate;

    }

    public void updateStockInfo(String StockID){
        Calendar currentDate = new GregorianCalendar();
        JSONArray stockDataList = new JSONArray();
        JSONObject stockInfo = new JSONObject();
        stockInfo.put(ManageCollection.COLUMN_STOCK_SYMBOL,StockID);
        stockInfo.put(ManageCollection.COLUMN_STOCK_END_DATE, dateFormatter.format(currentDate.getTime()));
        stockDataList.put(stockInfo);
        manageCollection.upsertStockInfo(stockDataList);
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
        String output = "Extraction .. " + StockID + " : " +
                        dateFormatter.format(startDate.getTime()) + " - " +
                        dateFormatter.format(endDate.getTime());
        System.out.println(output);
        return _stockExtractor.getHistoricStockData(StockID,startDate,endDate);

    }


    @Override
    public void run() {
        updateToCurrentDate(_stockID, _lastExtractionDate);
        updateStockInfo(_stockID);
    }
}
