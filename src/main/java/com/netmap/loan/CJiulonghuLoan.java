package com.netmap.loan;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

public class CJiulonghuLoan {

	public static void main(String[] args) {
		// 2015-10-24 4.9
		// 2015-08-26 5.15
		// 2015-06-28 5.40
		// 2015-05-11 5.65
		// 2015-03-01 5.90
		// 2014-11-22 6.15
		// 2012-07-06 6.55
		// Equal loan payments
		// Equal principal payments
		EqualPrincipalPayments();
	}

	public static void EqualPrincipalPayments() {
		BigDecimal principal = new BigDecimal("960000");
		BigDecimal principalRemain = new BigDecimal("960000");
		BigDecimal rate = new BigDecimal("5.65");
		BigDecimal discount = new BigDecimal("0.95");
		BigDecimal discountRate = rate.multiply(discount);
		BigDecimal month = new BigDecimal("360");
		BigDecimal monthPrincipal = principal.divide(month, 2, BigDecimal.ROUND_HALF_UP);
		System.out.println(monthPrincipal);
		LocalDate fromDate = new LocalDate(2015, 4, 20);
		LocalDate toDate = new LocalDate(2045, 4, 20);
		System.out.println(toDate);
		List<MonthlyAccruals> listMonthlyAccruals = new ArrayList<MonthlyAccruals>();
		MonthlyAccruals monthlyAccruals = null;
		while (fromDate.isBefore(toDate)) {
			if (fromDate.dayOfMonth().get() == 20) {
				if(monthlyAccruals != null){
					BigDecimal interestMonthly = monthlyAccruals.getInterest();
					BigDecimal principalMonthly = monthlyAccruals.getPrincipalPayment();
					System.out.println(principalMonthly + "	" + principalMonthly.add(interestMonthly));
				}
				monthlyAccruals = new MonthlyAccruals();
				listMonthlyAccruals.add(monthlyAccruals);
				principalRemain = principalRemain.subtract(monthPrincipal);
				if (listMonthlyAccruals.size() == 360) {
					monthlyAccruals.setPrincipalPayment(principalRemain);
				} else {
					monthlyAccruals.setPrincipalPayment(monthPrincipal);
				}
			}
			DailyAccruals dailyAccruals = new DailyAccruals();
			dailyAccruals.setDate(new LocalDate(fromDate));
			dailyAccruals.setRate(discountRate);
			dailyAccruals.setPrincipal(principalRemain);
			dailyAccruals.caculateInterest();
			monthlyAccruals.addDailyAccruals(dailyAccruals);
			fromDate = fromDate.plusDays(1);
		}
		printMontyly(listMonthlyAccruals);
	}

	public static void printMontyly(List<MonthlyAccruals> listMonthlyAccruals) {
		int liSize = listMonthlyAccruals.size();
		for (int i = 0; i < liSize; i++) {
			MonthlyAccruals monthlyAccruals = listMonthlyAccruals.get(i);
			BigDecimal interestMonthly = monthlyAccruals.getInterest();
			BigDecimal principalMonthly = monthlyAccruals.getPrincipalPayment();
			System.out.println(principalMonthly.add(interestMonthly));
		}
	}
}