
package com.stringregistry.domain;

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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)

@SpringBootTest(classes =Application.class)

public class StringHelperReadFromStoreTests {

	@Autowired
	StringHelper stringHelper;

	@MockBean
    private StringRegistryDAO  stringRegistryDAO;
	
    @Test
    public void readFromStoreAndLoadIntoMemoryCache() throws Exception {
    	
    	int id = 489;
    	UnicodeString string = new UnicodeString(id, "abc");
	
    	List<UnicodeString> list = new ArrayList<UnicodeString>();
    	list.add(string);
    	    	
    	Mockito.doReturn(list).when(stringRegistryDAO).readStrings();
   	    	
    	stringHelper.readFromStoreAndLoadIntoMemoryCache();
    	list = stringHelper.lookupStringsForId(id);
    	assertTrue(list.get(0).getId()== id);
    	
    }
}
