package com.stringregistry.persistence;

import java.util.List;

import com.stringregistry.model.UnicodeString;

public interface StringRegistryDAO {

	public List<UnicodeString> readStrings()  throws Exception;
	
	public void   persistStrings(List<UnicodeString> unicodeStrings) throws Exception;
}
