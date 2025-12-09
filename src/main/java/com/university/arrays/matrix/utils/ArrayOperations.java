package com.university.arrays.matrix.utils;

import com.university.arrays.enums.SortOrder;
import com.university.arrays.utils.IArrayOperations;

import java.util.ArrayList;

public class ArrayOperations implements IArrayOperations<Double> {

    @Override
    public Double max(ArrayList<Double> arrayList) {
        double max = arrayList.get(0);

        for (int i = 1; i < arrayList.size(); i++) {
            if (arrayList.get(i) > max) max = arrayList.get(i);
        }

        return max;
    }

    @Override
    public ArrayList<Double> sort(ArrayList<Double> arrayList, SortOrder order) {
        return null;
    }
}
