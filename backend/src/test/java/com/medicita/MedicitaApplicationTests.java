package com.medicita;

import com.medicita.DTO.LoginRequestDTO;
import com.medicita.DTO.LoginResponseDTO;
import com.medicita.controller.AuthController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MedicitaApplicationTests {

	@Autowired
	private AuthController authController;

	@Test
	void contextLoads() {
	}

	@Test
	void testAdminAutoCreatedAndCanLogin() {
		LoginRequestDTO request = new LoginRequestDTO();
		request.setUserName("admin");
		request.setPassword("admin123");

		LoginResponseDTO response = authController.login(request);
		assertNotNull(response);
		assertEquals("ADMIN", response.getRol());
		assertNotNull(response.getUserId());
	}
}
