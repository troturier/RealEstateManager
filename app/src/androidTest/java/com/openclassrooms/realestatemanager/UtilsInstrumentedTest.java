package com.openclassrooms.realestatemanager;

import android.support.test.runner.AndroidJUnit4;

import com.linkedin.android.testbutler.TestButler;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.openclassrooms.realestatemanager.utils.Utils.isInternetAvailable;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class UtilsInstrumentedTest {

    @Test
    public void testNoConnectivity(){
        toggleConnectivity(false);
        assertFalse(isInternetAvailable());
    }

    @Test
    public void testConnectivity(){
        toggleConnectivity(true);
        assertTrue(isInternetAvailable());
    }

    private void toggleConnectivity(boolean enable) {
        TestButler.setGsmState(enable);
        TestButler.setWifiState(enable);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @After
    public void teardown() {
        toggleConnectivity(true);
    }
}
