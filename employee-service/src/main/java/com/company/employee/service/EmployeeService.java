// package com.company.employee.service;

// import com.company.employee.entity.Employee;
// import com.company.employee.exception.ResourceNotFoundException;
// import com.company.employee.repository.EmployeeRepository;
// import org.springframework.stereotype.Service;

// import java.util.List;

// @Service
// This declaration defines the main type represented by this source file.
// public class EmployeeService {

//     private final EmployeeRepository employeeRepository;

//     public EmployeeService(
//             EmployeeRepository employeeRepository
//     ) {
//         this.employeeRepository = employeeRepository;
//     }

//     public Employee createEmployee(Employee employee) {

//         return employeeRepository.save(employee);
//     }

//     public List<Employee> getAllEmployees() {

//         return employeeRepository.findAll();
//     }

//     public Employee getEmployeeById(Long id) {

//         return employeeRepository.findById(id)
//         //if employee is not found, throw a RuntimeException with a message properly formatted
//                 .orElseThrow(() -> new RuntimeException(
//                         "Employee not found with id: " + id
//                 ));
//     }
//     //update an existing employee by id, if not found throw ResourceNotFoundException
//     public Employee updateEmployee(
//         Long id,
//         Employee updatedEmployee
// ) {

//     Employee existingEmployee =
//             employeeRepository
//                     .findById(id)
//                     .orElseThrow(
//                             () -> new ResourceNotFoundException(
//                                     "Employee not found with id: " + id
//                             )
//                     );

//     existingEmployee.setName(
//             updatedEmployee.getName()
//     );

//     existingEmployee.setEmail(
//             updatedEmployee.getEmail()
//     );

//     existingEmployee.setDesignation(
//             updatedEmployee.getDesignation()
//     );

//     existingEmployee.setSalary(
//             updatedEmployee.getSalary()
//     );

//     return employeeRepository.save(existingEmployee);
// }

// public void deleteEmployee(Long id) {

//     Employee employee =
//             employeeRepository
//                     .findById(id)
//                     .orElseThrow(
//                             () -> new ResourceNotFoundException(
//                                     "Employee not found with id: " + id
//                             )
//                     );

//     employeeRepository.delete(employee);
// }
// }

//Complete changes in the EmployeeService class to use DTOs and Mapper for better separation of concerns and data transfer. 
// The updated EmployeeService class now handles EmployeeRequest and EmployeeResponse DTOs, uses EmployeeMapper for entity conversion, and includes proper exception handling with ResourceNotFoundException.
package com.company.employee.service;

import com.company.employee.client.DepartmentClient;
import com.company.employee.dto.EmployeeRequest;
import com.company.employee.dto.EmployeeResponse;
import com.company.employee.entity.Employee;
import com.company.employee.exception.DepartmentNotFoundException;
import com.company.employee.exception.DepartmentServiceUnavailableException;
import com.company.employee.exception.DuplicateResourceException;
import com.company.employee.exception.ResourceNotFoundException;
// Use the mapper so API DTOs stay separate from JPA database entities.
import com.company.employee.mapper.EmployeeMapper;
import com.company.employee.repository.EmployeeRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import feign.FeignException;
import feign.RetryableException; //when no response from department service, we will get RetryableException, so we need to handle it and throw DepartmentServiceUnavailableException
// Registers this class as a Spring service containing business logic.
@Service
public class EmployeeService {

    // Dependency/state used by this class. Constructor injection supplies `employeeRepository` when the class is created.
    private final EmployeeRepository employeeRepository;
    // Dependency/state used by this class. Constructor injection supplies `employeeMapper` when the class is created.
    private final EmployeeMapper employeeMapper;
    //private final DepartmentClient departmentClient;

    // Dependency/state used by this class. Constructor injection supplies `departmentValidationService` when the class is created.
    private final DepartmentValidationService departmentValidationService;

    public EmployeeService(
            EmployeeRepository employeeRepository,
            EmployeeMapper employeeMapper,
            //DepartmentClient departmentClient,
                DepartmentValidationService departmentValidationService
    ) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        //this.departmentClient = departmentClient;
        this.departmentValidationService = departmentValidationService;
    }

    // @Transactional
    // public EmployeeResponse createEmployee(
    //         EmployeeRequest request
    // ) {

    //     Employee employee =
    //             employeeMapper.toEntity(request);

    //     Employee savedEmployee =
    //             employeeRepository.save(employee);

    //     return employeeMapper.toResponse(savedEmployee);
    // }
    //after adding Duplicate error in exception 

//     @Transactional
// public EmployeeResponse createEmployee(
//         EmployeeRequest request
// ) {

//     if (employeeRepository.existsByEmail(
//             request.getEmail()
//     )) {

//         throw new DuplicateResourceException(
//                 "Employee already exists with email: "
//                         + request.getEmail()
//         );
//     }

