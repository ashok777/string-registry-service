
package com.stringregistry.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.stringregistry.app.Application;
import com.stringregistry.model.UnicodeString;
import com.stringregistry.persistence.StringRegistryDAO;
import static org.mockito.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)

@SpringBootTest(classes =Application.class)

public class StringHelperLookupTests {

	@Autowired
	StringHelper stringHelper;

	@MockBean
    private StringRegistryDAO  stringRegistryDAO;
	
	@Before
	public void setup() throws Exception {
		
	   	/* stub out the stringRegistryDAO persistence method*/
    	Mockito.doNothing().when(stringRegistryDAO).persistStrings(anyListOf(UnicodeString.class));

    	/* set up a UnicodeString*/
    	UnicodeString string = new UnicodeString("abc");				
			
    	stringHelper.processStringAndSave(string);
	}
    @Test
    public void lookupStringsForId() throws Exception {
       	
    	int id=489;
    	List<UnicodeString> list = stringHelper.lookupStringsForId(id);
    	
    	assertTrue(list.get(0).getId()== id);
    	
    }
  
}
