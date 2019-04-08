# Avenue Code Java Assessment #

+ I have implement search rest API which contains 4 end-points for those 4 criteria.

	- [GET] http://localhost:8090/search/get-orders?status={status}
	
	- [GET] http://localhost:8090/search/get-discounted-orders
	
	- [GET] http://localhost:8090/search/get-orders-with-more-than-numberofproduct?numberOfProduct={numberOfProduct}
	
	- [GET] http://localhost:8090/search/get-product-with-price-more-than?price={price}

+ Also, 3 APIs for adding order, product and product to order.
Those 3 APIs take json as input and response as json too.
You can test those via Postman (a chrome plugin for testing REST web service).
	
	- [POST] http://localhost:8090/orders/addOrder
	 	Sample JSON:
	 	{
			"orderNumber": "Test101",
			"discount": 0,
			"taxPercent": 8.9,
			"status": "FULFILLED",
			"products" : [{
					"upc": "TestUPC01",
					"sku": "TestSKU01",
					"description": "This the test data 01",
					"price": 75
				}, {
					"upc": "TestUPC02",
					"sku": "TestSKU02",
					"description": "This the test data 02",
					"price": 100
				}
			]
		}
		
	- [POST] http://localhost:8090/products/addProduct
		{
			"upc": "TestUPC03",
			"sku": "TestSKU03",
			"description": "This the test data 03",
			"price": 98
		}

	- [POST] http://localhost:8090/products/addProductToOrder
		{
			"upc": "TestUPC04",
			"sku": "TestSKU04",
			"description": "This the test data 04",
			"price": 59,
			"orderNumber": "RTL_1001"
		}

+ To compile and run the application is: right click on the application -> run as -> Spring Boot App

+ All of unit tests and integration testing are in src/test/java

+ Test suit is located in com.avenuecode.orders package
	- To run that test suit: right click on OrdersTestSuit.java -> run as -> Junit Test
	
+ Integration testing is also located in com.avenuecode.orders package
	- I have separated the integration test and unit test because integration test is end-to-end testing which is different from unit test which is particular class testing
	- There are 3 integration testings: OrderResourceIntegrationTesting.java, ProductResourceIntegrationTesting.java, SearchResourceIntegrationTesting.java
	- To run those integration testings: right click on each one of them -> run as -> Junit Test