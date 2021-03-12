package com.nnk.springboot.controllers;

import com.nnk.springboot.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("app")
public class LoginController {

  @Autowired private UserRepository userRepository;

  private static Logger logger = LoggerFactory.getLogger(LoginController.class);

  @GetMapping("login")
  public ModelAndView login() {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("login");
    return mav;
  }

  @GetMapping("secure/article-details")
  public ModelAndView getAllUserArticles() {
    logger.info("http://localhost:8080/secure/article-details");
    ModelAndView mav = new ModelAndView();
    mav.addObject("users", userRepository.findAll());
    mav.setViewName("user/list");
    return mav;
  }

  @GetMapping("error")
  public ModelAndView error() {
    ModelAndView mav = new ModelAndView();
    String errorMessage = "You are not authorized for the requested data.";
    mav.addObject("errorMsg", errorMessage);
    mav.setViewName("403");
    return mav;
  }
}
