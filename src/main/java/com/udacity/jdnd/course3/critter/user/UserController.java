package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.services.CustomerService;
import com.udacity.jdnd.course3.critter.services.EmployeeService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EmployeeService employeeService;
    
    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = mapEmployeeDTOToEmployee(employeeDTO);
        Employee savedEmployee = employeeService.saveEmployee(employee);
        return mapEmployeeToEmployeeDTO(savedEmployee);
    }
    
    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        EmployeeDTO employeeDTO = mapEmployeeToEmployeeDTO(employee);
        return employeeDTO;
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.setEmployeeAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<Employee> employees = employeeService.getEmployeesForService(employeeDTO.getDate(), employeeDTO.getSkills());
        List<EmployeeDTO> employeeDTOs = mapEmployeesToDTOs(employees);
        return employeeDTOs;
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        List<CustomerDTO> customerDTOs = mapCustomersToDTOs(customers);
        return customerDTOs;
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer customer = mapPetDTOToPet(customerDTO);
        List<Long> petIds = customerDTO.getPetIds();
        Customer selectedCustomer = customerService.getSelectedCustomer(customer, petIds);

        Customer savedCustomer = customerService.saveCustomer(selectedCustomer);
        return mapCustomerToCustomerDTO(savedCustomer);
    }
    
    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId) {
        Customer customer = customerService.getCustomerByPetId(petId);
        CustomerDTO customerDTO = mapCustomerToCustomerDTO(customer);
        return customerDTO;
    }

    // map DTO to entities
    private Customer mapPetDTOToPet(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }

    // map DTO to entities
    private Employee mapEmployeeDTOToEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        return employee;
    }
    
    // map entities to DTO
    private EmployeeDTO mapEmployeeToEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        return employeeDTO;
    }

    // map List to DTO list
    private List<EmployeeDTO> mapEmployeesToDTOs(List<Employee> employees) {
        List<EmployeeDTO> employeeDTOs = new ArrayList<>();
        employees.forEach(employee -> employeeDTOs.add(mapEmployeeToEmployeeDTO(employee)));
        return employeeDTOs;
    }

    // map entities to DTO
    private CustomerDTO mapCustomerToCustomerDTO(Customer customer) {
        List<Long> petIds = new ArrayList<>();
        customer.getPets().forEach(pet -> petIds.add(pet.getId()));

        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        customerDTO.setPetIds(petIds);
        return customerDTO;
    }
    
    // map List to DTO list
    private List<CustomerDTO> mapCustomersToDTOs(List<Customer> customers) {
        List<CustomerDTO> customerDTOs = new ArrayList<>();
        customers.forEach(customer -> customerDTOs.add(mapCustomerToCustomerDTO(customer)));
        return customerDTOs;
    }

}
