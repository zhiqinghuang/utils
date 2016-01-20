package com.netmap.loan.date.daycount.defaultimpl;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import com.netmap.loan.date.Period;
import com.netmap.loan.date.daycount.DaycountCalculator;

public class ISDAActualActual extends DaycountCalculator {

	public List<Period> getPeriods(LocalDate startDate, LocalDate endDate) throws Exception {
		LocalDate hold = new LocalDate(startDate);
		LocalDate end = new LocalDate(endDate);

		List<Period> periods = new ArrayList<Period>();

		while (hold.getYear() < end.getYear()) {
			LocalDate holdEnd = new LocalDate(hold.getYear() + 1, 1, 1);

			periods.add(new Period(new LocalDate(hold), holdEnd));
			hold = new LocalDate(holdEnd);
		}
		periods.add(new Period(hold, end));
		return periods;
	}

	public int daysBetween(LocalDate fromDate, LocalDate toDate, boolean isTerminationDate) throws Exception {
		return Days.daysBetween(toDate, fromDate).getDays();
	}

	public List<String[]> getSegmentsInfo(LocalDate startDate, LocalDate endDate, int piPeriods, LocalDate notionalStartDate, LocalDate notionalEndDate) throws Exception {
		List<String[]> listFactors = new ArrayList<String[]>();
		List<Period> subPeriods = getPeriods(startDate, endDate);
		for (int i = 0; i < subPeriods.size(); i++) {
			Period period = (Period) subPeriods.get(i);
			String[] segment = new String[4];
			segment[0] = String.valueOf(daysBetween(period.getStartDate(), period.getEndDate(), false));
			segment[1] = String.valueOf(period.getStartDate().dayOfYear().withMaximumValue().getDayOfYear());
			segment[2] = period.getStartDate().toString();
			segment[3] = period.getEndDate().toString();
			listFactors.add(segment);
		}
		return listFactors;
	}
}