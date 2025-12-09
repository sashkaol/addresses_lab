package com.university.arrays.matrix.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public class MatrixRq {
    @NotNull
    @Size(min = 1, max = 10)
    private List<List<Double>> matrix;

    // getters and setters
    public List<List<Double>> getMatrix() { return matrix; }
    public void setMatrix(List<List<Double>> matrix) { this.matrix = matrix; }
}
