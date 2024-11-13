package com.example.demo.controller.page;

import com.example.demo.utils.JwtUtilGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class FormPageController {


    @Autowired
    private JwtUtilGen jwtUtilGen;

    @GetMapping("/form-fx")
    public String formPage(@RequestParam(name = "contentFrame", required = false) String url, Model model) {
        model.addAttribute("iframeUrl", url != null ? url : "about:blank");
        return "form";
    }


    @GetMapping("/formV2024")
    public String formV2024Page(@RequestParam(name = "ce") String token, Model model) {
        boolean isValid = jwtUtilGen.validateTokenGen(token);
        model.addAttribute("isValid", isValid);
        if (isValid) {
            String email = jwtUtilGen.getEmailFromToken(token);
            String idContract = jwtUtilGen.getIdContractFromToken(token);
            model.addAttribute("email", email);
            model.addAttribute("idCont", idContract);
            return "formV2024";
        } else {
            return "formError";
        }
    }

}
