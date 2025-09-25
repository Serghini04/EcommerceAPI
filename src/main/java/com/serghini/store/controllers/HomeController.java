package com.serghini.store.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

@Controller
@Tag(name="Home", description="Operations related to the home page")
public class HomeController {
	@RequestMapping("/")
	@Operation(summary="Displays the home page.")
	public String index(Model model) {
		model.addAttribute("name", "Serghini");
		return "index";
	}
}
