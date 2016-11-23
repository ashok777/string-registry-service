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
	 * The map facilitates the lookup of indexes  associated with a given string id.
	 * The list, where all of the strings are maintained, facilitates the persistence into a 
	 * local data store, something that is done whenever a new string is added
	 */
	
	Map<Integer, List<Integer>> globalIndexMap = new HashMap<Integer, List<Integer>>();
	List<UnicodeString> globalStringsList = new ArrayList<UnicodeString>();
	
	public StringHelper() {
	}

	public List<UnicodeString> lookupStringsForId(Integer id) throws Exception{
		
		List<Integer> indexList  = globalIndexMap.get(id);
		
		if (indexList == null){
			indexList = new ArrayList<Integer>();
		} 
		List<UnicodeString> stringListRet = new ArrayList<UnicodeString>();
		
		for (Integer index: indexList) {
			UnicodeString str = globalStringsList.get(index);
			if (str != null) {
				stringListRet.add(str);
			}
		}
		return stringListRet;
	}
	public UnicodeString processStringAndSave(UnicodeString string) throws Exception{
		
		if ( (string == null ) || (string.getText() == null) || (string.getText().isEmpty())) {
			
			throw new IllegalArgumentException("Invalid text in payload");
		}
		
		int id = stringIdResolver.getStringId(string.getText());
		string.setId(id);
		
		if ( (id != 0) && checkForExistenceInCache(string)){
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
		
		List<Integer> indexList = new ArrayList<Integer>();
		Integer id = string.getId();
		
		if (id != null &&  id != 0) {
			
			globalStringsList.add(string);
			Integer indexInGlobalList = globalStringsList.size()-1;
			
			if (globalIndexMap.containsKey(id)){
				
				indexList = globalIndexMap.get(id);
				indexList.add(indexInGlobalList);
				
			} else {
				
				indexList.add(indexInGlobalList);
				globalIndexMap.put(id, indexList);
			}			
			
		}	
	}
	private boolean checkForExistenceInCache (UnicodeString str) {
		
		Integer id = str.getId();
		List<Integer> indexList  = globalIndexMap.get(id);
		
		if (indexList != null) {
	
			for (Integer index: indexList){
				
				if (globalStringsList.get(index) != null) {
					
					String strInGlobalList = globalStringsList.get(index).getText();
					if (str.getText().equals(strInGlobalList )){
						return true;
					}
				}
			}
		}	
		return false;
	}
}
