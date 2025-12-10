package com.university.arrays.addresses.controller;

import com.university.arrays.addresses.dto.Address;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;

@Controller
public class MainController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/matrix")
    public String matrix(Model model) {
        model.addAttribute("rows", 3);
        model.addAttribute("cols", 3);
        return "matrix";
    }

    @GetMapping("/addresses")
    public String addresses(Model model) {
        model.addAttribute("addressForm", new Address());
        return "addresses";
    }

}

