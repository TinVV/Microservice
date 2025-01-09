package com.ltfullstack.employservice.query.projection;

import com.ltfullstack.employservice.command.data.Employee;
import com.ltfullstack.employservice.command.data.EmployeeRepository;
import com.ltfullstack.employservice.query.model.EmployeeResponseModel;
import com.ltfullstack.employservice.query.queries.GetAllEmployeeQuery;
import com.ltfullstack.employservice.query.queries.GetDetailEmployeeQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeProjection {

    @Autowired
    private EmployeeRepository employeeRepository;

    @QueryHandler
    public List<EmployeeResponseModel> handle(GetAllEmployeeQuery query){
        List<Employee> listEmployee = employeeRepository.findAllByIsDisciplined(query.getDisciplined());
        return listEmployee.stream().map(employee -> {
            EmployeeResponseModel model = new EmployeeResponseModel();
            BeanUtils.copyProperties(employee, model);

            return model;
        }).toList();
    }

    @QueryHandler
    public EmployeeResponseModel handle(GetDetailEmployeeQuery query) throws Exception{
        Employee employee = employeeRepository.findById(query.getId()).orElseThrow(() -> new Exception("Employee not found"));
        EmployeeResponseModel model = new EmployeeResponseModel();
        BeanUtils.copyProperties(query, model);
        return model;
    }
}
