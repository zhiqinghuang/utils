package com.netmap.loan;

import java.math.BigDecimal;
import java.util.List;

import org.joda.time.Days;
import org.joda.time.LocalDate;

public class PrincipalAndInterestEquals {

	public static void main(String[] args) {
		String lstrInvest = "810000";
		String strRate = "5.40";
		String strDuration = "18";
		String strDiscount = "0.95";
		String strFirstPaymentDate = "2015-10-18";
		String strLoanDate = "2015-09-01";
		caculate(lstrInvest, strRate, strDuration, strDiscount, strFirstPaymentDate, strLoanDate);
	}

	public static void caculate(String strInvest, String strRate, String strDuration, String strDiscount, String strFirstPaymentDate, String strLoanDate) {
		List<String[]> rateList = LoanUtils.buildRateList();
		BigDecimal invest = new BigDecimal(strInvest);
		BigDecimal yearRate = new BigDecimal(strRate).multiply(new BigDecimal("0.01"));
		BigDecimal year = new BigDecimal(strDuration);
		BigDecimal monthRate = yearRate.divide(new BigDecimal("12"), 12, BigDecimal.ROUND_HALF_UP);
		BigDecimal month = year.multiply(new BigDecimal("12"));
		BigDecimal discount = new BigDecimal(strDiscount);
		LocalDate localDate = LocalDate.parse(strFirstPaymentDate);
		int liMonthsToNextYear = 13 - localDate.getMonthOfYear() + 1;
		int liDaysFromFirstOfMonth = localDate.getDayOfMonth() - 1;
		int liDaysPaymentDayToEndOfMonth = 30 - liDaysFromFirstOfMonth;
		int liMonth = month.intValue();
		BigDecimal monthPayment = caculateMonthPayment(invest, monthRate, discount, liMonth);
		BigDecimal lastPrincipal = BigDecimal.ZERO;
		BigDecimal principalRemain = invest.subtract(lastPrincipal);
		BigDecimal monthRemain = month;
		boolean isRateChange = false;
		LocalDate localLoanDate = LocalDate.parse(strLoanDate);
		int liDaysOnPre = Days.daysBetween(localLoanDate, localDate).getDays();
		BigDecimal interestOnPre = invest.multiply(yearRate).multiply(discount).divide(new BigDecimal("12"), 2, BigDecimal.ROUND_HALF_UP);
		for (int i = 1; i <= liMonth; i++) {
			BigDecimal interestMonth = principalRemain.multiply(yearRate).multiply(discount).divide(new BigDecimal("12"), 2, BigDecimal.ROUND_HALF_UP);
			if (i%12 == liMonthsToNextYear + 1 && isRateChange == true) {
				monthPayment = caculateMonthPayment(principalRemain, monthRate, discount, monthRemain.intValue());
				isRateChange = false;
			}
			BigDecimal principal = monthPayment.subtract(interestMonth);
			if (i%12 == liMonthsToNextYear) {
				BigDecimal yearRateTemp = LoanUtils.fetchRate(rateList, i, strFirstPaymentDate);
				if(!yearRate.equals(yearRateTemp)){
					isRateChange = true;
					BigDecimal interestMonth1 = LoanUtils.caculateInterestSegmente(principalRemain, yearRate, discount, liDaysPaymentDayToEndOfMonth);
					yearRate = yearRateTemp;
					monthRate = yearRate.divide(new BigDecimal("12"), 12, BigDecimal.ROUND_HALF_UP);
					BigDecimal interestMonth2 = LoanUtils.caculateInterestSegmente(principalRemain, yearRate, discount, liDaysFromFirstOfMonth);
					interestMonth = interestMonth1.add(interestMonth2);
					monthPayment = principal.add(interestMonth);
				}
			}
			monthRemain = monthRemain.subtract(new BigDecimal("1"));
			if (i == liMonth) {
				principal = principalRemain;
			}
			principalRemain = principalRemain.subtract(principal);
			LoanUtils.printPaymentSchedule(i, principal, interestMonth, principalRemain, interestOnPre);
		}
	}

	public static BigDecimal caculateMonthPayment(BigDecimal principalRemain, BigDecimal monthRate, BigDecimal discount, int liMonth){
		BigDecimal monthRatePow = monthRate.multiply(discount).add(new BigDecimal("1")).pow(liMonth).setScale(12, BigDecimal.ROUND_HALF_UP);
		BigDecimal monthRatePowTemp = monthRatePow.subtract(new BigDecimal("1"));
		BigDecimal monthPayment = principalRemain.multiply(monthRate.multiply(discount)).multiply(monthRatePow).divide(monthRatePowTemp, 2, BigDecimal.ROUND_HALF_UP);
		return monthPayment;
	}
}