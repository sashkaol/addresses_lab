package com.university.arrays.utils;

import com.university.arrays.enums.SortOrder;

import java.util.ArrayList;

public interface IArrayOperations<T> {
    T max(ArrayList<T> arrayList);

    ArrayList<T> sort(ArrayList<T> arrayList, SortOrder order);
}
