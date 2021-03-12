package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
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
public class CurveController {
  @Autowired CurvePointRepository curvePointRepository;

  private static Logger logger = LoggerFactory.getLogger(CurveController.class);

  /**
   * Method display a view containing a list of all curvePoints
   *
   * @param model
   * @return curvePoint/list view
   */
  @RequestMapping("/curvePoint/list")
  public String home(Model model) {
    logger.info("http://localhost:8080/curvePoint/list");
    model.addAttribute("curvePoints", curvePointRepository.findAll());
    return "curvePoint/list";
  }

  /**
   * @param bid
   * @return view to add curvePoint's in db
   */
  @GetMapping("/curvePoint/add")
  public String addCurveForm(CurvePoint bid) {
    logger.info("http://localhost:8080/curvePoint/add");
    return "curvePoint/add";
  }

  /**
   * Method add curvePoint in database if User creation respect constraints defined in the class,
   * else return the view to add a new curvePoint with an error message
   *
   * @param curvePoint
   * @param result
   * @param model
   * @return view /curvePoint/list if success, else view curvePoint/add
   */
  @PostMapping("/curvePoint/validate")
  public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
    logger.info("http://localhost:8080/curvePoint/validate");
    if (!result.hasErrors()) {
      curvePointRepository.save(curvePoint);
      model.addAttribute("curvePoints", curvePointRepository.findAll());
      return "redirect:/curvePoint/list";
    }
    return "curvePoint/add";
  }

  @GetMapping("/curvePoint/update/{id}")
  public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
    logger.info("http://localhost:8080/curvePoint/update/" + id);
    CurvePoint curvePoint =
        curvePointRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid curve point id:" + id));
    model.addAttribute("curvePoint", curvePoint);
    return "curvePoint/update";
  }

  /**
   * Method modify curvePoint informations in database and return the view of the list of
   * curvePoints with the modifications if success, else return the update page with an error
   * message
   *
   * @param id
   * @param curvePoint
   * @param result
   * @param model
   * @return curvePoint/update view if fails, else /curvePoint/list view
   */
  @PostMapping("/curvePoint/update/{id}")
  public String updateCurve(
      @PathVariable("id") Integer id,
      @Valid CurvePoint curvePoint,
      BindingResult result,
      Model model) {
    logger.info("http://localhost:8080/curvePoint/update/" + id);
    if (result.hasErrors()) {
      return "curvePoints/update";
    }

    curvePoint.setId(id);
    curvePointRepository.save(curvePoint);
    model.addAttribute("curvePoints", curvePointRepository.findAll());
    return "redirect:/curvePoint/list";
  }

  /**
   * Method delete curvePoint defined by it's id
   *
   * @param id
   * @param model
   * @return /curvePoint/list view updated
   */
  @GetMapping("/curvePoint/delete/{id}")
  public String deleteCurve(@PathVariable("id") Integer id, Model model) {
    logger.info("http://localhost:8080/curvePoint/delete/" + id);
    CurvePoint curvePoint =
        curvePointRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid curve point id:" + id));
    curvePointRepository.delete(curvePoint);
    model.addAttribute("curvePoints", curvePointRepository.findAll());
    return "redirect:/curvePoint/list";
  }
}
