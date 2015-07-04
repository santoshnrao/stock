package com.process;

import com.db.mongo.ManageCollection;
import com.stock.integrate.StockExtractor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Santosh on 6/29/15.
 * Get the latest stock history information and update
 */
public class StockHistoryRun {

    private StockExtractor _stockExtractor = new StockExtractor();
    private int extractionIntervalUnit = Calendar.YEAR;
    private int extractionInterval = 1; // Max of one year is supported :-(
    private final Calendar beginingOfTime  = new GregorianCalendar(1900,0,1);
    private ManageCollection manageCollection = new ManageCollection();

    private SimpleDateFormat dateFormatter = new SimpleDateFormat("YYYY-MM-dd");

    private ArrayList<StockHistory> runningThreads = new ArrayList<>();

    private static final int _MAX_THREAD_NUMBER  = 10 ;

    public void setextractionIntervalUnit(int interval){
        extractionIntervalUnit = interval;

    }

    /**
     * udpate a stock to current date
     * @param StockID
     * @param lastExtractionDate
     * @return true;
     */
    public boolean updateToCurrentDate(String StockID, Calendar lastExtractionDate){

        StockHistory stockHistory = new StockHistory(StockID,lastExtractionDate);
//        Thread thread = new Thread(stockHistory);
        runningThreads.add(stockHistory);

        return true;

    }

    public boolean runThreadsSafe(){
        ExecutorService threadPool = Executors.newFixedThreadPool(_MAX_THREAD_NUMBER);


        //Spinoff threads
        for(StockHistory thread:runningThreads){
            //
            // thread.start();
            threadPool.execute(thread);
        }

        threadPool.shutdown();
        try {
            threadPool.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return  true;
    }


    /**
     * update all stocks to current date
     * @return success
     */
    public boolean updateAllStocksToCurrentDate(){
        /************ Get all stocks to update ****************/
        JSONArray jsonAllStocks = manageCollection.getAllStocksInfo();
        for (int i = 0; i < jsonAllStocks.length(); i++) {


            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            try {
                /************ Get  current stock information ****************/
                JSONObject currentRecord =jsonAllStocks.getJSONObject(i);
                String stockSymbol =  currentRecord.get(ManageCollection.COLUMN_STOCK_SYMBOL).toString();
                String sEndDate ;
                try {
                     sEndDate =  jsonAllStocks.getJSONObject(i).get(ManageCollection.COLUMN_STOCK_END_DATE).toString();
                }catch (Exception ex){
                    sEndDate = null;
                }


                /************ Get  current date ****************/
                Calendar lastExtractedDate = new GregorianCalendar();

                if(sEndDate!=null){
                    lastExtractedDate.setTime(dateFormatter.parse(sEndDate));
                }else{
                    lastExtractedDate = (Calendar) beginingOfTime.clone();
                }

                /*** Extract and persist stock information upto current date ***********/

                boolean status =  updateToCurrentDate(stockSymbol, lastExtractedDate);


            }catch (ParseException ex){
                ex.printStackTrace();
            }

        }

        runThreadsSafe();

        return  true;
    }



}
