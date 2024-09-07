package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.repositories.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Transactional
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getEmployeesForService(LocalDate date, Set<EmployeeSkill> skills) {
        List<Employee> availableEmployees = employeeRepository.getEmployeesByDaysAvailable(date.getDayOfWeek());
        List<Employee> selectedEmployees = new ArrayList<>();
        for (Employee employee : availableEmployees) {
            if (employee.getSkills().containsAll(skills)) {
                selectedEmployees.add(employee);
            }
        }
        return selectedEmployees;
    }

    public Employee getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.getOne(employeeId);
        return employee;
    }

    public Employee saveEmployee(Employee employee) {
        Employee newEmployee = employeeRepository.save(employee);
        return newEmployee;
    }

    public void setEmployeeAvailability(Set<DayOfWeek> days, Long employeeId) {
        Employee employee = employeeRepository.getOne(employeeId);
        employee.setDaysAvailable(days);
        employeeRepository.save(employee);
    }

}
