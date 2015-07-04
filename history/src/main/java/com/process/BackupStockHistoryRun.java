package com.process;

import com.db.mongo.ManageCollection;
import com.stock.integrate.StockExtractor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Santosh on 6/29/15.
 * Get the latest stock history information and update
 */
public class BackupStockHistoryRun {

    private StockExtractor _stockExtractor = new StockExtractor();
    private int extractionIntervalUnit = Calendar.YEAR;
    private int extractionInterval = 1; // Max of one year is supported :-(
    private final Calendar beginingOfTime  = new GregorianCalendar(1900,0,1);
    private ManageCollection manageCollection = new ManageCollection();

    private SimpleDateFormat dateFormatter = new SimpleDateFormat("YYYY-MM-dd");

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

        /************ Initialize date times ****************/

        Calendar startDate = new GregorianCalendar();
        Calendar endDate = (Calendar) lastExtractionDate.clone();
        Calendar nextInterval = (Calendar) startDate.clone();

        while(nextInterval.getTimeInMillis() >= endDate.getTimeInMillis()){

            /************ determine next interval - Interval + 1 day ****************/
            nextInterval.add(extractionIntervalUnit,(-1*extractionInterval));
            nextInterval.add(Calendar.DATE,1);
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
            startDate.add(extractionIntervalUnit,(-1*extractionInterval));
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

                /********* update stock information colletion with the current date *****************/
                if(status == true){
                    Calendar currentDate = new GregorianCalendar();
                    currentRecord.put(ManageCollection.COLUMN_STOCK_END_DATE, dateFormatter.format(currentDate.getTime()));
                    currentRecord.remove("_id");
                    manageCollection.updateStockInfo(currentRecord);
                }

            }catch (ParseException ex){
                ex.printStackTrace();
            }

        }
        return  true;
    }



}
