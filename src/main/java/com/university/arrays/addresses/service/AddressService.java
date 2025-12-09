package com.university.arrays.addresses.service;

import com.university.arrays.addresses.dto.Address;
import com.university.arrays.addresses.exception.InvalidAddressFormatException;
import com.university.arrays.addresses.utils.ArrayOperations;
import com.university.arrays.enums.SortOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class AddressService {

    ArrayOperations arrayOperations = new ArrayOperations();

    public List<Address> parseAddressesFromFile(MultipartFile file) throws IOException {
        List<Address> addresses = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 4) {
                    addresses.add(new Address(
                            parts[0].trim(),  // микрорайон
                            parts[1].trim(),  // улица
                            parts[2].trim(),  // номер дома
                            Integer.parseInt(parts[3].trim()) // год
                    ));
                } else {
                    String error = "Формат строки не соответствует необходимому ('микрорайон;улица;номер_дома;год_постройки'): " + line;
                    log.error(error);
                    throw new InvalidAddressFormatException(error);
                }
            }
        }
        return addresses;
    }

    public Map<String, ArrayList<Address>> getOldBuildings(ArrayList<Address> addresses) {

        int earliestYear = arrayOperations.max(addresses).getYear();

        return groupBuildingsByDistrict(addresses, earliestYear);
    }

    private Map<String, ArrayList<Address>> groupBuildingsByDistrict(ArrayList<Address> buildings, int year) {

        Map<String, ArrayList<Address>> result = new HashMap<>();

        for (int i = 0; i < buildings.size(); i++) {
            String key = buildings.get(i).getDistrict();
            Address value = buildings.get(i);

            if (result.containsKey(key)) {
                if (value.getYear() == year) {
                    result.get(key).add(value);
                    log.info("В ключ {} добавлено здание с годом {}", key, value.getYear());
                }
            } else {
                log.info("Добавлен ключ {}", key);

                ArrayList<Address> values = new ArrayList<>();
                if (value.getYear() == year) {
                    values.add(value);
                }
                result.put(key, values);
            }
        }

        return result;
    }

    public ArrayList<Address> getSortedAddresses(ArrayList<Address> addresses, SortOrder order) {
        log.info("Начата ортировка адресов по году, порядок: {}", order.name());
        return arrayOperations.sort(addresses, order);
    }

    public ArrayList<Address> addBuilding(ArrayList<Address> addresses, Address newAddress) {
        addresses.add(new Address(
                newAddress.getDistrict().trim(),
                newAddress.getStreet().trim(),
                newAddress.getHouseNumber().trim(),
                newAddress.getYear()
        ));
        return addresses;
    }
}
