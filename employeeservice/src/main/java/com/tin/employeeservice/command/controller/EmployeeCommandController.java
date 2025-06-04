package com.tin.employeeservice.command.controller;

import com.tin.employeeservice.command.command.CreateEmployeeCommand;
import com.tin.employeeservice.command.command.DeleteEmployeeCommand;
import com.tin.employeeservice.command.command.UpdateEmployeeCommand;
import com.tin.employeeservice.command.data.EmployeeRepository;
import com.tin.employeeservice.command.model.CreateEmployeeModel;
import com.tin.employeeservice.command.model.UpdateEmployeeModel;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/employees")
@Tag(name = "Employee Command")
public class EmployeeCommandController {

    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Operation(
            summary = "Create Employee",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Create successful"
                    )
            }
    )
    @PostMapping
    public String createEmployee(@Valid @RequestBody CreateEmployeeModel model){
        CreateEmployeeCommand command = new CreateEmployeeCommand(
                UUID.randomUUID().toString(), model.getFirstName(), model.getLastName(), model.getKin(), false
        );

        return commandGateway.sendAndWait(command);
    }

    @Operation(
            summary = "Update Employee",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Update successful"
                    )
            }
    )
    @PutMapping("/{employeeId}")
    public String updateEmployee(@Valid @RequestBody UpdateEmployeeModel model, @PathVariable String employeeId){
        UpdateEmployeeCommand command = new UpdateEmployeeCommand(
                employeeId, model.getFirstName(), model.getLastName(), model.getKin(), model.getIsDisciplined()
        );

        return commandGateway.sendAndWait(command);
    }

    @Operation(
            summary = "Delete Employee",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Delete successful"
                    )
            }
    )
    @Hidden
    @DeleteMapping("/{employeeId}")
    public String deleteEmployee(@Valid @PathVariable String employeeId){
        DeleteEmployeeCommand command = new DeleteEmployeeCommand(employeeId);

        return commandGateway.sendAndWait(command);
    }
}
