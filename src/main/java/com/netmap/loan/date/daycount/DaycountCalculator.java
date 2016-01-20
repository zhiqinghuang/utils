package com.netmap.loan.date.daycount;

import java.util.List;

import org.joda.time.LocalDate;

public abstract class DaycountCalculator {

	public abstract int daysBetween(LocalDate fromDate, LocalDate toDate, boolean isTerminationDate) throws Exception;

	public int daysBetween(LocalDate fromDate, LocalDate toDate) throws Exception {
		return daysBetween(fromDate, toDate, false);
	}

	public abstract List<String[]> getSegmentsInfo(LocalDate startDate, LocalDate endDate, int piPeriods, LocalDate notionalStartDate, LocalDate notionalEndDate) throws Exception;
}