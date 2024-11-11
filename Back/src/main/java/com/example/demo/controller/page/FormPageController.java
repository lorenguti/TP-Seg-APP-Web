package com.example.demo.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class FormPageController {
    @GetMapping("/form-fx")
    public String formPage(@RequestParam(name = "contentFrame", required = false) String url, Model model) {
        model.addAttribute("iframeUrl", url != null ? url : "about:blank");
        return "form";
    }


    @GetMapping("/formV2024")
    public String formV2024Page(@RequestParam(name = "contentFrame", required = false) String url, Model model) {
        model.addAttribute("iframeUrl", url != null ? url : "about:blank");
        return "formV2024";
    }

}