//        // Check department exists
// //     departmentClient.getDepartmentById(
// //             request.getDepartmentId()
// //     );
// //chnage with dependecy checking        
// validateDepartment(
//         request.getDepartmentId()
// );
   

//     Employee employee =
//             employeeMapper.toEntity(request);

//     Employee savedEmployee =
//             employeeRepository.save(employee);

    

//     return employeeMapper.toResponse(savedEmployee);
// }

//After adding relisence 
// Runs the method inside a database transaction so related changes succeed or roll back together.
@Transactional
public EmployeeResponse createEmployee(
        EmployeeRequest request
) {

    if (employeeRepository.existsByEmail(
            request.getEmail()
    )) {

        // Business rule failed, so throw a domain-specific exception for the global handler to translate.
        throw new DuplicateResourceException(
                "Employee already exists with email: "
                        + request.getEmail()
        );
    }

    departmentValidationService
            .validateDepartment(
                    request.getDepartmentId()
            );

    Employee employee =
            // Use the mapper so API DTOs stay separate from JPA database entities.
            employeeMapper.toEntity(request);

    Employee savedEmployee =
            // save(...) inserts a new row or updates an existing managed row and returns the persisted entity.
            employeeRepository.save(employee);

    // Use the mapper so API DTOs stay separate from JPA database entities.
    return employeeMapper.toResponse(
            savedEmployee
    );
}

    // Runs the method inside a database transaction so related changes succeed or roll back together.
    @Transactional(readOnly = true)
    /**
     * Reads and returns all available records.
     */
    public List<EmployeeResponse> getAllEmployees() {

        // Return the completed result to the caller of this service method.
        return employeeRepository
                // Ask the repository for all rows of this entity from the database.
                .findAll()
                .stream()
                .map(employeeMapper::toResponse)
                .toList();
    }

    // Runs the method inside a database transaction so related changes succeed or roll back together.
    @Transactional(readOnly = true)
    /**
     * Reads the requested data and returns it; missing data is normally converted into a not-found error.
     */
    public EmployeeResponse getEmployeeById(Long id) {

        Employee employee =
                findEmployeeById(id);

        // Use the mapper so API DTOs stay separate from JPA database entities.
        return employeeMapper.toResponse(employee);
    }

    // Runs the method inside a database transaction so related changes succeed or roll back together.
    @Transactional
    public EmployeeResponse updateEmployee(
            Long id,
            EmployeeRequest request
    ) {

        Employee employee =
                findEmployeeById(id);
//if emp1= r@gmail .com and emp2= p@gmail.com so we won't update emp1 with emp2's email, 
// so we need to check if email exists for another employee with a different id,
//gives 409 conflict   
if (employeeRepository
            .existsByEmailAndIdNot(
                    request.getEmail(),
                    id
            )) {

        // Business rule failed, so throw a domain-specific exception for the global handler to translate.
        throw new DuplicateResourceException(
                "Another employee already exists with email: "
                        + request.getEmail()
        );
    }

        departmentValidationService
                .validateDepartment(
                        request.getDepartmentId()
                );

        // Use the mapper so API DTOs stay separate from JPA database entities.
        employeeMapper.updateEntity(
                employee,
                request
        );

        Employee savedEmployee =
                // save(...) inserts a new row or updates an existing managed row and returns the persisted entity.
                employeeRepository.save(employee);

        // Use the mapper so API DTOs stay separate from JPA database entities.
        return employeeMapper.toResponse(savedEmployee);
    }

    // Runs the method inside a database transaction so related changes succeed or roll back together.
    @Transactional
    /**
     * Deletes/removes the requested data after checking that it exists.
     */
    public void deleteEmployee(Long id) {

        Employee employee =
                findEmployeeById(id);

        // Remove the selected record from the database.
        employeeRepository.delete(employee);
    }

    /**
     * Reads the requested data and returns it; missing data is normally converted into a not-found error.
     */
    private Employee findEmployeeById(Long id) {

        // Return the completed result to the caller of this service method.
        return employeeRepository
                // Ask the repository to look up one database record by its primary-key ID.
                .findById(id)
                // If Optional is empty, stop the flow and throw a meaningful application exception.
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Employee not found with id: " + id
                        )
                );
    }

// private void validateDepartment(Long departmentId) {

//     try {

//         departmentClient.getDepartmentById(departmentId);

//     } catch (FeignException.NotFound exception) {

//         throw new DepartmentNotFoundException(
//                 "Department not found with id: "
//                         + departmentId
//         );

//     } catch (RetryableException exception) {

//         throw new DepartmentServiceUnavailableException(
//                 "Department Service is currently unavailable"
//         );

//     } catch (FeignException exception) {

//         throw new DepartmentServiceUnavailableException(
//                 "Department Service returned an error"
//         );
//     }
// }
}
