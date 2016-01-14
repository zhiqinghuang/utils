package com.netmap.loan;

import java.text.DecimalFormat;

public class Jshk {

	public static void main(String[] args) {

		DecimalFormat df = new DecimalFormat("#.00");

		int totalmomey = 960000; // 贷款总金额
		double tax = 5.65 * 0.01; // 当前利率
		double percent = 0.95; // 70% 当前为7折优惠利率
		int years = 30; // 贷款年数
		int passedMonth = 4; // 已经还款月数
		int currentYear = 2015; // 当前年份
		double oneMonthMoney = (double) totalmomey / (years * 12);

		for (int i = 0; i < years - 1; i++) {
			System.out.println("/n-------------------" + currentYear + "年还款计划-------------------");
			for (int j = 0; j < 12; j++) {
				double taxMoney = (totalmomey - oneMonthMoney * passedMonth) * (tax / 12) * percent;
				double money = oneMonthMoney + taxMoney;

				passedMonth++;
				String currentMonth = "" + (j + 1);
				if (j < 9) {
					currentMonth = "0" + currentMonth;
				}
				System.out.println("" + currentYear + "年第" + currentMonth + "月应还款金额 : " + df.format(oneMonthMoney) + " + " + df.format(taxMoney) + " = " + df.format(money));
			}
			currentYear++;
		}
	}
}
