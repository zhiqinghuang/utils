package com.netmap.loan;

import java.math.BigDecimal;

import org.joda.time.LocalDate;

public class DailyAccruals {
	private BigDecimal principal = null;
	private BigDecimal interest = null;
	private BigDecimal rate = null;
	private LocalDate date = null;

	public BigDecimal getPrincipal() {
		return principal;
	}

	public void setPrincipal(BigDecimal principal) {
		this.principal = principal;
	}

	public BigDecimal getInterest() {
		return interest;
	}

	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public void caculateInterest() {
		BigDecimal bigTemp = principal.multiply(rate);
		bigTemp = bigTemp.multiply(new BigDecimal("0.01"));
		bigTemp = bigTemp.divide(new BigDecimal("12"), 8, BigDecimal.ROUND_HALF_UP);
		LocalDate localDate = date.dayOfMonth().withMaximumValue();
		String lstrDaysOfMonth = localDate.dayOfMonth().getAsString();
		bigTemp = bigTemp.divide(new BigDecimal(lstrDaysOfMonth), 8, BigDecimal.ROUND_HALF_UP);
		setInterest(bigTemp);
		System.out.println(date + "	" + bigTemp);
	}
}