package com.tgrb.banking.controller;

import com.tgrb.banking.dto.EmployeeDTO;
import com.tgrb.banking.exception.CustomException_Employee;
import com.tgrb.banking.service.EmployeeServiceImpl;
import com.tgrb.banking.utility.APIResponse;
import com.tgrb.banking.utility.PaginatedResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/employee")
@Validated
public class EmployeeController {
    @Autowired
    EmployeeServiceImpl employeeServiceImpl;

    @PostMapping("/add")
    public ResponseEntity<APIResponse<EmployeeDTO>> addEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) throws CustomException_Employee {
        EmployeeDTO emp = employeeServiceImpl.addNewEmployee(employeeDTO);
        return new ResponseEntity<>(new APIResponse<EmployeeDTO>(true,emp,201), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<APIResponse<EmployeeDTO>> updateEmployeeById(@PathVariable("id") Long id, @Valid @RequestBody EmployeeDTO employeeDTO) throws CustomException_Employee {
        EmployeeDTO employee = employeeServiceImpl.updateEmployeeById(id,employeeDTO);
        return new ResponseEntity<>(new APIResponse<EmployeeDTO>(true,employee,200), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<EmployeeDTO>> getEmployeeById(@PathVariable("id") Long id) throws CustomException_Employee{
        EmployeeDTO employee = employeeServiceImpl.getEmployeeById(id);

        return new ResponseEntity<>(new APIResponse<>(true,employee,200), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<APIResponse<List<EmployeeDTO>>> getAllEmployees() throws  CustomException_Employee{
        List<EmployeeDTO> employee = employeeServiceImpl.getAllEmployees();

        return new ResponseEntity<>(new APIResponse<>(true,employee,200), HttpStatus.OK);
    }

//    by default page:0, pageSize:5
    @GetMapping("/get")
    public ResponseEntity<APIResponse<PaginatedResponse<EmployeeDTO>>> getEmployees(@RequestParam(name = "pageNumber",defaultValue = "0") int pageNumber, @RequestParam(name = "pageSize", defaultValue = "5") int pageSize
            , @RequestParam(name = "sortBy", defaultValue = "id")
                @Pattern(regexp = "id|email|firstName|lastName", message = "orderBy should only be among the properties.") String sortBy
            , @RequestParam(name = "orderBy", defaultValue = "asc")
                @Pattern(regexp = "asc|ASC|desc|DESC", message = "sortBy should only be asc or desc.") String orderBy) throws  CustomException_Employee{
        PaginatedResponse<EmployeeDTO> PaginatedResponse = employeeServiceImpl.getEmployees(pageNumber,pageSize, sortBy,orderBy);

        return new ResponseEntity<>(new APIResponse<>(true,PaginatedResponse,200), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<EmployeeDTO>> deleteEmployeeById(@PathVariable("id") Long id) throws CustomException_Employee{
        employeeServiceImpl.deleteEmployeeById(id);

        return new ResponseEntity<>(new APIResponse<>(true,null,200), HttpStatus.OK);
    }
}
