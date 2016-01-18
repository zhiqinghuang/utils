package com.netmap.loan;

import java.math.BigDecimal;

public class PrincipalEquals {

	public static void main(String[] args) {
		BigDecimal invest = new BigDecimal("960000"); // 本金
		BigDecimal yearRate = new BigDecimal("0.0565"); // 年利率
		BigDecimal year = new BigDecimal("30");// 期限
		BigDecimal monthRate = yearRate.divide(new BigDecimal("12"), 8, BigDecimal.ROUND_HALF_UP);
		BigDecimal month = year.multiply(new BigDecimal("12"));
		BigDecimal discount = new BigDecimal("0.95");
		System.out.println(month.intValue());
		int liMonth = month.intValue();
		for (int i = 1; i <= liMonth; i++) {
			BigDecimal principal = invest.divide(month, 2, BigDecimal.ROUND_HALF_UP);
			BigDecimal monthRemain = new BigDecimal(i - 1);
			BigDecimal principalRemain = invest.subtract(principal.multiply(monthRemain));
			if (i == liMonth) {
				principal = principalRemain;
			}
			BigDecimal interestMonth = principalRemain.multiply(yearRate).multiply(discount).divide(new BigDecimal("12"), 2, BigDecimal.ROUND_HALF_UP);
			if (i%12 == 8) {
				BigDecimal interestMonth1 = principalRemain.multiply(yearRate).multiply(discount).multiply(new BigDecimal("11")).divide(new BigDecimal("30"), 8, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal("12"), 2, BigDecimal.ROUND_HALF_UP);
				yearRate = new BigDecimal("0.049");
				BigDecimal interestMonth2 = principalRemain.multiply(yearRate).multiply(discount).multiply(new BigDecimal("19")).divide(new BigDecimal("30"), 8, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal("12"), 2, BigDecimal.ROUND_HALF_UP);
				interestMonth = interestMonth1.add(interestMonth2);
			}
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
}
