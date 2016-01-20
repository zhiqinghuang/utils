package com.netmap.loan;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

public class LoanUtils {
	public static BigDecimal caculateInterestSegmente(BigDecimal principalRemain, BigDecimal yearRate, BigDecimal discount, int segmentDays) {
		BigDecimal interestSegmentMonth = principalRemain.multiply(yearRate).multiply(discount).multiply(new BigDecimal(segmentDays)).divide(new BigDecimal("30"), 8, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal("12"), 2, BigDecimal.ROUND_HALF_UP);
		return interestSegmentMonth;
	}

	public static List<String[]> buildRateList() {
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

	public static void addRateToRateList(List<String[]> rateList, String strDate, String strRate) {
		String[] strArrayRate = buildRateArray(strDate, strRate);
		rateList.add(strArrayRate);
	}

	public static String[] buildRateArray(String strDate, String strRate) {
		String[] strArrayRate = new String[2];
		strArrayRate[0] = strDate;
		strArrayRate[1] = strRate;
		return strArrayRate;
	}

	public static BigDecimal fetchRate(List<String[]> rateList, int indexMonth, String strFirstPaymentDate) {
		LocalDate localDate = LocalDate.parse(strFirstPaymentDate);
		localDate = localDate.plusMonths(indexMonth);
		localDate = new LocalDate(localDate.getYear(), 1, 1);
		BigDecimal rate = BigDecimal.ZERO;
		int liRateListSize = rateList.size();
		for (int i = 0; i < liRateListSize; i++) {
			String[] strArrayRate = rateList.get(i);
			LocalDate localDateRateOn = LocalDate.parse(strArrayRate[0]);
			if (localDateRateOn.isBefore(localDate)) {
				rate = new BigDecimal(strArrayRate[1]).multiply(new BigDecimal("0.01"));
				break;
			}
		}
		return rate;
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