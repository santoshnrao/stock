package com.test.extract;

import com.extract.Extractor;
import com.test.TestConstants;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Santosh on 6/29/15.
 */
public class ExtractorTest {

    private Extractor extractor = new Extractor();

    @Before
    public void setup(){

    }

    @Test
    public void extractMonthly(){

        Calendar date = new GregorianCalendar();
        date.add(Calendar.MONTH , -2);
        boolean status =  extractor.updateToCurrentDate(TestConstants._TEST_STOCK_ID,date);
        Assert.assertEquals(status,true);

    }

}
