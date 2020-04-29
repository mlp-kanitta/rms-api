/**
 * 
 */
package com.rms.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import com.rms.validation.Employee;

/**
 * @author Kanitta Moonlapong
 *
 */
@Entity
public class EmployeeModel {

	@Id
	@Employee
	@NotEmpty(message = "Please provide a employee id")
	private String id;
	@Employee
	@NotEmpty(message = "Please provide a first name")
	private String firstName;
	@Employee
	@NotEmpty(message = "Please provide a last name")
	private String lastName;
	private String contactNo;
	private BigDecimal salary;

	public EmployeeModel() {
		super();
	}

	public EmployeeModel(String id, String firstName, String lastName, String contactNo, BigDecimal salary) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.contactNo = contactNo;
		this.salary = salary;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

}
