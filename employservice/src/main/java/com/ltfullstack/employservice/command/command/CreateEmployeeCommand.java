package com.ltfullstack.employservice.command.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEmployeeCommand {
    @TargetAggregateIdentifier
    private String id;
    private String firstName;
    private String lastName;
    private String kin;
    private Boolean isDisciplined;

//    public CreateEmployeeCommand() {
//    }
//
//    public CreateEmployeeCommand(String id, String firstName, String lastName, String kin, Boolean isDisciplined) {
//        this.id = id;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.kin = kin;
//        this.isDisciplined = isDisciplined;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public String getKin() {
//        return kin;
//    }
//
//    public void setKin(String kin) {
//        this.kin = kin;
//    }
//
//    public Boolean getDisciplined() {
//        return isDisciplined;
//    }
//
//    public void setDisciplined(Boolean disciplined) {
//        isDisciplined = disciplined;
//    }
}
