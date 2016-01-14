package com.netmap.loan;

import java.math.BigDecimal;

import org.joda.time.LocalDate;

public class CJiulonghuLoan {

	public static void main(String[] args) {
		//2015-10-24 4.9
		//2015-08-26 5.15
		//2015-06-28 5.40
		//2015-05-11 5.65
		//2015-03-01 5.90
		//2014-11-22 6.15
		//2012-07-06 6.55
		//Equal loan payments
		//Equal principal payments
		EqualPrincipalPayments();
	}
	
	public static void EqualPrincipalPayments(){
		BigDecimal principal = new BigDecimal("960000");
		BigDecimal rate = new BigDecimal("5.65");
		BigDecimal discount = new BigDecimal("0.95");
		BigDecimal discountRate = rate.multiply(discount);
		BigDecimal month = new BigDecimal("360");
		BigDecimal monthPrincipal = principal.divide(month, 2, BigDecimal.ROUND_UP);
		System.out.println(monthPrincipal);
		//System.out.println(principal.multiply(rate));
		LocalDate fromDate = new LocalDate(2015,4,21);
		LocalDate toDate = new LocalDate(2045,4,20);//fromDate.plusMonths(360);
		System.out.println(toDate);
	}
}