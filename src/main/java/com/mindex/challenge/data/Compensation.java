package com.mindex.challenge.data;

import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Builder @ToString
public class Compensation {
    private String employee;
    private int salary;
    private String effectiveDate;
}
