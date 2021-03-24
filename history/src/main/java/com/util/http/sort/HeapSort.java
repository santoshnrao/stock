package com.util.http.sort;

import com.util.http.convert.Convert;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Santosh on 7/14/15.
 * Heap Sort
 */
public class HeapSort {

    public void sort( JSONArray jsonArray){

        Convert convert = new Convert();

        List<HashMap<String,String>> listDocument = convert.convertToListOfHashMap(jsonArray);
        //Todo

    }
}
