package com.tin.employeeservice.query.controller;

import com.tin.commonservice.model.EmployeeResponseCommonModel;
import com.tin.employeeservice.query.model.EmployeeResponseModel;
import com.tin.employeeservice.query.queries.GetAllEmployeeQuery;
import com.tin.commonservice.queries.GetDetailEmployeeQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@Tag(name = "Employee Query")
@Slf4j
public class EmployeeQueryController {

    @Autowired
    private QueryGateway queryGateway;

    @Operation(
            summary = "Get Detail for Employee",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Get detail successful"
                    )
            }
    )
    @GetMapping("/{employeeId}")
    public EmployeeResponseCommonModel getDetailEmployee(@PathVariable String employeeId){
        return queryGateway
                .query(new GetDetailEmployeeQuery(employeeId), ResponseTypes.instanceOf(EmployeeResponseCommonModel.class)).join();
    }

    @Operation(
            summary = "Get List Employee",
            description = "Get endpoint for employee with filter",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "401"
                    )
            }
    )
    @GetMapping
    public List<EmployeeResponseModel> getAllEmployee(@RequestParam(required = false, defaultValue = "false") Boolean isDisciplined){
        log.info("Calling to getAllEmployee");
        return queryGateway
                .query(new GetAllEmployeeQuery(isDisciplined), ResponseTypes.multipleInstancesOf(EmployeeResponseModel.class)).join();
    }
}
