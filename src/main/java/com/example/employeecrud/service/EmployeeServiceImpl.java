package com.example.employeecrud.service;

import com.example.employeecrud.entity.Employee;
import com.example.employeecrud.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    @CacheEvict(value = "allEmployees", allEntries = true)   // clear the list cache
    @CachePut(value = "employee", key = "#result.id")        // cache the newly created employee
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    @Cacheable(value = "allEmployees", key = "'all'")
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    @Cacheable(value = "employee", key = "#id")
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + id));
    }

    @Override
    @CacheEvict(value = "allEmployees", allEntries = true)   // list changed
    @CachePut(value = "employee", key = "#id")               // update single employee cache
    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Employee existing = getEmployeeById(id);
        existing.setFirstName(employeeDetails.getFirstName());
        existing.setLastName(employeeDetails.getLastName());
        existing.setEmail(employeeDetails.getEmail());
        return employeeRepository.save(existing);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "employee", key = "#id"),
            @CacheEvict(value = "allEmployees", allEntries = true)
    })  // clear the list cache
    public void deleteEmployee(Long id) {
        Employee existing = getEmployeeById(id);
        employeeRepository.delete(existing);
    }
}