package com.stringregistry.app;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stringregistry.domain.StringHelper;

@Component
public class ApplicationStartup {
	
	@Autowired
	StringHelper stringHelper;
	
	
	public void initialize() throws Exception{
		/* We read in all the strings from the string registry persistence store and cache
		 * it in memory. This is done at server startup.
		 */
		stringHelper.readFromStoreAndLoadIntoMemoryCache();
	}
}
