package com.netmap.loan.date.daycount.defaultimpl;

import java.util.List;

import org.joda.time.LocalDate;

import com.netmap.loan.LoanUtils;
import com.netmap.loan.date.daycount.DaycountCalculator;
import com.netmap.loan.date.daycount.DaycountCalculatorFactory;

public class DaycountCalculatorFactoryImpl extends DaycountCalculatorFactory {

	public DaycountCalculator getISDAActualActual() {
		return new ISDAActualActual();
	}

	public DaycountCalculator getISMAActualActual() {
		return new ISMAActualActual();
	}

	public DaycountCalculator getAFBActualActual() {
		return new AFBActualActual();
	}

	public DaycountCalculator getISDA30Actual() {
		return new ISDA30Actual();
	}

	public DaycountCalculator getISMA30Actual() {
		return new ISMA30Actual();
	}

	public DaycountCalculator getAFB30Actual() {
		return new AFB30Actual();
	}

	public DaycountCalculator getUS30360() {
		return new US30360();
	}

	public DaycountCalculator getEU30365() {
		return new EU30365();
	}

	public DaycountCalculator getEU30360() {
		return new EU30360();
	}

	public DaycountCalculator getIT30360() {
		return new IT30360();
	}

	public DaycountCalculator getActual360() {
		return new Actual360();
	}

	public DaycountCalculator getActual365Fixed() {
		return new Actual365Fixed();
	}

	public DaycountCalculator getActual366() {
		return new Actual366();
	}

	public DaycountCalculator getBond30360() {
		return new Bond30360();
	}

	public DaycountCalculator getEuroBond30360() {
		return new EuroBond30360();
	}

	public DaycountCalculator getISDA30360() {
		return new ISDA30360();
	}

	public String[] getAvailableDaycountCalculators() {
		return new String[] { "ISDAActualActual", "ISMAActualActual", "AFBActualActual", "ISDA30Actual", "ISMA30Actual", "AFB30Actual", "US30360", "EU30365", "EU30360", "IT30360", "Actual360", "Actual365Fixed", "Actual366", "Bond30360", "EuroBond30360", "ISDA30360" };
	}

/*	public static void main(String[] args) {
		testStartDay();

	}
*/
	public static void testStartDay() {
		try {
			String[] strArray = { "Bond30360", "EuroBond30360", "ISDA30360", "1", "US30360", "EU30360" };

			String[] strArrayStartDate = { "2012-01-01" };
			String[] strArrayEndDate = { "2012-01-15" };
			for (int j = 0; j < strArrayStartDate.length; j++) {
				LocalDate startDate = new LocalDate(strArrayStartDate[j]);
				LocalDate endDate = new LocalDate(strArrayEndDate[j]);
				System.out.print(strArrayStartDate[j]);
				System.out.print("	");
				System.out.print(strArrayEndDate[j]);
				for (int i = 0; i < strArray.length; i++) {
					DaycountCalculator daycountCalculator = DaycountCalculatorFactory.newInstance().getDaycountCalculator(strArray[i]);
					boolean isTermination = false;
					if (strArrayEndDate[j].equals("")) {
						isTermination = true;
					}
					int liDays = daycountCalculator.daysBetween(startDate, endDate, isTermination);
					System.out.print("	");
					System.out.print(liDays);
				}
				System.out.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void testBetweenDays() {
		try {
			String[] strArray = { "Bond30360", "EuroBond30360", "ISDA30360", "1" };

			String[] strArrayStartDate = { "2007-01-15", "2007-01-15", "2007-01-15", "2007-09-30", "2007-09-30", "2007-09-30", "2007-01-15", "2007-01-31", "2007-02-28", "2006-08-31", "2007-02-28", "2007-02-14", "2007-02-26", "2008-02-29", "2008-02-29", "2008-02-29", "2007-02-28", "2007-10-31", "2007-08-31", "2008-02-29", "2008-08-31", "2009-02-28" };
			String[] strArrayEndDate = { "2007-01-30", "2007-02-15", "2007-07-15", "2008-03-31", "2007-10-31", "2008-09-30", "2007-01-31", "2007-02-28", "2007-03-31", "2007-02-28", "2007-08-31", "2007-02-28", "2008-02-29", "2009-02-28", "2008-03-30", "2008-03-31", "2007-03-05", "2007-11-28", "2008-02-29", "2008-08-31", "2009-02-28", "2009-08-31" };
			for (int j = 0; j < strArrayStartDate.length; j++) {
				LocalDate startDate = new LocalDate(strArrayStartDate[j]);
				LocalDate endDate = new LocalDate(strArrayEndDate[j]);
				System.out.print(strArrayStartDate[j]);
				System.out.print("	");
				System.out.print(strArrayEndDate[j]);
				for (int i = 0; i < strArray.length; i++) {
					DaycountCalculator daycountCalculator = DaycountCalculatorFactory.newInstance().getDaycountCalculator(strArray[i]);
					boolean isTermination = false;
					if (strArrayEndDate[j].equals("2009-02-28")) {
						isTermination = true;
					}
					int liDays = daycountCalculator.daysBetween(startDate, endDate, isTermination);
					System.out.print("	");
					System.out.print(liDays);
				}
				System.out.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void testTestFactor() {
		try {
			int iPeriods = LoanUtils.getPeriods("H616");

			String[] strArray = { "Bond30360", "EuroBond30360", "ISDA30360" };
			LocalDate startDate = new LocalDate("2012-01-15");
			LocalDate endDate = new LocalDate("2012-01-30");
			LocalDate nationalStartDate = new LocalDate("2011-12-16");
			LocalDate nationalEndDate = new LocalDate("2012-06-16");
			for (int i = 0; i < strArray.length; i++) {
				System.err.println(strArray[i]);
				DaycountCalculator daycountCalculator = DaycountCalculatorFactory.newInstance().getDaycountCalculator(strArray[i]);
				List<String[]> list = daycountCalculator.getSegmentsInfo(startDate, endDate, iPeriods, nationalStartDate, nationalEndDate);
				int liSize = list.size();
				for (int j = 0; j < liSize; j++) {
					String[] factors = list.get(j);
					System.out.println(factors[0] + "/" + factors[1]);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}