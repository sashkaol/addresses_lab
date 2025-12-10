package com.university.arrays.addresses.service;

import com.university.arrays.addresses.dto.Address;
import com.university.arrays.addresses.exception.InvalidAddressFormatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class AddressService {

    @Autowired
    ValidateService validateService;

    public ArrayList<Address> parseAddressesFromFile(MultipartFile file) throws IOException {
        ArrayList<Address> addresses = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                i++;
                String[] parts = line.split(";");
                if (parts.length >= 4) {
                    Address address = new Address(
                            parts[0].trim(),  // микрорайон
                            parts[1].trim(),  // улица
                            parts[2].trim(),  // номер дома
                            Integer.parseInt(parts[3].trim()) // год
                    );
                    if (validateService.rowIsCorrect(address)) {
                        addresses.add(address);
                    } else {
                        String error = "Ошибка заполнения данных из строки " + i + ": " + line;
                        log.error(error);
                        throw new InvalidAddressFormatException(error);
                    }

                } else {
                    String error = "Формат строки не соответствует необходимому ('микрорайон;улица;номер_дома;год_постройки'): " + line;
                    log.error(error);
                    throw new InvalidAddressFormatException(error);
                }
            }
            if (i == 0) {
                throw new RuntimeException("Пустой файл");
            }
        }
        return addresses;
    }

    public Map<String, ArrayList<Address>> getGroupedAddressesByDistrict(ArrayList<Address> addresses) {

        int earliestYear = getEarliestBuilding(addresses).getYear();

        Map<String, ArrayList<Address>> result = new HashMap<>();

        for (int i = 0; i < addresses.size(); i++) {
            String key = addresses.get(i).getDistrict();
            Address value = addresses.get(i);

            if (result.containsKey(key)) {
                if (value.getYear() == earliestYear) {
                    result.get(key).add(value);
                    log.info("В ключ {} добавлено здание с годом {}", key, value.getYear());
                }
            } else {
                log.info("Добавлен ключ {}", key);

                ArrayList<Address> values = new ArrayList<>();
                if (value.getYear() == earliestYear) {
                    values.add(value);
                }
                result.put(key, values);
            }
        }

        return result;
    }

    public Address trimAddress(Address newAddress) {
        return new Address(
                newAddress.getDistrict().trim(),
                newAddress.getStreet().trim(),
                newAddress.getHouseNumber().trim(),
                newAddress.getYear()
        );
    }

    private Address getEarliestBuilding(ArrayList<Address> arrayList) {

        Address max = arrayList.get(0);

        for (int i = 1; i < arrayList.size(); i++) {
            if (arrayList.get(i).getYear() < max.getYear()) max = arrayList.get(i);
        }

        return max;
    }
}
