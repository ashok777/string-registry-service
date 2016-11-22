package com.stringregistry.domain.util;

public interface StringIdResolver {

	/* This method scans a string and generates an Id based on the Unicode points
	 * String id is a number calculated as follows:
	 
		* String id is a sum of each character's id
		* A character id is a sum of the current and previous character's Unicode code point
		* The character id of the first character in the string is the character's Unicode code point
		* If the current and previous characters are the same, the character id 
		  is the current character's Unicode code point
		* String id must be calculated without using a loop
		  Example:
			"abc" => 97 + (97 + 98) + (98 + 99) => 489
			"abbc" => 97 + (97 + 98) + (98) + (98 + 99)  =>587
	 */
	public int getStringId (String textString);
}
