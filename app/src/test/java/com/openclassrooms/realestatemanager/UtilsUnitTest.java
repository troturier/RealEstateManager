package com.openclassrooms.realestatemanager;

import org.junit.Test;

import static com.openclassrooms.realestatemanager.Utils.*;
import static org.junit.Assert.*;

public class UtilsUnitTest {
    @Test
    public void dollarToEuro(){
       assertEquals(81, convertDollarToEuro(100));
    }

    @Test
    public void euroToDollar(){
        assertEquals(119, convertEuroToDollar(100));
    }
}
