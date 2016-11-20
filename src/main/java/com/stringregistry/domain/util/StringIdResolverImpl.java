package com.stringregistry.domain.util;

import java.util.Arrays;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

import one.util.streamex.IntStreamEx;

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
