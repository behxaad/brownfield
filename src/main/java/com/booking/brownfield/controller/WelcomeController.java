package com.booking.brownfield.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class WelcomeController {

	@RequestMapping("/welcome")
	public ModelAndView welcome() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("welcome");
		return mv;
	}

}
