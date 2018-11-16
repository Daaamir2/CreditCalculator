package ru.itpark;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class CreditCalculatorTest {

    @Test
    void monthlyPayment() {
        {
            CreditCalculator calculator = new CreditCalculator();
            double exception = calculator.monthlyPayment(100_000, 12, 10);

            assertEquals(8_791.59, exception);
        }

        {
            CreditCalculator calculator = new CreditCalculator();
            double exception = calculator.monthlyPayment(200_000, 15, 12.5);

            assertEquals(14_471.30, exception);
        }
    }

    @Test
    void finalAmount() {
        {
            CreditCalculator calculator = new CreditCalculator();
            double exception = calculator.finalAmount(100_000, 12, 10);

            assertEquals(105_499.05, exception);
        }

        {
            CreditCalculator calculator = new CreditCalculator();
            double exception = calculator.finalAmount(200_000, 15, 12.5);

            assertEquals(217_069.50, exception);
        }
    }

    @Test
    void overpayment() {
        {
            CreditCalculator calculator = new CreditCalculator();
            double exception = calculator.overpayment(100_000, 12, 10);

            assertEquals(5_499.05, exception);
        }

        {
            CreditCalculator calculator = new CreditCalculator();
            double exception = calculator.overpayment(200_000, 15, 12.5);

            assertEquals(17_069.50, exception);
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