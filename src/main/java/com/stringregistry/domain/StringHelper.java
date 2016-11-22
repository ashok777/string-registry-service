package com.stringregistry.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stringregistry.domain.util.StringIdResolver;
import com.stringregistry.model.UnicodeString;
import com.stringregistry.persistence.StringRegistryDAO;

import javax.persistence.EntityExistsException;

@Component
public class StringHelper {

	
	@Autowired
	StringRegistryDAO  stringRegistryDAO;
	
	@Autowired
	StringIdResolver stringIdResolver;
	
	/* These 2 collection instances act as a memory cache. 
	 * The map facilitates the lookup of strings associated with a given id.
	 * The list, where all of the strings are maintained, facilitates the persistence into a 
	 * local data store, something that is done whenever a new string is added
	 */
	
	Map<Integer, List<UnicodeString>> stringsMap = new HashMap<Integer, List<UnicodeString>>();
	List<UnicodeString> globalStringsList = new ArrayList<UnicodeString>();
	
	public StringHelper() {
	}

	public List<UnicodeString> lookupStringsForId(Integer id) throws Exception{
		
		List<UnicodeString> stringsList  = stringsMap.get(id);
		
		if (stringsList == null){
			stringsList = new ArrayList<UnicodeString>();
		} 
		return stringsList;
	}
	public UnicodeString processStringAndSave(UnicodeString string) throws Exception{
		
		int id = stringIdResolver.getStringId(string.getText());
		string.setId(id);
		
		if (stringsMap.containsKey(id)){
			throw new EntityExistsException(" String " + "'" + string.getText() + "'" + " already exists in registry");
		}
		addToMemoryCache(string);
		
		stringRegistryDAO.persistStrings(globalStringsList);
		return string;
	}
	/* This function is invoked once, and only once during the life time of a server, and that is at startup*/	
	public void readFromStoreAndLoadIntoMemoryCache() throws Exception {
		
		List<UnicodeString> stringData = stringRegistryDAO.readStrings();
		addToMemoryCache(stringData);
	}
	public void addToMemoryCache (List<UnicodeString> stringsList){
		
		for (UnicodeString string: stringsList){
			addToMemoryCache(string);
		}
	}
	public void addToMemoryCache (UnicodeString string){
		
		List<UnicodeString> stringsList = new ArrayList<UnicodeString>();
		Integer id = string.getId();
		
		if (id != null &&  id != 0) {
			
			if (stringsMap.containsKey(id)){
				
				stringsList = stringsMap.get(id);
				stringsList.add(string);
				
			} else {
				
				stringsList.add(string);
				stringsMap.put(id, stringsList);
			}			
			globalStringsList.add(string);
		}	
	}
}
