package ru.itpark;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CreditCalculator {
    private BigDecimal monthInYear = new BigDecimal(12);
    private BigDecimal one = BigDecimal.ONE;
    private BigDecimal hundredPercent = new BigDecimal(100);

    public double monthlyPayment(double creditAmount, int termPlacementInMonths, double percentDouble) {
        BigDecimal amount = new BigDecimal(Double.toString(creditAmount)).setScale(2, RoundingMode.DOWN);
        BigDecimal percent = new BigDecimal(Double.toString(percentDouble)).setScale(2, RoundingMode.DOWN);
        BigDecimal percentInMonth = percent.divide(monthInYear, 5, RoundingMode.HALF_UP);
        BigDecimal interestRatePerMonth = percentInMonth.divide(hundredPercent, 8, RoundingMode.HALF_UP);
        BigDecimal onePlusInterest = one.add(interestRatePerMonth).setScale(8, RoundingMode.HALF_UP);
        BigDecimal power = onePlusInterest.pow(termPlacementInMonths).setScale(8, RoundingMode.HALF_UP);
        BigDecimal multiple = power.multiply(interestRatePerMonth).setScale(8, RoundingMode.HALF_UP);
        BigDecimal oneMinus = power.add(one.negate()).setScale(8, RoundingMode.HALF_UP);
        BigDecimal annuityRatio = multiple.divide(oneMinus, 8, RoundingMode.HALF_UP);
        BigDecimal monthlyPayment = annuityRatio.multiply(amount).setScale(2, RoundingMode.HALF_UP);

        return monthlyPayment.doubleValue();
    }

    public double finalAmount(double creditAmount, int termPlacementInMonths, double percentDouble) {
        BigDecimal amount = new BigDecimal(Double.toString(creditAmount)).setScale(2, RoundingMode.DOWN);
        BigDecimal period = new BigDecimal(termPlacementInMonths);
        BigDecimal monthlyPayment = new BigDecimal(monthlyPayment(creditAmount, termPlacementInMonths, percentDouble)).setScale(2, RoundingMode.HALF_UP);
        BigDecimal percent = new BigDecimal(Double.toString(percentDouble)).setScale(2, RoundingMode.DOWN);
        BigDecimal percentInMonth = percent.divide(monthInYear, 8, RoundingMode.HALF_UP);
        BigDecimal interestRatePerMonth = percentInMonth.divide(hundredPercent, 8, RoundingMode.HALF_UP);

        for (int i = 0; i < termPlacementInMonths; i++) {
            BigDecimal payPercentInMonth = interestRatePerMonth.multiply(amount).setScale(8, RoundingMode.HALF_UP);
            BigDecimal payInMonthWithPercent = monthlyPayment.add(payPercentInMonth.negate()).setScale(8, RoundingMode.HALF_UP);
            amount = amount.add(payInMonthWithPercent.negate()).setScale(2, RoundingMode.HALF_UP);
        }

        BigDecimal finalAmount = monthlyPayment.multiply(period).add(amount).setScale(2, RoundingMode.HALF_UP);

        return finalAmount.doubleValue();
    }

    public double overpayment(double creditAmount, int termPlacementInMonths, double percentDouble) {
        BigDecimal amount = new BigDecimal(Double.toString(creditAmount)).setScale(2, RoundingMode.DOWN);
        BigDecimal finalSum = new BigDecimal(finalAmount(creditAmount, termPlacementInMonths, percentDouble));
        BigDecimal overpayment = finalSum.add(amount.negate()).setScale(2, RoundingMode.HALF_UP);

        return overpayment.doubleValue();
    }

    String calculateDate(int termPlacementInMonths, String dateBegin) throws ParseException {
        Calendar dateDepositEnd = GregorianCalendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        dateDepositEnd.setTime(sdf.parse(dateBegin));
        dateDepositEnd.add(Calendar.MONTH, termPlacementInMonths);
        return sdf.format(dateDepositEnd.getTime());
    }
}
