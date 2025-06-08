package com.avinash.SequirityApp.SequirityApp;

import com.avinash.SequirityApp.SequirityApp.entities.User;
import com.avinash.SequirityApp.SequirityApp.services.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SequirityAppApplicationTests {

	@Autowired
	private JwtService jwtService;

	@Test
	void contextLoads(){
//		User user = new User(5L,"Avinash Tiwari","2341");
//
//		String token = jwtService.generateToken(user);            ///// this token you can paste on jwt.io to see actual header and payload from this token
//// this token expires after expiry time as you mentioned( in this app, i mention 1 minute)
//
//
//		System.out.println("====================generated token======================");
//		System.out.println(token);
//		System.out.println("==================================================================");
//
//		Long id = jwtService.getUserIdFromToken(token);
//
//		System.out.println("====================retriving user_id from token======================");
//		System.out.println(id);
//		System.out.println("==================================================================");

	}

}
