package com.stringregistry.domain.util;

import java.util.Arrays;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

import one.util.streamex.IntStreamEx;

/* This is one form of implementation of the string id generator. 
 *  It utilizes Java 8 streams instead of traditional loop over an array of Characters.
 *  
 *  The basic steps are :
 *  1. Set up an [int] array of code points with elements corresponding to characters in the text string
 *  2. Concatenate a 0-valued single element and the code point array fom the previous step
 *  3. Using the InstreamEx library generate a stream of the code point array; this library provides a 
 *     pairMap method which allows consecutive pairs of elements to be processed via the lambda expression.
 *     Within this mapping process we apply the business rule that determines the weighted or effective 
 *     code point value for an element 
 *  4. Finally we reduce the stream by invoking the sum function which calculates the total value   
 */
@Component
public class StringIdResolverImpl implements StringIdResolver {

	@Override
	public int getStringId(String textString) {
		
		int[] initialValue = {0};
		int[] codePoints = textString.chars().toArray();
		
		codePoints = IntStream.concat(Arrays.stream(initialValue), Arrays.stream(codePoints)).toArray();
				
		int [] weightedCodePoints  = IntStreamEx.of(codePoints).pairMap((a, b) ->  a == b ? a : a+b).toArray();		
		int totalCodePoints = Arrays.stream(weightedCodePoints).sum();
		
		return totalCodePoints;
	}
}
