package com.example.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.validation.Valid;

import java.util.Date;
import java.util.List;

import com.example.domain.Employee;
import com.example.domain.ApproveBucket;
import com.example.repository.EmployeeRepository;
import com.example.repository.ApproveRepository;


@RestController
@RequestMapping("/api")
public class EmployeeController {

	@Autowired
	EmployeeRepository EmployeeRepository;
	@Autowired
	ApproveRepository ApproveRepository;

	// Add Employee
	@PostMapping("/employee")
	public Employee addEmployee(@Valid @RequestBody Employee employee) {
		Long statusId=(long) 1;
		employee.setStatusID(statusId);
		return EmployeeRepository.save(employee);
	}

	// Get All employees
	@CrossOrigin(origins = "http://192.168.43.246:8080")
	@GetMapping("/employees")
	public List<Employee> getAllEmployee() {
		return EmployeeRepository.findAll();
	}

	
	// Get All employees in approve table 
	@CrossOrigin(origins = "http://192.168.43.246:8080")
	@GetMapping("/temporary/employees")
	public List<ApproveBucket> getAllTempEmployee() {
		return ApproveRepository.findAll();
	}
	
	//Get a Single employee
	@CrossOrigin(origins = "http://192.168.43.246:8080")
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") Long employeeId) {
		Employee Employee = EmployeeRepository.findOne(employeeId);
		if(Employee == null) {
			return null;
		}
		return ResponseEntity.ok().body(Employee);
	}

	// Update a employee
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateemployee(@PathVariable(value = "id") Long employeeId, 
			@Valid @RequestBody Employee employeeDetails) {

		Employee employee = EmployeeRepository.findOne(employeeId);
		ApproveBucket approveBucket = new ApproveBucket() ;
		if(employee == null) {
			System.out.println("employee Invalid");
			return null;
		}
		approveBucket.setId(employeeId);
		approveBucket.setName(employeeDetails.getName());
		approveBucket.setAddress(employeeDetails.getAddress());
		approveBucket.setManager(employeeDetails.getManager());
		approveBucket.setPosition(employeeDetails.getPosition());
		approveBucket.setCreatedAt(employee.getCreatedAt());
		approveBucket.setUpdatedAt(new Date());
		Long statusId=(long) 2;
		employee.setStatusID(statusId);

		Employee updatedEmployee = EmployeeRepository.save(employee);

		@SuppressWarnings("unused")
		ApproveBucket updateEmployee = ApproveRepository.save(approveBucket);
		System.out.println("employee updated successfully");
		return ResponseEntity.ok(updatedEmployee);
	}

	// Delete a Employee
	@DeleteMapping("/employee/{id}")
	public ResponseEntity<Void> deleteEmployee(@PathVariable(value = "id") Long employeeId) {
		Employee employee = EmployeeRepository.findOne(employeeId);
		ApproveBucket approveDelete = ApproveRepository.findOne(employeeId);
		if(employee == null && approveDelete==null) {
			return ResponseEntity.notFound().build();
		}

		if(employee!=null){
			EmployeeRepository.delete(employee);
			System.out.println("employee deleted successfully");
		}
		
		if(approveDelete!=null){
			ApproveRepository.delete(approveDelete);
			System.out.println("temprory employee deleted successfully");
		}
		
		return ResponseEntity.ok().build();

	}

	//Dis-Approve Changes
	@DeleteMapping("/employee/disapprove/{id}")
	@ResponseStatus
	public ResponseEntity<Employee> disapproveEmployee(@PathVariable(value = "id") Long employeeId) {
		//Employee employee = EmployeeRepository.findOne(employeeId);
		Employee employee=new Employee();
		ApproveBucket approveBucket = ApproveRepository.findOne(employeeId);
		if(approveBucket == null) {
			System.out.println("temprory null for disapprove");
			return null;
		}
		ApproveRepository.delete(approveBucket);
		//return ResponseEntity.ok().build();
		System.out.println("temprory employee deleted successfully");
		Long statusId=(long)4; 
		employee=EmployeeRepository.findOne(employeeId);
		employee.setStatusID(statusId);
		Employee employeeUpdate= EmployeeRepository.save(employee);
		return ResponseEntity.ok().body(employeeUpdate);
	}

	//Approve Changes
	@GetMapping("/employees/approve/{id}")
	public ResponseEntity<Employee> approveEmployeeById(@PathVariable(value = "id") Long employeeId) {

		ApproveBucket approveBucket= ApproveRepository.findOne(employeeId);

		Employee employee=new Employee();
		employee.setId(approveBucket.getId());
		employee.setName(approveBucket.getName());
		employee.setAddress(approveBucket.getAddress());
		employee.setManager(approveBucket.getManager());
		employee.setUpdatedAt(approveBucket.getUpdatedAt());
		employee.setPosition(approveBucket.getPosition());
		Long statusId=(long)3; 
		employee.setStatusID(statusId);

		Employee employeeUpdate= EmployeeRepository.save(employee);
		if(employeeUpdate!=null){
			ApproveRepository.delete(approveBucket);
		}
		return ResponseEntity.ok().body(employeeUpdate);
	}
}