
package com.stringregistry.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import static org.mockito.Matchers.any;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.stringregistry.app.Application;
import com.stringregistry.domain.StringHelper;
import com.stringregistry.model.UnicodeString;

import org.springframework.http.MediaType;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes =Application.class)
@AutoConfigureMockMvc

public class StringRegistryControllerTests {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private StringHelper stringHelper;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @SuppressWarnings("rawtypes")
	private HttpMessageConverter mappingJackson2HttpMessageConverter;
      
    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
            .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
            .findAny()
            .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }
 
    @Test
    public void saveString() throws Exception {
    	
    	UnicodeString  payload = new UnicodeString("def");
        String payloadJson = json(payload);

        int expectedId = 504;
        UnicodeString  processedString = new UnicodeString(expectedId, "def");
             	
        Mockito.doReturn(processedString).when(this.stringHelper).processStringAndSave(any(UnicodeString.class));
               
        this.mockMvc.perform(post("/strings")
                .contentType(contentType)
                .content(payloadJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(expectedId)));
    }
    @Test
    public void getStrings() throws Exception {
    	
    	int expectedId = 504;
    	UnicodeString  payload = new UnicodeString(expectedId, "def");
    	List<UnicodeString> list = new ArrayList<UnicodeString>() ;
    	list.add(payload);
    	
    	Mockito.doReturn(list).when(this.stringHelper).lookupStringsForId(anyInt());
    	   	
        this.mockMvc.perform(get("/strings/504"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$[0].id", is(expectedId)));
    }
 
    @SuppressWarnings("unchecked")
	protected String json(Object o) throws IOException {
    	
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
