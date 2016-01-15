package com.netmap.loan;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

public class MonthlyAccruals {
	private BigDecimal principalPayment = null;
	private BigDecimal interest = new BigDecimal(0);

	private LocalDate fromDate = null;
	private LocalDate toDate = null;

	private List<DailyAccruals> listDailyAccruals = new ArrayList<DailyAccruals>();

	public BigDecimal getPrincipalPayment() {
		return principalPayment;
	}

	public void setPrincipalPayment(BigDecimal principalPayment) {
		this.principalPayment = principalPayment;
	}

	public BigDecimal getInterest() {
		return interest;
	}

	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}

	public List<DailyAccruals> getListDailyAccruals() {
		return listDailyAccruals;
	}

	public void setListDailyAccruals(List<DailyAccruals> listDailyAccruals) {
		this.listDailyAccruals = listDailyAccruals;
	}

	public void addDailyAccruals(DailyAccruals dailyAccruals) {
		listDailyAccruals.add(dailyAccruals);
		if (fromDate == null) {
			setFromDate(dailyAccruals.getDate());
		}
		setToDate(dailyAccruals.getDate());
		BigDecimal interestDaily = dailyAccruals.getInterest();
		interest = interest.add(interestDaily);
	}

	public LocalDate getToDate() {
		return toDate;
	}

	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

	public LocalDate getFromDate() {
		return fromDate;
	}

	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}
}