package com.stock.api;

@javax.ws.rs.Path("/history")
public class StockHistoryAPI {
    @javax.ws.rs.GET
    public String getStockStatus(){
        return "User Info";
    }


}
