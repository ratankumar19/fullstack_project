// package com.company.employee.controller;

// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.DeleteMapping;
// //3 import for 3 annotations
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.company.employee.entity.Employee;
// import com.company.employee.service.EmployeeService;
// import java.util.List;   // <-- ADD THIS
// @RestController
// //it is a combination of @Controller and @ResponseBody annotations
// @RequestMapping("/api/employees") 
// //base path for all the endpoints in this controller:http://localhost:8080/api/employees/
// This declaration defines the main type represented by this source file.
// public class EmployeeController {

//     @GetMapping("/test")
//     public String test() {
//         return "Employee Service is running";
//     }

//     //injecting the EmployeeService dependency into the controller

//     private final EmployeeService employeeService;

//     public EmployeeController(
//             EmployeeService employeeService
//     ) {
//         this.employeeService = employeeService;
//     }

//     @PostMapping
//     public ResponseEntity<Employee> createEmployee(
//             @RequestBody Employee employee
//     ) {

//         Employee createdEmployee =
//                 employeeService.createEmployee(employee);

//         return ResponseEntity
//                 .status(HttpStatus.CREATED)
//                 .body(createdEmployee);
//     }

//     @GetMapping
//     public ResponseEntity<List<Employee>> getAllEmployees() {

//         return ResponseEntity.ok(
//                 employeeService.getAllEmployees()
//         );
//     }

//     @GetMapping("/{id}")
// public ResponseEntity<Employee> getEmployeeById(
//         @PathVariable Long id
// ) {

//     return ResponseEntity.ok(
//             employeeService.getEmployeeById(id)
//     );
// }
// //update mapping 
// @PutMapping("/{id}")
// public ResponseEntity<Employee> updateEmployee(
//         @PathVariable Long id,
//         @RequestBody Employee employee
// ) {

//     return ResponseEntity.ok(
//             employeeService.updateEmployee(id, employee)
//     );
// }

// @DeleteMapping("/{id}")
// public ResponseEntity<Void> deleteEmployee(
//         @PathVariable Long id
// ) {

//     employeeService.deleteEmployee(id);

//     return ResponseEntity.noContent().build();
// }
// }

//new controller code and refractored code to use DTOs and Mapper for better separation of concerns and data transfer. 
// The updated EmployeeController class now handles EmployeeRequest and EmployeeResponse DTOs, 
// uses EmployeeService for business logic, and includes proper exception handling with ResourceNotFoundException.
package com.company.employee.controller;

import com.company.employee.dto.EmployeeRequest;
import com.company.employee.dto.EmployeeResponse;
import com.company.employee.service.EmployeeService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Marks this class as a REST controller; returned objects are serialized to the HTTP response body.
@RestController
// Defines the common/base URL path handled by this controller.
@RequestMapping("/api/employees")
public class EmployeeController {

    // Dependency/state used by this class. Constructor injection supplies `employeeService` when the class is created.
    private final EmployeeService employeeService;

    public EmployeeController(
            EmployeeService employeeService
    ) {
        this.employeeService = employeeService;
    }

    // Maps an HTTP POST request to the method below.
    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(
            // Triggers Bean Validation for the object supplied to the method.
            @Valid @RequestBody EmployeeRequest request
    ) {
//we are prnting here 
          System.out.println(
        "Department received = " + request.getDepartmentId()
    );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                    employeeService.createEmployee(request)
                );
    }

    // Maps an HTTP GET request to the method below.
    @GetMapping
    public ResponseEntity<List<EmployeeResponse>>
    getAllEmployees() {

        return ResponseEntity.ok(
                employeeService.getAllEmployees()
        );
    }

    // Maps an HTTP GET request to the method below.
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse>
    getEmployeeById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                employeeService.getEmployeeById(id)
        );
    }

    // Maps an HTTP PUT request to the method below.
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse>
    updateEmployee(
            @PathVariable Long id,
            // Triggers Bean Validation for the object supplied to the method.
            @Valid @RequestBody EmployeeRequest request
    ) {

        return ResponseEntity.ok(
                employeeService.updateEmployee(
                        id,
                        request
                )
        );
    }

    // Maps an HTTP DELETE request to the method below.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(
            @PathVariable Long id
    ) {

        employeeService.deleteEmployee(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}
