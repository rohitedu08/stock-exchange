# Stock Exchange


## About ##

This is a rest application which deals with various CRUD operations on Stock. 
This Application is written in Java 8 using Springboot version 2.0.1. This application make use of in memory H2 database. 
On startup, application will load some initial stock entries into the H2 database.

## What you’ll need ##

JDK 1.8 or later

Gradle 4+ or Maven 3.2+

You can also import the code straight into your IDE: Such as below, or anyone of your choice

```
Spring Tool Suite (STS)

IntelliJ IDEA

```
## Getting started ##

Follow the steps to use the application

    1. Download and unzip the source repository for this application, or clone it using Git: 
    
			git clone https://github.com/rohitedu08/stock-exchange.git

	2. Go to the root directory of the cloned project using command prompt/terminal

	3. The application can now be started using following maven command from the Project root directory. 

			mvn spring-boot:run
The command will start the application and make it available on your local machine on port 8080. if you wish to change the port, please run above command with desired port number as command line argument as shown below.

```
mvn spring-boot:run --server.port=8085
```

## Swagger UI ##

After application is started successfully, You will see the list of operations. Clicking on any of them will list the valid HTTP methods (GET, POST, PUT). Expanding each method provides additional useful data, such as response status, content-type, and a list of parameters. 
	
	It is also possible to try each method using the UI. visit the following URL
	http://localhost:8080/swagger-ui.html
	
## REST endpoints ##

  (1) Get All stocks.
  
         Method : GET 
         URL    : http://localhost:8080/api/stocks
         
  (2) Get a stock by id.
    
         Method : GET 
         URL    : http://localhost:8080/api/stocks/{id}
   
  (3) Create a stock.
      
         Method : POST
         URL    : http://localhost:8080/api/stocks
         Body   : { "name" : "Nestle", "currentPrice": "EUR 12.25"}
  
  (4) Update an existing stock.
        
         Method : PUT
         URL    : http://localhost:8080/api/stocks/{id}
         Body   : { "currentPrice": "EUR 153.47"}
