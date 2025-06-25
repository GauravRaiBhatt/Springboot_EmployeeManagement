package com.tgrb.banking.service;

import com.tgrb.banking.dto.EmployeeDTO;
import com.tgrb.banking.entity.Employee;
import com.tgrb.banking.exception.CustomException_Employee;
import com.tgrb.banking.repository.EmployeeRepository;
import com.tgrb.banking.utility.Mapper;
import com.tgrb.banking.utility.PaginatedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService{
    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public EmployeeDTO addNewEmployee(EmployeeDTO employeeDTO) throws CustomException_Employee {
        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(employeeDTO.getEmail());
        if (optionalEmployee.isPresent())
            throw new CustomException_Employee("Employee with the email is already registered.");

        Employee savedEmployee = employeeRepository.save(Mapper.mapToEmployeeEntity(employeeDTO));
        return Mapper.mapToEmployeeDTO(savedEmployee);
    }

    @Override
    public EmployeeDTO updateEmployeeById(Long id, EmployeeDTO employeeDTO) throws CustomException_Employee {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);

        Employee existingEmployee = optionalEmployee.orElseThrow(() -> new CustomException_Employee("Employee with the given id does not exists."));
        existingEmployee.setFirstName(employeeDTO.getFirstName());
        existingEmployee.setLastName(employeeDTO.getLastName());
        existingEmployee.setEmail(employeeDTO.getEmail());

        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        return Mapper.mapToEmployeeDTO(updatedEmployee);
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) throws CustomException_Employee {
        Optional<Employee> employee = employeeRepository.findById(id);
        Employee existingEmployee = employee.orElseThrow(()-> new CustomException_Employee("Employee with the id "+ id + " does not exists"));

        return Mapper.mapToEmployeeDTO(existingEmployee);
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employeeList = employeeRepository.findAll();

        List<EmployeeDTO> listOfEmployeeDTOs = employeeList.stream()
                .map(employee -> Mapper.mapToEmployeeDTO(employee))
                .collect(Collectors.toList());

        return listOfEmployeeDTOs;
    }

    @Override
    public PaginatedResponse<EmployeeDTO> getEmployees(int pageNumber, int pageSize, String sortBy, String orderBy) {
        Sort.Direction orderType = Sort.Direction.valueOf(orderBy.toUpperCase());
        Sort sort = Sort.by(orderType, sortBy);
        Pageable pageable = PageRequest.of(pageNumber,pageSize, sort);
        Page<Employee> page = employeeRepository.findAll(pageable);
        List<Employee> employeeList = page.getContent();
        List<EmployeeDTO> employeeDTOList = employeeList.stream().map(Mapper::mapToEmployeeDTO).toList();

        PaginatedResponse<EmployeeDTO> paginatedResponse = new PaginatedResponse<>();
        paginatedResponse.setPageNumber(page.getNumber());
        paginatedResponse.setPageSize(page.getSize());
        paginatedResponse.setContent(employeeDTOList);
        paginatedResponse.setNoOfRecordsInThisPage(page.getNumberOfElements());
        paginatedResponse.setTotalNoOfRecords(page.getTotalElements());
        paginatedResponse.setLastPage(page.isLast());
        paginatedResponse.setTotalNoOfPages(page.getTotalPages());
//        paginatedResponse.setTotalNoOfPages(page.getTotalPages() - 1);

        return paginatedResponse;
    }

    @Override
    public boolean deleteEmployeeById(Long id) throws CustomException_Employee {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        Employee employee = optionalEmployee.orElseThrow(() -> new CustomException_Employee("Employee with the id "+ id + " does not exists"));
        employeeRepository.delete(employee);

        return true;
    }


}
