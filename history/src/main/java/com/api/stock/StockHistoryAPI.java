package com.api.stock;

import com.api.stock.internal.StockAPI;

import javax.ws.rs.PathParam;

@javax.ws.rs.Path("/history")
public class StockHistoryAPI {
    @javax.ws.rs.GET
    public String getStockStatus(){
        return "Call the API with a stock information";
    }
    @javax.ws.rs.GET
    @javax.ws.rs.Path("/{stockID}")
    public String getStockHistory(@PathParam("stockID") String stockID){
        StockAPI stockAPI = new StockAPI();
        return stockAPI.getStockHistory(stockID);
    }


}
