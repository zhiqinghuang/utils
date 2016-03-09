package com.netmap.zip4j;

public class CPasswordGenerater {

	static char[] charSource = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
	static int sLength = charSource.length;

	public static void main(String[] args) {
		long beginMillis = System.currentTimeMillis();
		System.out.println(beginMillis);
		int maxLength = 5;
		int counter = 0;
		StringBuilder buider = new StringBuilder();
		while (buider.toString().length() <= maxLength) {
			buider = new StringBuilder(maxLength * 2);
			int _counter = counter;
			while (_counter >= sLength) {
				buider.insert(0, charSource[_counter % sLength]);
				_counter = _counter / sLength;
				_counter--;
			}
			buider.insert(0, charSource[_counter]);
			counter++;
			System.out.println(buider.toString());
		}
		long endMillis = System.currentTimeMillis();
		System.out.println(endMillis);
		System.out.println(endMillis - beginMillis);
	}
}