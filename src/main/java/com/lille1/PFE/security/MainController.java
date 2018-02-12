package com.lille1.PFE.security;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class MainController {

	@ModelAttribute
	public void addAttributes(Model model, Principal principal) {
		if (principal != null)
			System.out.println(principal.getName());
	}
}
