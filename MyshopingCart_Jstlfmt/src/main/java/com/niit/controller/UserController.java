package com.niit.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.niit.dao.UserDao;
import com.niit.domain.User;

@Controller
public class UserController {
	@Autowired
	MessageSource messageSource = null;

	@Autowired
	User user;

	@Autowired
	UserDao userDao;

	// register
	@RequestMapping(value = "registration", method = RequestMethod.GET)
	public String userRegister(Model model) {

		model.addAttribute("user", user);
		return "register";

	}

	@RequestMapping(value = "registration", method = RequestMethod.POST)
	public String useRegister(@ModelAttribute("user") User user, Model model) {

		userDao.save(user);
		model.addAttribute("registrationsuccess", "Congratulations Registered sucessfully!");
		return "login";

	}

	// Login
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String userLogin(Model model) {

		model.addAttribute("user", user);
		return "login";

	}

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String userLogin(@ModelAttribute("user") User user, Model model) {

		boolean flag = userDao.validate(user);
		if (flag) {
			model.addAttribute("uname", user.getName());
			return "home";
		} else {

			String errmsg = messageSource.getMessage("credentials.notfound", null, Locale.getDefault());
			model.addAttribute("error", errmsg);
			return "login";
		}

	}

}
