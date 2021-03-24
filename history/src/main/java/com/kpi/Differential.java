package com.kpi;

import com.db.mongo.ManageCollection;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Santosh on 7/13/15.
 * Find the differential value of a stock over time
 */
public class Differential {
    /**
     * return format
     * {
     *   "Symbol: "ABC" ,
     *   "DateKPI" : [ { "Date" : "2000-01-01" ,
     *       "ForwardDifferential : 1.2 ,
     *       "BackwardDifferential : 1.3,
     *
     *   },
     *   {
     *     "Date" : "2000-01-02" ,
     *       "ForwardDifferential : 1.2 ,
     *       "BackwardDifferential : 1.3,
     *
     *   }
     *   ]
     * }
     */

    /**
     * Provide only Sorted data based on Date Field
     * @param stockHistory
     * @return
     */
    public JSONObject extractDifferential(JSONArray stockHistory){

        JSONObject differential = new JSONObject();
        String stockSymbol = stockHistory.getJSONObject(0).getString((ManageCollection.COLUMN_STOCK_SYMBOL));

        differential.put(ManageCollection.COLUMN_STOCK_SYMBOL,stockSymbol);
        JSONArray kpiArray = new JSONArray();

        int length = stockHistory.length();

        for (int i = 1 ; i < length ; i++){

            JSONObject dayValue = stockHistory.getJSONObject(i);
            JSONObject previousValue = stockHistory.getJSONObject(i -1);

            String referenceDate = dayValue.getString(ManageCollection.COLUMN_DATE);

            JSONObject kpiValues = new JSONObject();

            kpiValues.put(ManageCollection.COLUMN_DATE , referenceDate);

            /********  Forward Differential  *********/

            double referenceClose = Double.parseDouble( dayValue.getString("Close") );
            double previousDayClose = Double.parseDouble( previousValue.getString("Close") );

            double closePreviousDifferntial = referenceClose - previousDayClose ;


        }

        return  differential;

    }

}
