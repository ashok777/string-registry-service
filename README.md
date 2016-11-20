# string-registry-service
A RESTful service that registers and retrieves Unicode strings

#Building and Installing:

To build and install the string-registry-service on your local workstation please follow the following instructions:

	1. Install JDK 1.8 and set JAVA_HOME to point to the installed directory
	2. In a terminal window cd to the directory where you want the service repository clone deployed
	3. run: git clone https://github.com/ashok777/string-registry-service.git
	4. cd to string-registry-service
	5. The service persists data to a text file in the local file system. Set the property 'local.datastore.file.path' in the 
   	   application.properties file (in src/main/resources) to point to the needed file path. 
   
	6. run: mvnw clean package

	7. To run under spring boot enter
	   mvnw spring-boot:run	
	   The application is now installed without an url segment for the context. During testing use the following base url: 	
	   http://localhost:8080/
	   
	8. To run under tomcat please deploy the war file (target/string-registry.war)to the tomcat webapps directory.
	   The application is now installed with an url segment for the context. During testing use the following base url: 	
	   http://localhost:8080/string-registry/

#API Usage :
