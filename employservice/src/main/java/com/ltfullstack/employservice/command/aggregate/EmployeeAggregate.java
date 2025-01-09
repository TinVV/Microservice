package com.ltfullstack.employservice.command.aggregate;

import com.ltfullstack.employservice.command.command.CreateEmployeeCommand;
import com.ltfullstack.employservice.command.command.DeleteEmployeeCommand;
import com.ltfullstack.employservice.command.command.UpdateEmployeeCommand;
import com.ltfullstack.employservice.command.event.EmployeeCreatedEvent;
import com.ltfullstack.employservice.command.event.EmployeeDeletedEvent;
import com.ltfullstack.employservice.command.event.EmployeeUpdatedEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@NoArgsConstructor
@Aggregate
public class EmployeeAggregate {
    @AggregateIdentifier
    private String id;
    private String firstName;
    private String lastName;
    private String kin;
    private Boolean isDisciplined;

    @CommandHandler
    public EmployeeAggregate(CreateEmployeeCommand command) {
        EmployeeCreatedEvent event = new EmployeeCreatedEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(EmployeeCreatedEvent event){
        this.id = event.getId();
        this.firstName = event.getFirstName();
        this.lastName = event.getLastName();
        this.kin = event.getKin();
        this.isDisciplined = event.getDisciplined();
    }

    @CommandHandler
    public EmployeeAggregate(UpdateEmployeeCommand command){
        EmployeeUpdatedEvent event = new EmployeeUpdatedEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(EmployeeUpdatedEvent event){
        this.id = event.getId();
        this.firstName = event.getFirstName();;
        this.lastName = event.getLastName();
        this.kin = event.getKin();
        this.isDisciplined = event.getDisciplined();
    }


    @CommandHandler
    public EmployeeAggregate(DeleteEmployeeCommand command){
        EmployeeDeletedEvent event = new EmployeeDeletedEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(EmployeeDeletedEvent event){
        this.id = event.getId();
    }
}
