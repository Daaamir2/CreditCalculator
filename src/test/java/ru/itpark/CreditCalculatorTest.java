package ru.itpark;

import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class CreditCalculatorTest {

    @Test
    void finalAmount() {
        {
            CreditCalculator calculator = new CreditCalculator();
            long exception = calculator.finalAmount(100_000, 12, 10);

            assertEquals(105_480, exception);
        }

        {
            CreditCalculator calculator = new CreditCalculator();
            long exception = calculator.finalAmount(200_000, 15, 12.5);

            assertEquals(217_020, exception);
        }
    }

    @Test
    void monthlyPayment() {
        {
            CreditCalculator calculator = new CreditCalculator();
            long exception = calculator.monthlyPayment(100_000, 12, 10);

            assertEquals(8_792, exception);
        }

        {
            CreditCalculator calculator = new CreditCalculator();
            long exception = calculator.monthlyPayment(200_000, 15, 12.5);

            assertEquals(14_471, exception);
        }

        {
            CreditCalculator calculator = new CreditCalculator();
            long exception = calculator.monthlyPayment(363_636, 27, 7.36);

            assertEquals(14_655, exception);
        }
    }

    @Test
    void overpayment() {
        {
            CreditCalculator calculator = new CreditCalculator();
            long exception = calculator.overpayment(100_000, 12, 10);

            assertEquals(5480, exception);
        }
    }

    @Test
    void calculateDate() throws ParseException {
        {
            CreditCalculator calculator = new CreditCalculator();
            String exception = calculator.calculateDate(12, "21.10.2018");

            assertEquals("21.10.2019", exception);
        }

        {
            CreditCalculator calculator = new CreditCalculator();
            String exception = calculator.calculateDate(15, "20.09.2020");

            assertEquals("20.12.2021", exception);
        }
    }
}