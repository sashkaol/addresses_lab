package com.university.arrays.matrix.dto;

import java.util.List;

public class MatrixRs {
    private Double scalarResult;

    public MatrixRs(Double scalarResult) {
        this.scalarResult = scalarResult;
    }

    public MatrixRs(int scalarResult) {
        this.scalarResult = (double) scalarResult;
    }

    public Double getScalarResult() { return scalarResult; }
    public void setScalarResult(Double scalarResult) { this.scalarResult = scalarResult; }
}
