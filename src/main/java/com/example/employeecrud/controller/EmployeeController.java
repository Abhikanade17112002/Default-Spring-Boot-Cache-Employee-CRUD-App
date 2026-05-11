package com.example.employeecrud.controller;

import com.example.employeecrud.entity.Employee;
import com.example.employeecrud.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // Create – evict allEmployees cache, and cache the new employee by id
    @PostMapping
    @CacheEvict(value = "allEmployees", allEntries = true)   // clear the list cache
    @CachePut(value = "employee", key = "#result.body.id")        // cache the newly created employee
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        Employee saved = employeeService.createEmployee(employee);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // Get all – cache the whole list with a constant key
    @GetMapping
    @Cacheable(value = "allEmployees", key = "'all'")
    public List<Employee> getAll() {
        return employeeService.getAllEmployees();
    }

    // Get by id – cache individual employee
    @GetMapping("/{id}")
    @Cacheable(value = "employee", key = "#id")
    public ResponseEntity<Employee> getById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    // Update – evict allEmployees cache, update single employee cache
    @PutMapping("/{id}")
    @CacheEvict(value = "allEmployees", allEntries = true)   // list changed
    @CachePut(value = "employee", key = "#id")               // update single employee cache
    public ResponseEntity<Employee> update(@PathVariable Long id, @RequestBody Employee employee) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, employee));
    }

    // Delete – evict both caches
    @DeleteMapping("/{id}")
    @Caching(evict = {
            @CacheEvict(value = "employee", key = "#id"),
            @CacheEvict(value = "allEmployees", allEntries = true)
    })  // clear the list cache
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}