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

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.stringregistry.domain.StringHelper;
import com.stringregistry.model.UnicodeString;


@RestController
public class StringRegistryController {

	@Autowired
    private StringHelper stringHelper;
    
    @RequestMapping("/strings/{id}")
    public ResponseEntity<List<UnicodeString>> getRegisteredStrings(@PathVariable Integer id) throws Exception {
    	
    	List<UnicodeString> strings =  stringHelper.lookupStringsForId(id);
        return new ResponseEntity<List<UnicodeString>>(strings, HttpStatus.OK);
    }
    
    @RequestMapping(value="/strings", method=RequestMethod.POST)
   	public ResponseEntity<UnicodeString> saveString(@RequestBody UnicodeString string) throws Exception{
     		
    	UnicodeString processedString = stringHelper.processStringAndSave(string); 	
        return new ResponseEntity<UnicodeString>(processedString, HttpStatus.CREATED);
  
   	}
    /*
    @RequestMapping(value="/userprofilesany", method=RequestMethod.POST)
   	public ResponseEntity<List<UserProfile>> saveProfiles(@RequestParam(value="apiKey") String apiKey,
   			                                              @RequestBody JsonNode jsonNode) throws Exception{
     		
    	List<UserProfile> userProfiles = new ArrayList<UserProfile>();
    	ObjectMapper objectMapper = new ObjectMapper();
    	
    	if (jsonNode.isArray()){
    		JavaType listType =	objectMapper.getTypeFactory().constructCollectionType(List.class, UserProfile.class);
    		userProfiles = objectMapper.readValue(jsonNode.toString(),listType);
    	} else {
    		UserProfile userProfile = objectMapper.treeToValue(jsonNode,UserProfile.class);
    		userProfiles.add(userProfile);
    	}
		
    	userProfiles = userProfileDomainHelper.resolveAddressesAndSave(apiKey, userProfiles);    	
        return new ResponseEntity<List<UserProfile>>(userProfiles, HttpStatus.CREATED);
  
   	}
   	*/
}
