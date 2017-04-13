package pl.lucasjasek.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import pl.lucasjasek.dto.OrderDTO;

@Controller
@SessionAttributes("orderDTO")
public class TicketController {

    @GetMapping("/bilet")
    public String printTicket(@ModelAttribute("orderDTO") OrderDTO orderDTO, Model model) {

        model.addAttribute("orderDTO", orderDTO);

        return "ticket";
    }
}
