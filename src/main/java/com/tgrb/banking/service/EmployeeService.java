package com.tgrb.banking.service;

import com.tgrb.banking.dto.EmployeeDTO;
import com.tgrb.banking.exception.CustomException_Employee;
import com.tgrb.banking.utility.PaginatedResponse;

import java.util.List;

public interface EmployeeService {
    EmployeeDTO addNewEmployee(EmployeeDTO employeeDTO) throws CustomException_Employee;
    EmployeeDTO updateEmployeeById(Long id, EmployeeDTO employeeDTO) throws CustomException_Employee;
    EmployeeDTO getEmployeeById(Long id) throws CustomException_Employee;
    List<EmployeeDTO> getAllEmployees();
    PaginatedResponse<EmployeeDTO> getEmployees(int pageNumber, int pageSize, String sortBy, String orderBy);
    boolean deleteEmployeeById(Long id) throws CustomException_Employee;
}
