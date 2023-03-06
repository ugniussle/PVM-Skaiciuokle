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

        Map<String, String> results = new HashMap<>();

        results.put("singlePriceWithPVM",   "" + round(singlePriceWithPVM) +                                "€");
        results.put("singlePriceNoPVM",     "" + round(singlePriceWithPVM * (1 - PVMRate)) +                "€");
        results.put("PVMofItem",            "" + round(singlePriceWithPVM * PVMRate) +                      "€");
        results.put("totalPriceNoPVM",      "" + round(singlePriceWithPVM * (1 - PVMRate) * itemCount) +    "€");
        results.put("totalPVM",             "" + round(singlePriceWithPVM * PVMRate * itemCount) +          "€");
        results.put("totalPriceWithPVM",    "" + round(singlePriceWithPVM * itemCount) +                    "€");

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

    private Double round(Double number) {
        return round(number, 2);
    }
}
