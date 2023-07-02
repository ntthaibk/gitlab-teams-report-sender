package com.mdaq.testing.reportportal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Executions {
    private int total;
    private int failed;
    private int passed;
    private int skipped;
}
