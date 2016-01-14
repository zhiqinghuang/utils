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
}