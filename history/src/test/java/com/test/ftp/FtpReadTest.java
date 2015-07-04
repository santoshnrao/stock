package com.test.ftp;

import com.stock.integrate.StockListExtractor;
import com.ftp.Ftp;
import junit.framework.Assert;
import org.json.JSONArray;
import org.junit.Test;

/**
 * Created by Santosh on 7/1/15.
 */
public class FtpReadTest {

    @Test
    public void readFileFromFTP(){

        Ftp ftp = new Ftp();
        String server = "ftp.nasdaqtrader.com";
        String fileName ="nasdaqtraded.txt";
        String directory = "/SymbolDirectory";

        String stringFileContent = ftp.readRemoteFile( server, directory, fileName);
        Assert.assertNotNull(stringFileContent);
        System.out.println(stringFileContent);

    }

    @Test
    public void getStockListTest(){
        JSONArray jsonArray =  new StockListExtractor().getStockList();
        Assert.assertNotNull(jsonArray);
    }

}
