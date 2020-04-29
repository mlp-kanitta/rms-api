/**
 * 
 */
package com.rms.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.rms.exception.ResourceNotFoundException;
import com.rms.model.EmployeeModel;
import com.rms.repository.EmployeeRepository;
import com.rms.utils.CommonUtils;

/**
 * @author Kanitta Moonlapong
 *
 */
@RestController
public class EmployeeController {

	private static String ERR_PARAM_REQUIRES = "Parameter employee id is requires.";
	private static String ERR_SPECIAL_CHAR_FOUND = "Only alphabet numeric allows.";
	private static String NO_RECORD_FOUND = "No record found.";

	@Autowired
	private EmployeeRepository repository;

	/*
	 * Retrieve all employee.
	 */
	@RequestMapping(value = "/employee-api/all", method = RequestMethod.GET)
	public List<EmployeeModel> getAllEmployee(HttpServletResponse response) {
		return repository.findAll();
	}

	/*
	 * Retrieve an employee by id
	 */
	@RequestMapping(value = "/employee-api/id/{id}", method = RequestMethod.GET)
	public EmployeeModel getEmployeeById(@PathVariable @Min(3) String id, HttpServletResponse response){
		// EmptyId is not allow
		if (!(null != id && id.length() > 0)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ERR_PARAM_REQUIRES);

		}

		// API will only accept alphanumeric characters. If not, reject and throw error
		// message,
		if (!CommonUtils.isAlphaNumeric(id)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ERR_SPECIAL_CHAR_FOUND);
		}

		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(NO_RECORD_FOUND));

	}

	// Save
	@PostMapping("/employee-api/create")
	@ResponseStatus(HttpStatus.CREATED)
	public EmployeeModel employee(@Valid @RequestBody EmployeeModel employee) {
		return repository.save(employee);
	}

	// Save or update
	@PutMapping("/employee-api/update/{id}")
	public EmployeeModel update(@RequestBody EmployeeModel employee, @PathVariable String id){

		return repository.findById(id).map(x -> {
			x.setFirstName(employee.getFirstName());
			x.setLastName(employee.getLastName());
			x.setContactNo(employee.getContactNo());
			x.setSalary(employee.getSalary());
			return repository.save(x);
		}).orElseGet(() -> {
			throw new ResourceNotFoundException(NO_RECORD_FOUND);
		});
	}

	@DeleteMapping("/employee-api/remove/{id}")
	public void deleteEmployee(@PathVariable String id) {
		repository.deleteById(id);
	}
}
