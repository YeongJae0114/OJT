package org.example.transcation.week4.exapmle.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrimaryTable {
    private Long id;
    private String keyValue;
    private String firstValue;
}
