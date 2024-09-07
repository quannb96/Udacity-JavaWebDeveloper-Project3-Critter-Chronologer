package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.repositories.CustomerRepository;
import com.udacity.jdnd.course3.critter.repositories.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repositories.PetRepository;
import com.udacity.jdnd.course3.critter.repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class ScheduleService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    public List<Schedule> getAllSchedules() {
        List<Schedule> schedules = scheduleRepository.findAll();
        return schedules;
    }
    
    public Schedule saveSchedule(Schedule schedule, List<Long> employeeIds, List<Long> petIds) {
        List<Pet> pets = petRepository.findAllById(petIds);
        List<Employee> employees = employeeRepository.findAllById(employeeIds);

        schedule.setPets(pets);
        schedule.setEmployees(employees);

        return scheduleRepository.save(schedule);
    }
    
    public List<Schedule> getAllSchedulesForCustomer(Long customerId) {
        Customer customer = customerRepository.getOne(customerId);
        List<Schedule> schedules = scheduleRepository.getSchedulesByPetsIn(customer.getPets());
        return schedules;
    }

    public List<Schedule> getAllSchedulesForEmployee(Long employeeId) {
        Employee employee = employeeRepository.getOne(employeeId);
        List<Schedule> schedules = scheduleRepository.getSchedulesByEmployees(employee);
        return schedules;
    }

    public List<Schedule> getAllSchedulesForPet(Long petId) {
        Pet pet = petRepository.getOne(petId);
        List<Schedule> schedules = scheduleRepository.getSchedulesByPets(pet);
        return schedules;
    }

}
