package com.tgrb.banking.utility;

import com.tgrb.banking.dto.EmployeeDTO;
import com.tgrb.banking.entity.Employee;

public class Mapper {
    public static Employee mapToEmployeeEntity(EmployeeDTO employeeDTO){
        Employee employee = new Employee();

        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setEmail(employeeDTO.getEmail());

        return employee;
    }

    public static EmployeeDTO mapToEmployeeDTO(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();

        employeeDTO.setId(employee.getId());
        employeeDTO.setFirstName(employee.getFirstName());
        employeeDTO.setLastName(employee.getLastName());
        employeeDTO.setEmail(employee.getEmail());

        return employeeDTO;
    }
}
