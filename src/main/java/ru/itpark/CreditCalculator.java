package ru.itpark;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CreditCalculator {
    private int monthInYear = 12;
    private BigDecimal one = new BigDecimal(1);

    public long finalAmount(int creditAmount, int termPlacementInMonths, double percent) {
        BigDecimal amount = new BigDecimal(creditAmount);
        BigDecimal interestRatePerMonth = new BigDecimal(percent / monthInYear / 100).setScale(5,RoundingMode.HALF_UP);
        BigDecimal onePlusInterest = one.add(interestRatePerMonth).setScale(5,RoundingMode.HALF_UP);
        BigDecimal power = onePlusInterest.pow(termPlacementInMonths).setScale(5,RoundingMode.HALF_UP);
        BigDecimal multiple = power.multiply(interestRatePerMonth).setScale(5,RoundingMode.HALF_UP);
        BigDecimal oneMinus = power.add(one.negate()).setScale(5,RoundingMode.HALF_UP);
        BigDecimal annuityRatio = multiple.divide(oneMinus, 5, RoundingMode.HALF_UP);
        BigDecimal monthlyPayment = annuityRatio.multiply(amount).setScale(0,RoundingMode.HALF_UP);

        return (long) (monthlyPayment.intValue() * termPlacementInMonths);
    }

    public long monthlyPayment (int creditAmount, int termPlacementInMonths, double percent){
        BigDecimal amount = new BigDecimal(creditAmount);
        BigDecimal interestRatePerMonth = new BigDecimal(percent / monthInYear / 100);
        BigDecimal onePlusInterest = one.add(interestRatePerMonth);
        BigDecimal power = onePlusInterest.pow(termPlacementInMonths);
        BigDecimal multiple = power.multiply(interestRatePerMonth);
        BigDecimal oneMinus = power.add(one.negate());
        BigDecimal annuityRatio = multiple.divide(oneMinus, 6, RoundingMode.HALF_UP);
        BigDecimal monthlyPayment = annuityRatio.multiply(amount).setScale(0,RoundingMode.HALF_UP);

        return monthlyPayment.intValue();
    }

    public long overpayment(int creditAmount, int termPlacementInMonths, double percent){
        BigDecimal amount = new BigDecimal(creditAmount);
        BigDecimal interestRatePerMonth = new BigDecimal(percent / monthInYear / 100).setScale(5,RoundingMode.HALF_UP);
        BigDecimal onePlusInterest = one.add(interestRatePerMonth).setScale(5,RoundingMode.HALF_UP);
        BigDecimal power = onePlusInterest.pow(termPlacementInMonths).setScale(5,RoundingMode.HALF_UP);
        BigDecimal multiple = power.multiply(interestRatePerMonth).setScale(5,RoundingMode.HALF_UP);
        BigDecimal oneMinus = power.add(one.negate()).setScale(5,RoundingMode.HALF_UP);
        BigDecimal annuityRatio = multiple.divide(oneMinus, 5, RoundingMode.HALF_UP);
        BigDecimal monthlyPayment = annuityRatio.multiply(amount).setScale(0,RoundingMode.HALF_UP);

        long finalAmount = (long) (monthlyPayment.intValue() * termPlacementInMonths);

        return finalAmount - creditAmount;
    }

    String calculateDate(int termPlacementInMonths, String dateBegin) throws ParseException {
        Calendar dateDepositEnd = GregorianCalendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        dateDepositEnd.setTime(sdf.parse(dateBegin));
        dateDepositEnd.add(Calendar.MONTH, termPlacementInMonths);
        return sdf.format(dateDepositEnd.getTime());
    }
}
