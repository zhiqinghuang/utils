package com.netmap.loan;

import java.math.BigDecimal;
import java.util.List;

import org.joda.time.LocalDate;

public class PrincipalAndInterestEquals {

	public static void main(String[] args) {
		/*
		 * 吴春庚 13:09:51
			5月18余额790962.6
			吴春庚 13:10:06
			利率4.655
			吴春庚 13:10:20
			5月24日还400000
			吴春庚 13:10:56
			银行同时把400000的5.18-5.23的利息310.33收掉
			吴春庚 13:11:25
			就是说5月24总共收了400310.33
			吴春庚 13:16:47
			2742.27
			黄志清 13:16:48
			2742.27
			黄志清 13:20:38
			2676.31
			黄志清 13:21:14
			390962.6
		 */
		/*String lstrInvest = "390962.6";
		String strRate = "4.90";
		String strDuration = "18";
		String strDiscount = "0.95";
		String strFirstPaymentDate = "2016-06-18";
		String strLoanDate = "2016-05-18";
		caculate(lstrInvest, strRate, strDuration, strDiscount, strFirstPaymentDate, strLoanDate);*/
		String lstrInvest = "770000";
		String strRate = "4.90";
		String strDuration = "18";
		String strDiscount = "0.90";
		String strFirstPaymentDate = "2016-11-05";
		String strLoanDate = "2016-10-05";
		caculate(lstrInvest, strRate, strDuration, strDiscount, strFirstPaymentDate, strLoanDate);

	}

	public static void caculate(String strInvest, String strRate, String strDuration, String strDiscount, String strFirstPaymentDate, String strLoanDate) {
		List<String[]> rateList = LoanUtils.buildRateList();
		BigDecimal invest = new BigDecimal(strInvest);
		BigDecimal yearRate = new BigDecimal(strRate).multiply(new BigDecimal("0.01"));
		BigDecimal year = new BigDecimal(strDuration);
		BigDecimal monthRate = yearRate.divide(new BigDecimal("12"), 12, BigDecimal.ROUND_HALF_UP);
		//BigDecimal month = new BigDecimal("208");//year.multiply(new BigDecimal("12"));
		BigDecimal month = new BigDecimal("211");//year.multiply(new BigDecimal("12"));
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
		int liDaysOnPre = LoanUtils.getDaysOnPre(localLoanDate, localDate, "ISDA30360")%30;
		BigDecimal interestOnPre = invest.multiply(yearRate).multiply(discount).multiply(new BigDecimal(liDaysOnPre)).divide(new BigDecimal(12 * 30), 2, BigDecimal.ROUND_HALF_UP);
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