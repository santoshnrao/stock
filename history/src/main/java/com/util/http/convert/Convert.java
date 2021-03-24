package com.util.http.convert;

import com.util.http.sort.HeapSort;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Santosh on 7/14/15.
 * Convert json Array to ArrayList
 */
public class Convert {

    /**
     * convert json array to document array
     * @param jsonArray
     * @return
     */
    public List<HashMap<String,String>> convertToListOfHashMap(JSONArray jsonArray){

        List<HashMap<String,String>> listDocument = new ArrayList<>();

        for(int i = 0 ;i <jsonArray.length();i++){

            listDocument.add(convertToHeap(jsonArray.getJSONObject(i)));

        }

        return  listDocument;

    }

    /**
     * convert json to document
     * @param jsonObject
     * @return document
     */
    public HashMap<String,String> convertToHeap(JSONObject jsonObject){

        HashMap<String,String> document = new HashMap<>();
        Iterator<String> jsonKeys = jsonObject.keys();
        while(jsonKeys.hasNext()){
            HashMap<String,String> value = new HashMap<>();
            String key = jsonKeys.next();
            value.put(key, jsonObject.get(key).toString());
        }

        return document;

    }
}
