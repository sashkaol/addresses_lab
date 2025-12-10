package com.university.arrays.addresses.controller;

import com.university.arrays.addresses.dto.Address;
import com.university.arrays.addresses.service.AddressService;
import com.university.arrays.addresses.service.SortService;
import com.university.arrays.addresses.service.ValidateService;
import com.university.arrays.enums.SortOrder;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/api/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;
    @Autowired
    private SortService sortService;
    @Autowired
    private ValidateService validateService;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, HttpSession session,
                            RedirectAttributes redirectAttributes) {
        try {
            ArrayList<Address> addresses = addressService.parseAddressesFromFile(file);

            session.setAttribute("addresses", addresses);
            redirectAttributes.addFlashAttribute("success", "Файл успешно загружен!");
            redirectAttributes.addFlashAttribute("addresses", addresses);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка обработки файла: " + e.getMessage());
        }
        return "redirect:/addresses";
    }

    @PostMapping("/add")
    public String addAddress(@ModelAttribute Address newAddress,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
        ArrayList<Address> addresses = (ArrayList<Address>) session.getAttribute("addresses");
        if (addresses == null) {
            addresses = new ArrayList<>();
            session.setAttribute("addresses", addresses);
        }

        addresses.add(addressService.trimAddress(newAddress));

        redirectAttributes.addFlashAttribute("success", "Дом добавлен!");
        redirectAttributes.addFlashAttribute("addresses", addresses);
        return "redirect:/addresses";
    }


    @GetMapping("/old")
    public String getOldBuildings(HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            ArrayList<Address> addresses = (ArrayList<Address>) session.getAttribute("addresses");

            Map<String, ArrayList<Address>> groupedOldBuildings = addressService.getGroupedAddressesByDistrict(addresses);

            redirectAttributes.addFlashAttribute("success", "Старые здания выведены");
            redirectAttributes.addFlashAttribute("addresses", addresses);
            redirectAttributes.addFlashAttribute("groupedOldBuildings", groupedOldBuildings);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка обработки списка: " + e.getMessage());
        }
        return "redirect:/addresses";
    }

    @GetMapping("/sort")
    public String getSortedAddresses(HttpSession session,
                       @RequestParam("order") SortOrder order, RedirectAttributes redirectAttributes) {
        try {
            ArrayList<Address> addresses = (ArrayList<Address>) session.getAttribute("addresses");

            log.info("Начата ортировка адресов по году, порядок: {}", order.name());
            ArrayList<Address> sortedAddresses = sortService.sort(addresses, order);

            redirectAttributes.addFlashAttribute("success", "Сортировка по " + (order == SortOrder.ASC ? "возрастанию" : "убыванию") + " успешно произведена");
            redirectAttributes.addFlashAttribute("addresses", sortedAddresses);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка сортировки " + e.getMessage());
        }
        return "redirect:/addresses";
    }

}
