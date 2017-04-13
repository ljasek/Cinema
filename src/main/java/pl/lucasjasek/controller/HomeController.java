package pl.lucasjasek.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import pl.lucasjasek.dto.OrderDTO;
import pl.lucasjasek.util.DateUtil;

@Controller
@SessionAttributes("orderDTO")
public class HomeController {

    @RequestMapping("/")
    public String mainPage() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String indexPage(Model model) {

        OrderDTO orderDTO = new OrderDTO();

        model.addAttribute("orderDTO", orderDTO);
        model.addAttribute("calendar", DateUtil.getCalendar());
        return "index";
    }

    @PostMapping("/index")
    public String indexPagePost(@ModelAttribute("orderDTO") OrderDTO orderDTO, Model model) {

        model.addAttribute("orderDTO", orderDTO);

        return "redirect:/film";
    }
}
