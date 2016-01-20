package com.netmap.loan.date;

import org.joda.time.LocalDate;

public class Period {
	private LocalDate startDate = null;

	private LocalDate endDate = null;

	private LocalDate referenceStartDate = null;

	private LocalDate referenceEndDate = null;

	public Period() {
	}

	public Period(Period toCopy) {
		this.startDate = toCopy.startDate;
		this.endDate = toCopy.endDate;
		this.referenceStartDate = toCopy.referenceStartDate;
		this.referenceEndDate = toCopy.referenceEndDate;
	}

	public Period(LocalDate startDate, LocalDate endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Period(LocalDate startDate, LocalDate endDate, LocalDate referenceStartDate, LocalDate referenceEndDate) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.referenceStartDate = referenceStartDate;
		this.referenceEndDate = referenceEndDate;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate date) {
		this.startDate = date;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndCalendar(LocalDate date) {
		this.endDate = date;
	}

	public LocalDate getReferenceEndDate() {
		return referenceEndDate;
	}

	public void setReferenceEndDate(LocalDate referenceEndDate) {
		this.referenceEndDate = referenceEndDate;
	}

	public LocalDate getReferenceStartDate() {
		return referenceStartDate;
	}

	public void setReferenceStartDate(LocalDate referenceStartDate) {
		this.referenceStartDate = referenceStartDate;
	}

	public String toString() {
		if (referenceStartDate == null || referenceEndDate == null) {
			return startDate.toString() + " - " + endDate.toString();
		} else {
			return startDate.toString() + " - " + endDate.toString() + "[" + referenceStartDate.toString() + " - " + referenceEndDate.toString() + "]";
		}
	}
}