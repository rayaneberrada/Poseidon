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

  /**
   * Method display a view containing a list of all ratings
   *
   * @param model
   * @return rating/list view
   */
  @RequestMapping("/rating/list")
  public String home(Model model) {
    logger.info("http://localhost:8080/rating/list");
    model.addAttribute("ratings", ratingRepository.findAll());
    return "rating/list";
  }

  /**
   * @param rating
   * @return view to add rating's in db
   */
  @GetMapping("/rating/add")
  public String addRatingForm(Rating rating) {
    logger.info("http://localhost:8080/rating/add");
    return "rating/add";
  }

  /**
   * Method add rating in database if User creation respect constraints defined in the class, else
   * return the view to add a new rating with an error message
   *
   * @param rating
   * @param result
   * @param model
   * @return view /rating/list if success, else view rating/add
   */
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

  /**
   * Method modify rating informations in database and return the view of the list of ratings with
   * the modifications if success, else return the update page with an error message
   *
   * @param id
   * @param rating
   * @param result
   * @param model
   * @return rating/update view if fails, else /rating/list view
   */
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

  /**
   * Method delete rating defined by it's id
   *
   * @param id
   * @param model
   * @return /rating/list view updated
   */
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
