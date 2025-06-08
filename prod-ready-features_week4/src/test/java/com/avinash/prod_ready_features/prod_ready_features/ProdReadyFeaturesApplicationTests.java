package com.avinash.prod_ready_features.prod_ready_features;

import com.avinash.prod_ready_features.prod_ready_features.client.EmployeeClient;
import com.avinash.prod_ready_features.prod_ready_features.dto.EmployeeDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class ProdReadyFeaturesApplicationTests {

	@Autowired
	private EmployeeClient employeeClient;

	@Test
	void getAllEmployees(){
		List<EmployeeDto> employeeDtoList = employeeClient.getAllEmployees();

		System.out.println("===============employeee-list========================================");
		System.out.println(employeeDtoList);
		System.out.println("=====================================================================");
	}


	@Test
	void getEmployeeById(){
		EmployeeDto employee = employeeClient.getEmployeeById(252L);        // casting int to long  => 1 --> 1L

		System.out.println("===============employeee of ${empId}========================================");
		System.out.println(employee);
		System.out.println("=====================================================================");
	}

	@Test
	void createNewEmployee()
	{

		EmployeeDto employeeDto = new EmployeeDto(null,"akankshit","akankshit@gmail.com", LocalDate.of(2024,10,10),
				"USER",25000,21,60,true);
		EmployeeDto savedEmployee = employeeClient.createNewEmployee(employeeDto);
		System.out.println("===============new employee========================================");
		System.out.println(savedEmployee);
		System.out.println("=====================================================================");
	}

}
