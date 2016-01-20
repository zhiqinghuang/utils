package com.netmap.loan;

import java.math.BigDecimal;
import java.util.List;

import org.joda.time.LocalDate;

public class PrincipalEquals {

	public static void main(String[] args) {
		String lstrInvest = "960000";
		String strRate = "5.65";
		String strDuration = "30";
		String strDiscount = "0.95";
		String strFirstPaymentDate = "2015-06-20";
		caculate(lstrInvest, strRate, strDuration, strDiscount, strFirstPaymentDate);
	}

	public static void caculate(String strInvest, String strRate, String strDuration, String strDiscount, String strFirstPaymentDate) {
		List<String[]> rateList = LoanUtils.buildRateList();
		LocalDate localDate = LocalDate.parse(strFirstPaymentDate);
		int liMonthsToNextYear = 13 - localDate.getMonthOfYear() + 1;
		int liDaysFromFirstOfMonth = localDate.getDayOfMonth() - 1;
		int liDaysPaymentDayToEndOfMonth = 30 - liDaysFromFirstOfMonth;
		BigDecimal invest = new BigDecimal(strInvest);
		BigDecimal yearRate = new BigDecimal(strRate).multiply(new BigDecimal("0.01"));
		BigDecimal years = new BigDecimal(strDuration);
		BigDecimal months = years.multiply(new BigDecimal("12"));
		BigDecimal discount = new BigDecimal(strDiscount);
		int liMonth = months.intValue();
		for (int i = 1; i <= liMonth; i++) {
			BigDecimal principal = invest.divide(months, 2, BigDecimal.ROUND_HALF_UP);
			BigDecimal monthRemain = new BigDecimal(i - 1);
			BigDecimal principalRemain = invest.subtract(principal.multiply(monthRemain));
			if (i == liMonth) {
				principal = principalRemain;
			}
			BigDecimal interestMonth = principalRemain.multiply(yearRate).multiply(discount).divide(new BigDecimal("12"), 2, BigDecimal.ROUND_HALF_UP);
			if (i % 12 == liMonthsToNextYear) {
				BigDecimal yearRateTemp = LoanUtils.fetchRate(rateList, i, strFirstPaymentDate);
				if(!yearRate.equals(yearRateTemp)){
					BigDecimal interestMonth1 = LoanUtils.caculateInterestSegmente(principalRemain, yearRate, discount, liDaysPaymentDayToEndOfMonth);
					yearRate = yearRateTemp;
					BigDecimal interestMonth2 = LoanUtils.caculateInterestSegmente(principalRemain, yearRate, discount, liDaysFromFirstOfMonth);
					interestMonth = interestMonth1.add(interestMonth2);
				}
			}
			LoanUtils.printPaymentSchedule(i, principal, interestMonth, principalRemain);
		}
	}
}