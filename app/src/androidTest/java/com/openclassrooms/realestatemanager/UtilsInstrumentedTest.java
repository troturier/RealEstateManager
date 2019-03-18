package com.openclassrooms.realestatemanager;

import android.content.Context;
import android.net.wifi.WifiManager;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.runner.AndroidJUnit4;

import static androidx.test.InstrumentationRegistry.getTargetContext;
import static com.openclassrooms.realestatemanager.utils.Utils.isInternetAvailable;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class UtilsInstrumentedTest {

    @Test
    public void testNoConnectivity(){
        setInternet(false);
        assertFalse(isInternetAvailable());
    }

    @Test
    public void testConnectivity(){
        setInternet(true);
        assertTrue(isInternetAvailable());
    }

    private void setInternet(boolean enabled) {
        WifiManager wifiManager = (WifiManager) getTargetContext().getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(enabled);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @After
    public void teardown()  {
        setInternet(true);
    }
}
