package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = mapScheduleDTOToSchedule(scheduleDTO);
        Schedule createdSchedule = scheduleService.saveSchedule(schedule, scheduleDTO.getEmployeeIds(), scheduleDTO.getPetIds());
        return mapScheduleToScheduleDTO(createdSchedule);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleService.getAllSchedules();
        List<ScheduleDTO> scheduleDTOs = mapSchedulesToDTOs(schedules);
        return scheduleDTOs;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> schedules = scheduleService.getAllSchedulesForPet(petId);
        List<ScheduleDTO> scheduleDTOs = mapSchedulesToDTOs(schedules);
        return scheduleDTOs;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> schedules = scheduleService.getAllSchedulesForEmployee(employeeId);
        List<ScheduleDTO> scheduleDTOs = mapSchedulesToDTOs(schedules);
        return scheduleDTOs;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> schedules = scheduleService.getAllSchedulesForCustomer(customerId);
        List<ScheduleDTO> scheduleDTOs = mapSchedulesToDTOs(schedules);
        return scheduleDTOs;
    }
    
    // map list to DTO list
    private List<ScheduleDTO> mapSchedulesToDTOs(List<Schedule> schedules) {
        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
        schedules.forEach(schedule -> scheduleDTOs.add(mapScheduleToScheduleDTO(schedule)));
        return scheduleDTOs;
    }

    // map entity to DTO
    private ScheduleDTO mapScheduleToScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();

        List<Long> employeeIds = new ArrayList<>();
        schedule.getEmployees().forEach(employee -> employeeIds.add(employee.getId()));
        List<Long> petIds = new ArrayList<>();
        schedule.getPets().forEach(pet -> petIds.add(pet.getId()));

        scheduleDTO.setId(schedule.getId());
        scheduleDTO.setDate(schedule.getDate());
        scheduleDTO.setActivities(schedule.getActivities());
        scheduleDTO.setEmployeeIds(employeeIds);
        scheduleDTO.setPetIds(petIds);

        return scheduleDTO;
    }

    // map DTO to entity
    private Schedule mapScheduleDTOToSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        schedule.setDate(scheduleDTO.getDate());
        schedule.setActivities(scheduleDTO.getActivities());
        return schedule;
    }

}
