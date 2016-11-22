package com.stringregistry.api;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RestController;

import com.stringregistry.domain.StringHelper;
import com.stringregistry.model.UnicodeString;


@RestController
public class StringRegistryController {

	@Autowired
    private StringHelper stringHelper;
    
    @RequestMapping("/strings/{id}")
    public ResponseEntity<List<UnicodeString>> getRegisteredStrings(@PathVariable Integer id) throws Exception {
    	
    	
    	/* Defer to the domain object to lookup the strings associated with the provided id */
    	List<UnicodeString> strings =  stringHelper.lookupStringsForId(id);
        return new ResponseEntity<List<UnicodeString>>(strings, HttpStatus.OK);
    }
    
    @RequestMapping(value="/strings", method=RequestMethod.POST)
   	public ResponseEntity<UnicodeString> saveString(@RequestBody UnicodeString string) throws Exception{
     		
    	UnicodeString processedString = stringHelper.processStringAndSave(string); 	
        return new ResponseEntity<UnicodeString>(processedString, HttpStatus.CREATED);
  
   	}
}
