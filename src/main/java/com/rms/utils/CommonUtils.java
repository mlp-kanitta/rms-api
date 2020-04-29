/**
 * 
 */
package com.rms.utils;

import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Kanitta Moonlapong
 *
 */
public class CommonUtils {

	public static String decodeBase64(String encodedText) {

		// Get decode text

		byte[] decodedBytes = Base64.getDecoder().decode(encodedText);
		String decodedText = new String(decodedBytes);

		return decodedText;

	}

	public static String encodeBase64(String text) {

		// Get decode text

		byte[] encodedBytes = Base64.getEncoder().encode(text.getBytes());
		String encodedText = new String(encodedBytes);

		return encodedText;

	}
	
	/*
	 * Check if input contains only alphanumeric
	 * 
	 * @param String text
	 * 
	 * @result False = only contains alphanumeric, else return true
	 */
	public static boolean isAlphaNumeric(String inputStr) {

		Pattern pattern = Pattern.compile(new String("^[a-zA-Z0-9]*$"));
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			return true;
		}
		return false;
	}

}
