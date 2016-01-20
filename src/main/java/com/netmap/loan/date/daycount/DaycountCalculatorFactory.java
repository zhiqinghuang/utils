package com.netmap.loan.date.daycount;

import com.netmap.loan.date.daycount.defaultimpl.DaycountCalculatorFactoryImpl;

public abstract class DaycountCalculatorFactory {

	public static String daycountCalculatorFactoryClassNameParameter = DaycountCalculatorFactory.class.getName();

	public static String defaultDaycountCalculatorFactoryClassName = DaycountCalculatorFactoryImpl.class.getName();

	public static DaycountCalculatorFactory newInstance() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		String daycountCalculatorFactoryClassName = System.getProperty(daycountCalculatorFactoryClassNameParameter);
		if (daycountCalculatorFactoryClassName == null) {
			daycountCalculatorFactoryClassName = defaultDaycountCalculatorFactoryClassName;
		}

		return newInstance(daycountCalculatorFactoryClassName);
	}

	public static DaycountCalculatorFactory newInstance(String daycountCalculatorFactoryClassName) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Class<?> daycountCalculatorFactoryClass = Class.forName(daycountCalculatorFactoryClassName);
		DaycountCalculatorFactory factory = (DaycountCalculatorFactory) daycountCalculatorFactoryClass.newInstance();
		return factory;
	}

	public abstract DaycountCalculator getISDAActualActual();

	public abstract DaycountCalculator getISMAActualActual();

	public abstract DaycountCalculator getAFBActualActual();

	public abstract DaycountCalculator getISDA30Actual();

	public abstract DaycountCalculator getISMA30Actual();

	public abstract DaycountCalculator getAFB30Actual();

	public abstract DaycountCalculator getUS30360();

	public abstract DaycountCalculator getEU30365();

	public abstract DaycountCalculator getEU30360();

	public abstract DaycountCalculator getIT30360();

	public abstract DaycountCalculator getActual360();

	public abstract DaycountCalculator getActual365Fixed();

	public abstract DaycountCalculator getActual366();

	public abstract DaycountCalculator getBond30360();

	public abstract DaycountCalculator getEuroBond30360();

	public abstract DaycountCalculator getISDA30360();

	public DaycountCalculator getDaycountCalculator(String name) throws Exception {
		if(name.equals("1") || name.equals("ISDAActualActual") || name.equals("D")) {
			return getISDAActualActual();
		} else if (name.equals("2") || name.equals("ISMAActualActual")) {
			return getISMAActualActual();
		} else if (name.equals("3") || name.equals("AFBActualActual")) {
			return getAFBActualActual();
		} else if (name.equals("4") || name.equals("ISDA30Actual")) {
			return getISDA30Actual();
		} else if (name.equals("5") || name.equals("ISMA30Actual")) {
			return getISMA30Actual();
		} else if (name.equals("6") || name.equals("AFB30Actual")) {
			return getAFB30Actual();
		} else if (name.equals("US30360")) {
			return getUS30360();
		} else if (name.equals("8") || name.equals("EU30365")) {
			return getEU30365();
		} else if (name.equals("7") || name.equals("EU30360")) {
			return getEU30360();
		} else if (name.equals("IT30360")) {
			return getIT30360();
		} else if (name.equals("A") || name.equals("Actual360")) {
			return getActual360();
		} else if (name.equals("9") || name.equals("Actual365Fixed")) {
			return getActual365Fixed();
		} else if (name.equals("Actual366")) {
			return getActual366();
		} else if (name.equals("Bond30360")) {
			return getBond30360();
		} else if (name.equals("EuroBond30360")) {
			return getEuroBond30360();
		} else if(name.equals("ISDA30360") || name.equals("M")) {
			return getISDA30360();
		} else {
			throw new Exception("Unknown day count calculator \"" + name + "\"");
		}
	}

	public abstract String[] getAvailableDaycountCalculators();
}