package com.avenuecode.orders;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.avenuecode.orders.repository.OrderRepositoryTest;
import com.avenuecode.orders.repository.ProductRepositoryTest;
import com.avenuecode.orders.resource.OrderResourceTest;
import com.avenuecode.orders.resource.ProductResourceTest;
import com.avenuecode.orders.resource.SearchResourceTest;
import com.avenuecode.orders.service.OrderServiceTest;
import com.avenuecode.orders.service.ProductServiceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ SearchResourceIntegrationTesting.class, OrderResourceIntergrationTesting.class,
		ProductResourceIntegrationTesting.class, OrderRepositoryTest.class, ProductRepositoryTest.class,
		OrderServiceTest.class, ProductServiceTest.class, OrderResourceTest.class, ProductResourceTest.class,
		SearchResourceTest.class })
public class OrdersTestSuit {

}
