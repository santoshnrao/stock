package com.stock.provider.yahoo;

/**
 * Created by Santosh on 6/28/15.
 */
import com.stock.provider.IStock;
import com.util.http.HttpRequestWrapper;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
/*
 * https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.historicaldata%20where%20symbol%20=%20%22SAP%22%20%20and%20startDate%20=%20%22%202009-09-11%22%20and%20endDate%20=%20%22%202010-03-10%20%22&env=http://datatables.org/alltables.env&format=json
 * https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.historicaldata%20where%20symbol%20%3D%20%22YHOO%22%20and%20startDate%20%3D%20%222009-09-11%22%20and%20endDate%20%3D%20%222010-03-10%22&format=json&diagnostics=true&env=http%3A%2F%2Fdatatables.org%2Falltables.env&callback=
 */

public class YahooQL implements IStock{

    private SimpleDateFormat dateFormatter = new SimpleDateFormat("YYYY-MM-dd");


    private static final String _YQL_HOST = "query.yahooapis.com";
    private static final String _YQL_PATH = "/v1/public/yql";
    private static final String _YQL_SCHEMA = "https";
    private static final String _YQL_ENV_ALLTABLE = "env=http://datatables.org/alltables.env";
    private static final String _YQL_FORMAT = "format=json";

    private String getQuote(String QuoteSymbol) {
        String QueryString = "select * from yahoo.finance.quotes where symbol in (\""
                + QuoteSymbol + "\") ";
        String queryParam = "q=" + QueryString + "&" + _YQL_ENV_ALLTABLE + "&"
                + _YQL_FORMAT;

        URI uri = null;
        try {
            uri = new URI(_YQL_SCHEMA, null, _YQL_HOST, 443, _YQL_PATH,
                    queryParam, null);
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String responseString = new HttpRequestWrapper().HttpGet(uri.toString());
        return responseString;
    }

    private String getHistoricQuote(String sQuoteSymbol, String sStartDate,
                                   String sEndDate) {
        String QueryString = "select * from yahoo.finance.historicaldata where symbol = \""
                + sQuoteSymbol
                + "\"  and startDate = \""
                + sStartDate
                + "\" and endDate = \"" + sEndDate + "\" ";
        String queryParam = "q=" + QueryString + "&" + _YQL_ENV_ALLTABLE + "&"
                + _YQL_FORMAT;

        URI uri = null;
        try {
            uri = new URI(_YQL_SCHEMA, null, _YQL_HOST, 443, _YQL_PATH,
                    queryParam, null);
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//        System.out.println(uri.toASCIIString());
        String responseString = new HttpRequestWrapper().HttpGet(uri.toString());
        return responseString;

    }

    @Override
    public String getCurrentStockValue(String stockSymbol) {
        return  getQuote(stockSymbol);

    }

    @Override
    public String getHistoricStock(String stockSymbol, Calendar startDate, Calendar endDate) {

        String sStartDate = dateFormatter.format(startDate.getTime());
        String sEndDate = dateFormatter.format(endDate.getTime());

        return getHistoricQuote(stockSymbol,sStartDate,sEndDate);

    }
}

