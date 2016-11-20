package com.stringregistry.app;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stringregistry.domain.StringHelper;

@Component
public class ApplicationStartup {
	
	@Autowired
	StringHelper stringHelper;
	
	
	public void initialize() throws Exception{
		
		stringHelper.readFromStoreAndLoadIntoMemoryCache();
	}
}
