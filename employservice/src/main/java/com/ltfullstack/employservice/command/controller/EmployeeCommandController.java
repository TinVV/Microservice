package com.ltfullstack.employservice.command.controller;

import com.ltfullstack.employservice.command.command.CreateEmployeeCommand;
import com.ltfullstack.employservice.command.command.DeleteEmployeeCommand;
import com.ltfullstack.employservice.command.command.UpdateEmployeeCommand;
import com.ltfullstack.employservice.command.data.EmployeeRepository;
import com.ltfullstack.employservice.command.model.CreateEmployeeModel;
import com.ltfullstack.employservice.command.model.UpdateEmployeeModel;
import jakarta.validation.Valid;
import jakarta.ws.rs.NotFoundException;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeCommandController {

    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping
    public String addEmployee(@Valid @RequestBody CreateEmployeeModel model){
        CreateEmployeeCommand command = new CreateEmployeeCommand(UUID.randomUUID().toString(), model.getFirstName(), model.getLastName(), model.getKin(), false);

        return commandGateway.sendAndWait(command);
    }

    @PutMapping("/{employeeId}")
    public String updateEmployee(@Valid @RequestBody UpdateEmployeeModel model, @PathVariable String employeeId){

        System.out.println("Employee ID is: " + employeeRepository.existsById(employeeId));

        if(!employeeRepository.existsById(employeeId)){
            throw new NotFoundException("Employee not found with ID: " + employeeId);
        }

        UpdateEmployeeCommand command = new UpdateEmployeeCommand(employeeId, model.getFirstName(), model.getLastName(), model.getKin(), model.getDisciplined());

        return  commandGateway.sendAndWait(command);
    }

    @DeleteMapping("/{employeeId}")
    public String deleteEmployee(@Valid @PathVariable String employeeId){

        DeleteEmployeeCommand command = new DeleteEmployeeCommand(employeeId);

        return  commandGateway.sendAndWait(command);
    }

}
