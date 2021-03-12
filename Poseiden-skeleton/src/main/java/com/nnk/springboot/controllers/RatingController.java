package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class RatingController {
  @Autowired RatingRepository ratingRepository;

  private static Logger logger = LoggerFactory.getLogger(RatingController.class);

  @RequestMapping("/rating/list")
  public String home(Model model) {
    logger.info("http://localhost:8080/rating/list");
    model.addAttribute("ratings", ratingRepository.findAll());
    return "rating/list";
  }

  @GetMapping("/rating/add")
  public String addRatingForm(Rating rating) {
    logger.info("http://localhost:8080/rating/add");
    return "rating/add";
  }

  @PostMapping("/rating/validate")
  public String validate(@Valid Rating rating, BindingResult result, Model model) {
    logger.info("http://localhost:8080/rating/validate");
    if (!result.hasErrors()) {
      ratingRepository.save(rating);
      model.addAttribute("ratings", ratingRepository.findAll());
      return "redirect:/rating/list";
    }
    return "rating/add";
  }

  @GetMapping("/rating/update/{id}")
  public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
    logger.info("http://localhost:8080/rating/update/" + id);
    Rating rating =
        ratingRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid rating id:" + id));
    model.addAttribute("rating", rating);
    return "rating/update";
  }

  @PostMapping("/rating/update/{id}")
  public String updateRating(
      @PathVariable("id") Integer id, @Valid Rating rating, BindingResult result, Model model) {
    // TODO: check required fields, if valid call service to update Rating and return Rating list
    logger.info("http://localhost:8080/rating/update/" + id);
    if (result.hasErrors()) {
      return "rating/update";
    }

    rating.setId(id);
    ratingRepository.save(rating);
    model.addAttribute("ratings", ratingRepository.findAll());
    return "redirect:/rating/list";
  }

  @GetMapping("/rating/delete/{id}")
  public String deleteRating(@PathVariable("id") Integer id, Model model) {
    logger.info("http://localhost:8080/rating/delete/" + id);
    Rating rating =
        ratingRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid rating id:" + id));
    ratingRepository.delete(rating);
    model.addAttribute("ratings", ratingRepository.findAll());
    return "redirect:/rating/list";
  }
}
