
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
import static org.mockito.Mockito.when;

import javax.persistence.EntityExistsException;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)

@SpringBootTest(classes =Application.class)

public class StringHelperSaveTests {

	@Autowired
	StringHelper stringHelper;

	@MockBean
    private StringRegistryDAO  stringRegistryDAO;
	
	@Before
	public void setup() throws Exception {
		
	   	/* stub out the stringRegistryDAO persistence method*/
    	Mockito.doNothing().when(stringRegistryDAO).persistStrings(anyListOf(UnicodeString.class));

	}
    @Test
    public void processStringAndSave() throws Exception {
       	
    	//Mockito.doReturn(resolvedAddress).when(this.addressResolutionProvider).getResolvedAddress(anyString(),anyString());
    	//when(this.addressResolutionProvider.getResolvedAddress(anyString(),anyString())).thenReturn(resolvedAddress);
    		    	     	
    	/* set up a UnicodeString*/
    	UnicodeString string = new UnicodeString();
		string.setText("abc");
		
		int expectedId = 489;
		
		/* Call StringHelper to process string*/
		
    	UnicodeString registeredString  = stringHelper.processStringAndSave(string);
    	assertTrue(registeredString.getId() == expectedId);
    	
    }
    @Test(expected=EntityExistsException.class)
    public void processStringAndSaveDuplicate() throws Exception {
       	
    	//Mockito.doReturn(resolvedAddress).when(this.addressResolutionProvider).getResolvedAddress(anyString(),anyString());
    	//when(this.addressResolutionProvider.getResolvedAddress(anyString(),anyString())).thenReturn(resolvedAddress);
    		    	     	
    	/* set up a UnicodeString*/
    	UnicodeString string = new UnicodeString();
		string.setText("abc");
		
		/* Call StringHelper to process string; expected exception to be thrown*/
		
    	stringHelper.processStringAndSave(string);
    }
}
