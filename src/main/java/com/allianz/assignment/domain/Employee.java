package com.allianz.assignment.domain;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import com.fasterxml.jackson.annotation.JsonProperty;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
@RedisHash("employee")
public class Employee implements Serializable {
	@JsonProperty("id")
	@Id
	private String id;
	@JsonProperty("employee-name")
	private String name;
	@JsonProperty("job-title")
	private String jobTitle;
	@JsonProperty("department")
	private String department;
	@JsonProperty("status")
	private String status;
	@JsonProperty("year-of-service")
	private String yearOfService;
}
