package org.example.transcation.week4.exapmle.dto;

import lombok.Data;

@Data
public class UpdateDtoRollBack {
    private Long primaryId;
    private String firstValue;

    private Long secondaryId;
    private String secondValue;
}
