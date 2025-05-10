package com.avinash.prod_ready_features.prod_ready_features.client.Implementation;

import com.avinash.prod_ready_features.prod_ready_features.advices.ApiResponse;
import com.avinash.prod_ready_features.prod_ready_features.client.EmployeeClient;
import com.avinash.prod_ready_features.prod_ready_features.dto.EmployeeDto;
import com.avinash.prod_ready_features.prod_ready_features.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeClientEmpl implements EmployeeClient {

    private final RestClient restClient;

 Logger log = LoggerFactory.getLogger(EmployeeClientEmpl.class);

    @Override
    public List<EmployeeDto> getAllEmployees() {

log.info("trying to fetch user details");
log.info("trying to fetch user details");
log.info("trying to fetch user details");
log.info("trying to fetch user details");
log.info("trying to fetch user details");
log.info("trying to fetch user details");
log.info("trying to fetch user details");
log.info("trying to fetch user details");
log.info("trying to fetch user details");
log.info("trying to fetch user details");
log.info("trying to fetch user details");
log.info("trying to fetch user details");
log.info("trying to fetch user details");
log.info("trying to fetch user details");
log.info("trying to fetch user details");
log.info("trying to fetch user details");
log.info("trying to fetch user details");
log.info("trying to fetch user details");
log.info("trying to fetch user details");
log.info("trying to fetch user details");
log.info("trying to fetch user details");
log.info("trying to fetch user details");
log.info("trying to fetch user details");
log.info("trying to fetch user details");
log.info("trying to fetch user details");
log.info("trying to fetch user details");
log.info("trying to fetch user details");
log.info("trying to fetch user details");
log.info("trying to fetch user details");
log.info("trying to fetch user details");
log.info("trying to fetch user details");
log.info("trying to fetch user details");
log.info("trying to fetch user details");
log.info("trying to fetch user details");
log.info("trying to fetch user details");
log.info("trying to fetch user details");
log.info("trying to fetch user details");
log.info("trying to fetch user details");
log.info("trying to fetch user details");
log.info("trying to fetch user details");
log.info("trying to fetch user details");
log.info("trying to fetch user details");
log.info("trying to fetch user details");
log.info("trying to fetch user details");
log.info("trying to fetch user details");


//a new change to check branching
//        checking to in temp2 branch
    try{
        ApiResponse<List<EmployeeDto>> employeeDtoList = restClient.get()
                .uri("employee/getall")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,(req,res) -> {
                    System.out.println(new String(res.getBody().readAllBytes()));
                    throw new ResourceNotFoundException("could not create the employee");
                })
                .body(new ParameterizedTypeReference<>() {
                });
log.debug("user details fetched successfully");
        return employeeDtoList.getData();
    }
    catch (Exception e)
    {
        log.debug("exception came in getAllEmployee");
       throw new RuntimeException(e);
    }
    }


//    emp by Id
    @Override
    public EmployeeDto getEmployeeById(Long empId)
    {
        try{

            ApiResponse<EmployeeDto> empApiResponse = restClient.get()
                    .uri("employee/{id}",empId)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });

            return empApiResponse.getData();

        }catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }

//creat new employee
    @Override
    public EmployeeDto createNewEmployee(EmployeeDto employeeDto)
    {
        try{
//

//  -----------------   1..body()--------------------------------------------------------------------------------------------------

//            ApiResponse<EmployeeDto> employeeDtoApiResponse = restClient.post()
//                    .uri("employee/create")
//                    .body(employeeDto)
//                    .retrieve()
//                    .onStatus(HttpStatusCode::is4xxClientError,(req,res) -> {
//                        System.out.println(new String(res.getBody().readAllBytes()));
//                        throw new ResourceNotFoundException("could not create the employee");
//                    })
//                    .body(new ParameterizedTypeReference<>() {                      /// .body()  --> it gives only body
//                    });
//
//            return employeeDtoApiResponse.getData();


//  -------------------------------------------------------------------------------------------------------------------





//  --------------------------------//2..toEntity()-----------------------------------------------------------------------------------


            ResponseEntity<ApiResponse<EmployeeDto>> employeeDtoApiResponse = restClient.post()
                    .uri("employee/create")
                    .body(employeeDto)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError,(req,res) -> {
                        System.out.println(new String(res.getBody().readAllBytes()));
                        throw new ResourceNotFoundException("could not create the employee");
                    })
                    .toEntity(new ParameterizedTypeReference<>() {                      /// .toEntity()  --> it gives body,statusCodes,errors,etc.
                    });

            System.out.println(employeeDtoApiResponse.getStatusCode());   /// employeeDtoApiResponse -> it has many methods, you can explore it.

            return employeeDtoApiResponse.getBody().getData();

        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
        //  -------------------------------------------------------------------------------------------------------------------

    }
}
