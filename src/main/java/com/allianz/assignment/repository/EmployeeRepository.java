package com.allianz.assignment.repository;

import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;

import com.allianz.assignment.domain.Employee;

@EnableRedisRepositories
public interface EmployeeRepository extends CrudRepository<Employee, String> {

}
