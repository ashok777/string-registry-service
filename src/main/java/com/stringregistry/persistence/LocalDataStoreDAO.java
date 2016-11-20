package com.stringregistry.persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JavaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.stringregistry.model.UnicodeString;


@PropertySource("classpath:application.properties")
@Component
public class LocalDataStoreDAO implements StringRegistryDAO {
	
	@Value("${local.datastore.file.path}")
	private String localDataStoreFilePath;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	public List<UnicodeString> readStrings() throws Exception{
		
		List<UnicodeString> strings = new ArrayList<UnicodeString>();
		
		File dataFile = new File(localDataStoreFilePath);
		if (dataFile.exists()){

			JavaType listType =	objectMapper.getTypeFactory().constructCollectionType(List.class, UnicodeString.class);
			Reader reader = new BufferedReader(new InputStreamReader(new FileInputStream(localDataStoreFilePath), 
					                          StandardCharsets.UTF_8.name()));
			strings = objectMapper.readValue(reader, listType);
		}
		
		return strings;	
	}
	
	public void   persistStrings(List<UnicodeString> strings) throws Exception{
	
		objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		Writer w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(localDataStoreFilePath), 
																StandardCharsets.UTF_8.name()));
		objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		objectMapper.writeValue(w, strings);
	}
}
