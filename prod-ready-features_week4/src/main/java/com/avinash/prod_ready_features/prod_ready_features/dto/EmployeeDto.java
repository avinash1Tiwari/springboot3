package com.avinash.prod_ready_features.prod_ready_features.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class EmployeeDto {



    private Long empId;

    private String name;

    private String email;

    private LocalDate dateOfJoining;

    private String role;

    private Integer salary;

    private Integer age;

    private Integer probationPeriod;


    @JsonProperty("isActive")
    private boolean isActive;



    @Override
    public String toString() {
        return "Employeedto{" +
                "id=" + empId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", dateOfJoining=" + dateOfJoining +
                '}';
    }
}







