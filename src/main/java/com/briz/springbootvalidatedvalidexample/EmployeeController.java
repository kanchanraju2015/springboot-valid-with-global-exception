package com.briz.springbootvalidatedvalidexample;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Validated  // for method level validation we need to use this
@RestController

// https://blog.devgenius.io/how-to-handle-constraint-violation-exception-using-controlleradvice-in-spring-boot-2f61147d19de
public class EmployeeController 
{
	//  PLEASE USE STARTER VALIDATOR IN POM.XML FILE THEN RUN 
@Autowired
EmployeeRepository erepo;

@RequestMapping("/save")
public String save(@Valid @RequestBody Employee employee)// @ valid for object validation 
{
	// without @Valid it gives bean validation error ConstraintViolationException
	// with @Valid MethodArgumentNotValidException in postman
	// https://www.baeldung.com/spring-boot-bean-validation
	erepo.save(employee);
	return "data is saved";
}

@RequestMapping("/{city}")
public List<Employee> bycity(@PathVariable  @Size(min=3,message="city must be more than 3 letter") String city)
{     // there is no entity level constraint 
	//ConstraintViolationException with @Validated check at the top 
	return erepo.findByCity(city);
}
@RequestMapping("/by/{age}")// ConstraintViolationException: into postman if age<30 OK running 
public List<Employee> byage(@Valid @PathVariable  @Min(30) int age)
{     // there is no entity level constraint 
	//ConstraintViolationException with @Validated check at the top 
	return erepo.findByAge(age);
}
}