package com.netmap.loan;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

public class PrincipalEquals {

	public static void main(String[] args) {
		String lstrInvest = "960000";
		String strRate = "5.65";
		String strDuration = "30";
		String strDiscount = "0.95";
		String strFirstPaymentDate = "2015-05-20";
		caculate(lstrInvest, strRate, strDuration, strDiscount, strFirstPaymentDate);
	}

	public static void caculate(String strInvest, String strRate, String strDuration, String strDiscount, String strFirstPaymentDate) {
		List<String[]> rateList = buildRateList();
		LocalDate localDate = LocalDate.parse(strFirstPaymentDate);
		System.out.println(localDate);
		System.out.println(localDate.getMonthOfYear());
		BigDecimal invest = new BigDecimal(strInvest);
		BigDecimal yearRate = new BigDecimal(strRate).multiply(new BigDecimal("0.01"));
		BigDecimal years = new BigDecimal(strDuration);
		BigDecimal months = years.multiply(new BigDecimal("12"));
		BigDecimal discount = new BigDecimal(strDiscount);
		int liMonth = months.intValue();
		for (int i = 1; i <= liMonth; i++) {
			// BigDecimal principal = invest.divide(months.subtract(new
			// BigDecimal(i - 1)), 2, BigDecimal.ROUND_HALF_UP);
			BigDecimal principal = invest.divide(months, 2, BigDecimal.ROUND_HALF_UP);
			BigDecimal monthRemain = new BigDecimal(i - 1);
			BigDecimal principalRemain = invest.subtract(principal.multiply(monthRemain));
			if (i == liMonth) {
				principal = principalRemain;
			}
			BigDecimal interestMonth = principalRemain.multiply(yearRate).multiply(discount).divide(new BigDecimal("12"), 2, BigDecimal.ROUND_HALF_UP);
			if (i % 12 == 8) {
				BigDecimal interestMonth1 = LoanUtils.caculateInterestSegmente(principalRemain, yearRate, discount, 11);
				yearRate = fetchRate(rateList, i, strFirstPaymentDate);
				BigDecimal interestMonth2 = LoanUtils.caculateInterestSegmente(principalRemain, yearRate, discount, 19);
				interestMonth = interestMonth1.add(interestMonth2);
			}
			printPaymentSchedule(i, principal, interestMonth, principalRemain);
		}
	}

	public static BigDecimal fetchRate(List<String[]> rateList, int indexMonth, String strFirstPaymentDate){
		LocalDate localDate = LocalDate.parse(strFirstPaymentDate);
		localDate = localDate.plusMonths(indexMonth);
		localDate = new LocalDate(localDate.getYear(), 1, 1);
		System.out.println(localDate);
		BigDecimal rate = BigDecimal.ZERO;
		int liRateListSize = rateList.size();
		for(int i=0;i<liRateListSize;i++){
			String[] strArrayRate = rateList.get(i);
			LocalDate localDateRateOn = LocalDate.parse(strArrayRate[0]);
			System.out.println(localDateRateOn);
			if(localDateRateOn.isBefore(localDate)){
				rate = new BigDecimal(strArrayRate[1]).multiply(new BigDecimal("0.01"));;
				break;
			}
		}
		return rate;
	}

	public static List<String[]> buildRateList(){
		List<String[]> rateList = new ArrayList<String[]>();
		addRateToRateList(rateList, "2015-10-24", "4.9");
		addRateToRateList(rateList, "2015-08-26", "5.15");
		addRateToRateList(rateList, "2015-06-28", "5.40");
		addRateToRateList(rateList, "2015-05-11", "5.65");
		addRateToRateList(rateList, "2015-03-01", "5.9");
		addRateToRateList(rateList, "2014-11-22", "6.15");
		addRateToRateList(rateList, "2012-07-06", "6.55");
		return rateList;
	}

	public static void addRateToRateList(List<String[]> rateList, String strDate, String strRate){
		String[] strArrayRate = buildRateArray(strDate, strRate);
		rateList.add(strArrayRate);
	}

	public static String[] buildRateArray(String strDate, String strRate){
		String[] strArrayRate = new String[2];
		strArrayRate[0] = strDate;
		strArrayRate[1] = strRate;
		return strArrayRate;
	}

	public static void printPaymentSchedule(int i, BigDecimal principal, BigDecimal interestMonth, BigDecimal principalRemain) {
		System.out.print(i);
		System.out.print("	");
		System.out.print(principal);
		System.out.print("	");
		System.out.print(interestMonth);
		System.out.print("	");
		System.out.print(interestMonth.add(principal));
		System.out.print("	");
		System.out.println(principalRemain);
	}
}