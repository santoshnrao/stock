package com.transform;


import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by i029249 on 6/29/15.
 */
public class StockToDB {

    public JSONArray convertExternalFormatToInternal(String stockID, String jsonStockDate){

        JSONObject externalFormat = new JSONObject(jsonStockDate);
        JSONArray internalFormat = null;

        try {
            JSONObject jsonQueryResult = externalFormat.getJSONObject("query").getJSONObject("results");
            internalFormat = jsonQueryResult.getJSONArray("quote");

        }catch (Exception e){
            //e.printStackTrace();
        }


        //jsonQuery

        return  internalFormat;

    }

}
