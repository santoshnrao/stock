package com.stock.provider.nasdaq;

import com.ftp.Ftp;
import com.stock.provider.IStockList;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Satnosh on 7/3/15.
 * Get the stock from Nasdaq
 */
public class NasdaqStockUpdates implements IStockList{

    private static final String ftpServer = "ftp.nasdaqtrader.com";
    private static final String directory = "/SymbolDirectory";
    private static final String fileName = "nasdaqtraded.txt";

    /**
     * convert to Json Array
     * @param stockData
     * @return
     */
    private JSONArray convertData(String stockData){

        String[] stockHeader = null;

        String[] stockList = stockData.split("\\r?\\n");
        int i = 0 , j = 0;
        stockHeader = stockList[0].split("\\|");
        int columns = stockHeader.length;
        int rows = stockList.length;
        for(i = 0 ; i < columns; i++){
            stockHeader[i] = stockHeader[i].replaceAll("\\s+", "");
        }

        JSONArray jsonStockList = new JSONArray();

        for(i = 1 ; i<rows -1 ; i++){

            String[] stockInfoData = stockList[i].split("\\|");

            JSONObject jsonStockInfo = new JSONObject();
            for(j= 1; j< columns; j++){
                jsonStockInfo.put(stockHeader[j],stockInfoData[j]);
            }

            jsonStockList.put(jsonStockInfo);
        }

        return jsonStockList;
    }

    @Override
    public JSONArray getStockList(){

        Ftp ftp = new Ftp();
        String stockListData = ftp.readRemoteFile(ftpServer, directory, fileName);
        JSONArray jsonStockList = null ;

        if(stockListData.length() > 1){
            jsonStockList =  convertData(stockListData);
        }

        return jsonStockList;

    }
}
