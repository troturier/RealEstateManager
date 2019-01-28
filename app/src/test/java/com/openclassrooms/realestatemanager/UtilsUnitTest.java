package com.openclassrooms.realestatemanager;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static com.openclassrooms.realestatemanager.Utils.convertDollarToEuro;
import static com.openclassrooms.realestatemanager.Utils.convertEuroToDollar;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UtilsUnitTest {
    @Test
    public void dollarToEuro(){
       assertEquals(81, convertDollarToEuro(100));
    }

    @Test
    public void euroToDollar(){
        assertEquals(119, convertEuroToDollar(100));
    }

    @Test
    public void getTodayDate(){
        LocalDateTime ldt;
        DateTimeFormatter fomatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String value = Utils.getTodayDate();

        boolean isValid = false;

        try {
            ldt = LocalDateTime.parse(value, fomatter);
            String result = ldt.format(fomatter);
            isValid = result.equals(value);
        } catch (DateTimeParseException e) {
            try {
                LocalDate ld = LocalDate.parse(value, fomatter);
                String result = ld.format(fomatter);
                isValid = result.equals(value);
            } catch (DateTimeParseException exp) {
                try {
                    LocalTime lt = LocalTime.parse(value, fomatter);
                    String result = lt.format(fomatter);
                    isValid = result.equals(value);
                } catch (DateTimeParseException e2) {
                    e2.printStackTrace();
                }
            }
        }

        assertTrue(value + " is not a valid date or is not in dd/MM/yyyy format", isValid);
    }
}
