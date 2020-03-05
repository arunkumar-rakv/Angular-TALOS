package net.guides.springboot2.springboot2jpacrudexample.controller;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.opsworks.model.AppType;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.guides.springboot2.springboot2jpacrudexample.exception.ResourceNotFoundException;
import net.guides.springboot2.springboot2jpacrudexample.model.AppRequest;
import net.guides.springboot2.springboot2jpacrudexample.model.Employee;
import net.guides.springboot2.springboot2jpacrudexample.repository.AppRequestRepository;
import net.guides.springboot2.springboot2jpacrudexample.repository.EmployeeRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private AppRequestRepository appRequestRepository;

	@GetMapping("/employees")
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
		return ResponseEntity.ok().body(employee);
	}

	@PostMapping("/employees")
	public Employee createEmployee(@Valid @RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}

	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long employeeId,
			@Valid @RequestBody Employee employeeDetails) throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

		employee.setEmailId(employeeDetails.getEmailId());
		employee.setLastName(employeeDetails.getLastName());
		employee.setFirstName(employeeDetails.getFirstName());
		final Employee updatedEmployee = employeeRepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}

	@DeleteMapping("/employees/{id}")
	public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

		employeeRepository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	
	@PostMapping("/createRequest")
	public AppRequest createRequest(@Valid @RequestBody AppRequest appRequest) throws JsonProcessingException {
		AppRequest appRequestFinal = appRequestRepository.save(appRequest);
		AWSCredentials credentials = new BasicAWSCredentials("accessKeyID", "accessKey");
		AmazonS3 s3client = AmazonS3ClientBuilder
				  .standard()
				  .withCredentials(new AWSStaticCredentialsProvider(credentials))
				  .withRegion(Regions.US_EAST_2)
				  .build();
		
		ObjectMapper objectMapper = new ObjectMapper();
		String appRequestStr = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(appRequestFinal);
		s3client.putObject("lambda-s3-example", "applicationDetails-"+appRequestFinal.getId()+".json", appRequestStr);
		return appRequestFinal;
	}
	
	@GetMapping("/getRequestList")
	public List<AppRequest> getRequestList() {
		return appRequestRepository.findAll();
	}
	
	@DeleteMapping("/deleteRequest/{id}")
	public Map<String, Boolean> deleteRequest(@PathVariable(value = "id") Long requestId)
			throws ResourceNotFoundException {
		AppRequest appRequest = appRequestRepository.findById(requestId)
				.orElseThrow(() -> new ResourceNotFoundException("Request not found for this id :: " + requestId));

		appRequestRepository.delete(appRequest);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	
	@PutMapping("/updateRequest/{id}")
	public ResponseEntity<AppRequest> updateRequest(@PathVariable(value = "id") Long requestId,
			@Valid @RequestBody AppRequest appRequestLatest) throws ResourceNotFoundException {
		AppRequest appRequest = appRequestRepository.findById(requestId)
				.orElseThrow(() -> new ResourceNotFoundException("Request not found for this id :: " + requestId));
		
		appRequest.setAppId(appRequestLatest.getAppId());
		appRequest.setAppName(appRequestLatest.getAppName());
		appRequest.setEnvironment(appRequestLatest.getEnvironment());
		appRequest.setRegion(appRequestLatest.getRegion());
		appRequest.setAsgDesired(appRequestLatest.getAsgDesired());
		appRequest.setAsgMax(appRequestLatest.getAsgMax());
		appRequest.setAsgMin(appRequestLatest.getAsgMin());
		
		final AppRequest updatedAppRequest = appRequestRepository.save(appRequest);
		return ResponseEntity.ok(updatedAppRequest);
	}
	
	@GetMapping("/getRequest/{id}")
	public ResponseEntity<AppRequest> getAppRequestById(@PathVariable(value = "id") Long requestId)
			throws ResourceNotFoundException {
		AppRequest appRequest = appRequestRepository.findById(requestId)
				.orElseThrow(() -> new ResourceNotFoundException("Request not found for this id :: " + requestId));
		return ResponseEntity.ok().body(appRequest);
	}
}
