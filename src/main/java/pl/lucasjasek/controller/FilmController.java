package pl.lucasjasek.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import pl.lucasjasek.dto.OrderDTO;
import pl.lucasjasek.service.OrderDetailService;

import java.util.List;

@Controller
@SessionAttributes("orderDTO")
public class FilmController {

    private final OrderDetailService orderDetailService;

    @Autowired
    public FilmController(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    @GetMapping("/film")
    public String filmPage(@ModelAttribute("orderDTO") OrderDTO orderDTO, Model model) {

        List<String> schedule = orderDetailService.getSchedule(orderDTO.getFilm(), orderDTO.getDate());

        if (schedule.isEmpty()) {
            model.addAttribute("message", "Brak repertuaru dla wybranego filmu na dziś. Wybierz inny dzień z kalendarza.");
        } else {
            model.addAttribute("schedule", schedule);
        }

        model.addAttribute("orderDTO", orderDTO);

        return "film";
    }

    @PostMapping("/film")
    public String filmPagePost(@ModelAttribute("orderDTO") OrderDTO orderDTO, Model model) {

        model.addAttribute("orderDTO", orderDTO);

        return "redirect:/miejsce";
    }
}
