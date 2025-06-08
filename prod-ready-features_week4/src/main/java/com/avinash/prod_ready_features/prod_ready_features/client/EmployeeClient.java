package com.avinash.prod_ready_features.prod_ready_features.client;

import com.avinash.prod_ready_features.prod_ready_features.dto.EmployeeDto;

import java.util.List;

public interface EmployeeClient {

    List<EmployeeDto> getAllEmployees();

    EmployeeDto getEmployeeById(Long empId);
    EmployeeDto createNewEmployee(EmployeeDto employeeDto);
}
