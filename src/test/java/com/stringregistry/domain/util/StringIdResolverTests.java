
package com.stringregistry.domain.util;


import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;

import com.stringregistry.app.Application;


import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)

@SpringBootTest(classes =Application.class)

public class StringIdResolverTests {

	@Autowired
	StringIdResolver stringIdResolver;


    @Test
    public void getStringId() throws Exception {
    	
    	String str = "The quick brown fox";
    	int expectedId = 3502;
    	
    	int id = stringIdResolver.getStringId(str);
    	assertTrue(id == expectedId);
    	
    }
}
