package com.tin.employeeservice.command.event;

import com.tin.employeeservice.command.data.Employee;
import com.tin.employeeservice.command.data.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.util.Optional;

@Component
@Slf4j
public class EmployeeEventHandler {
    @Autowired
    private EmployeeRepository employeeRepository;

    @EventHandler
    public void on(EmployeeCreatedEvent event){
        Employee employee = new Employee();
        BeanUtils.copyProperties(event, employee);
        employeeRepository.save(employee);
    }

    @EventHandler
    public void on(EmployeeUpdatedEvent event){
        Optional<Employee> oldEmployee = employeeRepository.findById(event.getId());
        oldEmployee.ifPresent(employee -> {
            employee.setFirstName(event.getFirstName());
            employee.setLastName(event.getLastName());
            employee.setKin(event.getKin());
            employee.setIsDisciplined(event.getIsDisciplined());
            employeeRepository.save(employee);
        });
    }

    @EventHandler
    public void on(EmployeeDeletedEvent event){
        try {
        employeeRepository.findById(event.getId()).orElseThrow(() -> new Exception("Employee not found"));

        employeeRepository.deleteById(event.getId());
        } catch (Exception e){
            log.error(e.getMessage());
        }
    }
}
