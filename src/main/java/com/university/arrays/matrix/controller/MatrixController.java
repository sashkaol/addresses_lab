package com.university.arrays.matrix.controller;

import com.university.arrays.addresses.dto.Address;
import com.university.arrays.matrix.dto.MatrixRq;
import com.university.arrays.matrix.dto.MatrixRs;
import com.university.arrays.matrix.service.MatrixService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/matrix")
@CrossOrigin(origins = "http://localhost:8090")
@Slf4j
public class MatrixController {

    @Autowired
    private MatrixService matrixService;

    // получить количество строк, не содержащих ни одного нулевого элемента
    @PostMapping("/get-non-zero-lines")
    public ResponseEntity<MatrixRs> nonZero(@Valid @RequestBody MatrixRq request) {
        int count = matrixService.getNonZeroLineCount(request.getMatrix());
        return ResponseEntity.ok(new MatrixRs(count));
    }

    // получить максимальное из чисел, встречающихся в заданной матрице более одного раза
    @PostMapping("/get-max-number")
    public ResponseEntity<MatrixRs> max(@Valid @RequestBody MatrixRq request) {
        double result = matrixService.getMaxNumber(request.getMatrix());
        return ResponseEntity.ok(new MatrixRs(result));
    }

}
