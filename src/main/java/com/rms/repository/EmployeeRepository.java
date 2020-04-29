/**
 * 
 */
package com.rms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rms.model.EmployeeModel;

/**
 * @author Kanitta Moonlapong
 *
 */
public interface EmployeeRepository extends JpaRepository<EmployeeModel, String> {
}