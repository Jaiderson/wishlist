package com.books.wishlist;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(JUnitPlatform.class)
@SelectPackages(value = {"com.books.wishlist.services","com.books.wishlist.controllers"})
class WishlistApplicationTests {

	@Test
	void contextLoads() {
	}

}
