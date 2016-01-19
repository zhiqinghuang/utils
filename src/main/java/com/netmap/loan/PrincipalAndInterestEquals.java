package com.netmap.loan;

import java.math.BigDecimal;

public class PrincipalAndInterestEquals {

	public static void main(String[] args) {
		caculate();
	}

	public static void caculate() {
		BigDecimal invest = new BigDecimal("960000");
		BigDecimal yearRate = new BigDecimal("0.0565");
		BigDecimal year = new BigDecimal("30");
		BigDecimal monthRate = yearRate.divide(new BigDecimal("12"), 12, BigDecimal.ROUND_HALF_UP);
		BigDecimal month = year.multiply(new BigDecimal("12"));
		BigDecimal discount = new BigDecimal("0.95");
		System.out.println(month.intValue());
		int liMonth = month.intValue();
		BigDecimal monthPayment = caculateMonthPayment(invest, monthRate, discount, liMonth);
		System.out.println(monthPayment);
		BigDecimal lastPrincipal = BigDecimal.ZERO;
		BigDecimal principalRemain = invest.subtract(lastPrincipal);
		BigDecimal monthRemain = month;
		for (int i = 1; i <= liMonth; i++) {
			BigDecimal interestMonth = principalRemain.multiply(yearRate).multiply(discount).divide(new BigDecimal("12"), 2, BigDecimal.ROUND_HALF_UP);
			if (i%12 == 8) {
				BigDecimal interestMonth1 = caculateSegmenteInterest(principalRemain, yearRate, discount, 11);
				yearRate = new BigDecimal("0.049");
				monthRate = yearRate.divide(new BigDecimal("12"), 12, BigDecimal.ROUND_HALF_UP);
				BigDecimal interestMonth2 = caculateSegmenteInterest(principalRemain, yearRate, discount, 19);
				interestMonth = interestMonth1.add(interestMonth2);
			}
			if (i%12 == 9) {
				monthPayment = caculateMonthPayment(invest, monthRate, discount, monthRemain.intValue());
			}
			BigDecimal principal = monthPayment.subtract(interestMonth);
			monthRemain = monthRemain.subtract(new BigDecimal("1"));
			if (i == liMonth) {
				principal = principalRemain;
			}
			principalRemain = principalRemain.subtract(principal);
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

	public static BigDecimal caculateSegmenteInterest(BigDecimal principalRemain, BigDecimal yearRate, BigDecimal discount, int segmentDays){
		BigDecimal interestMonth1 = principalRemain.multiply(yearRate).multiply(discount).multiply(new BigDecimal(segmentDays)).divide(new BigDecimal("30"), 8, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal("12"), 2, BigDecimal.ROUND_HALF_UP);
		return interestMonth1;
	}

	public static BigDecimal caculateMonthPayment(BigDecimal principalRemain, BigDecimal monthRate, BigDecimal discount, int liMonth){
		BigDecimal monthRatePow = monthRate.multiply(discount).add(new BigDecimal("1")).pow(liMonth).setScale(12, BigDecimal.ROUND_HALF_UP);
		BigDecimal monthRatePowTemp = monthRatePow.subtract(new BigDecimal("1"));
		BigDecimal monthPayment = principalRemain.multiply(monthRate.multiply(discount)).multiply(monthRatePow).divide(monthRatePowTemp, 2, BigDecimal.ROUND_HALF_UP);
		return monthPayment;
	}
}