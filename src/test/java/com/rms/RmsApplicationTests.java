package com.rms;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.rms.utils.CommonUtils;

@ExtendWith(SpringExtension.class)
class RmsApplicationTests {

	@Test
	/*
	 * Check if input contains only alphanumeric
	 */
	public void testContainsSpecialCharacter() {
		assertEquals(false, CommonUtils.isAlphaNumeric("TEXT INPUT"));
		assertEquals(false, CommonUtils.isAlphaNumeric("TEST1234$5"));
		assertEquals(true, CommonUtils.isAlphaNumeric("test1234"));
		assertEquals(true, CommonUtils.isAlphaNumeric("TEXT14520"));
	}

}
