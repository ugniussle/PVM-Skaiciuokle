package ku.pvmskaiciuokle.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class PvmController {
    private static final Double PVMRate = 0.21;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "PVM Skaičiuoklė");

        return "calculator";
    }

    @PostMapping("/")
    public String calculatePVM(
            @RequestParam("price") Double singlePriceWithPVM,
            @RequestParam("itemCount") Integer itemCount,
            Model model
    ) {
        model.addAttribute("title", "PVM Rezultatai");

        Map<String, Double> results = new HashMap<>();

        results.put("singlePriceWithPVM",   round(singlePriceWithPVM, 2));
        results.put("singlePriceNoPVM",     round(singlePriceWithPVM * (1 - PVMRate), 2));
        results.put("PVMofItem",            round(singlePriceWithPVM * PVMRate, 2));
        results.put("totalPriceNoPVM",      round(results.get("singlePriceNoPVM") * itemCount, 2));
        results.put("totalPVM",             round(results.get("PVMofItem") * itemCount, 2));
        results.put("totalPriceWithPVM", results.get("totalPriceNoPVM") + results.get("totalPVM"));

        model.addAttribute("itemCount", itemCount);
        model.addAllAttributes(results);

        return "PVMResult";
    }

    private Double round(Double number, Integer decimalPlaces) {
        Double n = 10.0;

        while(decimalPlaces > 1) {
            n *= 10;
            decimalPlaces--;
        }

        return Math.round(number*n)/n;
    }
}
