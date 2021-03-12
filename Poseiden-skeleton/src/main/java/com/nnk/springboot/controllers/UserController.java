package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/** Class managing user's */
@Controller
public class UserController {
  @Autowired private UserRepository userRepository;

  private static Logger logger = LoggerFactory.getLogger(UserController.class);

  /**
   * Method display a view containing a list of all users
   *
   * @param model
   * @return user/list view
   */
  @RequestMapping("/user/list")
  public String home(Model model) {
    logger.info("http://localhost:8080/user/list");
    model.addAttribute("users", userRepository.findAll());
    return "user/list";
  }

  /**
   * @param bid
   * @return view to add user's in db
   */
  @GetMapping("/user/add")
  public String addUser(User bid) {
    logger.info("http://localhost:8080/user/add");
    return "user/add";
  }

  /**
   * Method add user in database if User creation respect constraints defined in the class, else
   * return the view to add a new user with an error message
   *
   * @param user
   * @param result
   * @param model
   * @return view /user/list if success, else view user/add
   */
  @PostMapping("/user/validate")
  public String validate(@Valid User user, BindingResult result, Model model) {
    logger.info("http://localhost:8080/user/validate");
    if (!result.hasErrors()) {
      BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
      user.setPassword(encoder.encode(user.getPassword()));
      userRepository.save(user);
      model.addAttribute("users", userRepository.findAll());
      return "redirect:/user/list";
    }
    return "user/add";
  }

  @GetMapping("/user/update/{id}")
  public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
    logger.info("http://localhost:8080/user/update/" + id);
    User user =
        userRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
    user.setPassword("");
    model.addAttribute("user", user);
    return "user/update";
  }

  /**
   * Method modify user informations in database and return the view of the list of users with the
   * modifications if success, else return the update page with an error message
   *
   * @param id
   * @param user
   * @param result
   * @param model
   * @return user/update view if fails, else /user/list view
   */
  @PostMapping("/user/update/{id}")
  public String updateUser(
      @PathVariable("id") Integer id, @Valid User user, BindingResult result, Model model) {
    logger.info("http://localhost:8080/user/update/" + id);
    if (result.hasErrors()) {
      return "user/update";
    }

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    user.setPassword(encoder.encode(user.getPassword()));
    user.setId(id);
    userRepository.save(user);
    model.addAttribute("users", userRepository.findAll());
    return "redirect:/user/list";
  }

  /**
   * Method delete user defined by it's id
   *
   * @param id
   * @param model
   * @return /user/list view updated
   */
  @GetMapping("/user/delete/{id}")
  public String deleteUser(@PathVariable("id") Integer id, Model model) {
    logger.info("http://localhost:8080/user/delete/" + id);
    User user =
        userRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
    userRepository.delete(user);
    model.addAttribute("users", userRepository.findAll());
    return "redirect:/user/list";
  }
}
