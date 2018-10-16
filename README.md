# Stock Exchange


## About ##

This is a rest application which deals with various CRUD operations on Stock. 
The application uses [Spring Boot](http://projects.spring.io/spring-boot/), so it is easy to run. This application make use of in memory H2 database. 
On startup, application will load some initial stock entries into the H2 database.

## What you’ll need ##

JDK 1.8 or later

 Maven 3.2+

You can also import the code straight into your IDE: Such as below, or anyone of your choice

```
Spring Tool Suite (STS)

IntelliJ IDEA

```
## Getting started ##

Follow the steps to use the application. This will start app at the port 8080.

    1. Download and unzip the source repository for this application, or clone it using Git: 
    
			git clone https://github.com/rohitedu08/stock-exchange.git

    2. Go to the root directory of the cloned project using command prompt/terminal

    3.  Running the project, choose any of the following

	   * Run the `main` method from `StockExchangeApplication`
	   * Use the Maven Spring Boot plugin: `mvn spring-boot:run`
	   * Package the application as a JAR by executing mvn clean package and run it using `java -jar stock-exchange.jar`

## Access the application UI ##

Once the application is started, UI can be accessed on following page. This is a very basic page used only to visualize add and update Stock options.

			http://localhost:8080/viewstocks.html

## Swagger UI ##

To see the list of all operations supported by App and for API documentation, go to the following link.

	http://localhost:8080/swagger-ui.html

Click on List operations and you will see the list of all supported operations. The list includes valid HTTP methods (GET, POST, PUT).    Expanding each method provides additional useful data, such as response status, content-type, and a list of parameters. 
	
	It is also possible to try each method using the swagger UI.
	
	
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
