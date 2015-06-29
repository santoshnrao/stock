package com.stock.provider;

import java.sql.Date;
import java.util.Calendar;

/**
 * Created by i029249 on 6/28/15.
 */
public interface IStock {

    public String getCurrentStockValue(String stockSymbol) ;

    public String getHistoricStock(String stockSymbol, Calendar startDate , Calendar endDate);

}
