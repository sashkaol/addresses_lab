package com.university.arrays.addresses.service;

import com.university.arrays.addresses.dto.Address;
import com.university.arrays.enums.SortOrder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SortService {

    private void quickSort(ArrayList<Address> addresses, int low, int high, SortOrder order) {
        if (low < high) {
            int pi = partition(addresses, low, high, order);
            quickSort(addresses, low, pi - 1, order);
            quickSort(addresses, pi + 1, high, order);
        }
    }

    private int partition(ArrayList<Address> addresses, int low, int high, SortOrder order) {
        Address pivot = addresses.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            boolean shouldSwap = order == SortOrder.ASC
                    ? addresses.get(j).getYear() <= pivot.getYear()
                    : addresses.get(j).getYear() >= pivot.getYear();

            if (shouldSwap) {
                i++;
                swap(addresses, i, j);
            }
        }
        swap(addresses, i + 1, high);
        return i + 1;
    }

    private void swap(ArrayList<Address> addresses, int i, int j) {
        Address temp = addresses.get(i);
        addresses.set(i, addresses.get(j));
        addresses.set(j, temp);
    }

    public ArrayList<Address> sort(ArrayList<Address> addresses, SortOrder order) {
        quickSort(addresses, 0, addresses.size() - 1, order);
        return addresses;
    }

}
