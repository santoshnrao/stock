package com.stock.integrate;

import com.stock.provider.IStock;
import com.stock.provider.yahoo.YahooQL;
import com.transform.StockToDB;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;

/**
 * Created by i029249 on 6/29/15.
 */
public class StockExtractor {

    private IStock stockProvider = new YahooQL();
    private StockToDB stockDataTransformer = new StockToDB();

    public JSONArray getHistoricStockData(String stockID, Calendar startDate, Calendar endDate){

        String stockHistory = stockProvider.getHistoricStock(stockID,startDate,endDate);

        return stockDataTransformer.convertExternalFormatToInternal(stockID,stockHistory);


    }
}
