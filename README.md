# string-registry-service
A RESTful service that registers and retrieves Unicode strings

#Building and Deploying:

To build and deploy the string-registry-service on your local workstation please read the following instructions:

	1. Install JDK 1.8 and set JAVA_HOME to point to the installed directory
	2. In a terminal window cd to the directory where you want the service repository clone deployed
	3. run: git clone https://github.com/ashok777/string-registry-service.git
	4. cd to string-registry-service
	5. Optional customizations:
	
	   The service persists data to a text file in the local file system. 
	   Set the property 'local.datastore.file.path' in the application.properties 
	   file (in src/main/resources) to point to the needed fully qualified file path. 
	   
	   Log files are written to the file specified in the 'logging.file' property; 
	   set the fully qualified file path as needed

	6. run: mvnw clean package
	   This results in a .war file being produced (target/string-registry.war)
	
	7. To run under spring boot enter
	   mvnw spring-boot:run	
	   The application is now installed without an url segment for the context.
	   During testing use the following base url: http://localhost:8080/

	8. To run under tomcat please deploy the war file to the tomcat webapps directory.
	   The application is now installed with an url segment for the context. 
	   During testing use the following base url: http://localhost:8080/string-registry/

#API Usage :

	1. To post a string to the service submit following request (done through curl or postman)  
	
		POST /strings
		Accept: application/json
		Content-Type: application/json

		content body :
		{
			"text" : "The quick brown fox"
		}

		RESPONSE: HTTP 201 (Created)	
		response body:
		{
			"id": 3502,
			"text": "The quick brown fox"
		}
		The response is returned with an id with which the string is identified and 
		stored in the service's local data store. Please note that the request content body 
		has to strictly conform to the above json or else an Http BAD_REQUEST response will result.
		
	2. To retrieve a string, submit the following request using the general url pattern '/strings/{stringId}'	
	
		GET /strings/3502
		Accept: application/json

		RESPONSE: HTTP 200(OK)	
		response body:
		[
		{
			"id": 3502,
			"text": "The quick brown fox"
		}
		]
		
	3. Any error in user input will result in a standard error response; 
	   the following request has an incorrect url 	
	
		GET /strings/ddd/489
		Accept: application/json

		RESPONSE: HTTP 400(BAD REQUEST)	
		response body:
		{
			"errorCode": 400,
			"message": "An error ocurred while processing your request. No handler found for GET /strings/ddd/489"
		}	
