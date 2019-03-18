package com.openclassrooms.realestatemanager;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.InstrumentationRegistry;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4ClassRunner.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.openclassrooms.realestatemanager", appContext.getPackageName());
    }
}
