package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
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
public class RuleNameController {
  @Autowired RuleNameRepository ruleNameRepository;

  /**
   * Method display a view containing a list of all ruleNames
   *
   * @param model
   * @return ruleName/list view
   */
  @RequestMapping("/ruleName/list")
  public String home(Model model) {
    model.addAttribute("ruleNames", ruleNameRepository.findAll());
    return "ruleName/list";
  }

  /**
   * @param bid
   * @return view to add ruleName's in db
   */
  @GetMapping("/ruleName/add")
  public String addRuleForm(RuleName bid) {
    return "ruleName/add";
  }

  /**
   * Method add ruleName in database if User creation respect constraints defined in the class, else
   * return the view to add a new ruleName with an error message
   *
   * @param ruleName
   * @param result
   * @param model
   * @return view /ruleName/list if success, else view ruleName/add
   */
  @PostMapping("/ruleName/validate")
  public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
    if (!result.hasErrors()) {
      ruleNameRepository.save(ruleName);
      model.addAttribute("ruleNames", ruleNameRepository.findAll());
      return "redirect:/ruleName/list";
    }
    return "ruleName/add";
  }

  @GetMapping("/ruleName/update/{id}")
  public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
    RuleName rule =
        ruleNameRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid rule:" + id));
    model.addAttribute("ruleName", rule);
    return "ruleName/update";
  }

  /**
   * Method modify ruleName informations in database and return the view of the list of ruleNames
   * with the modifications if success, else return the update page with an error message
   *
   * @param id
   * @param ruleName
   * @param result
   * @param model
   * @return ruleName/update view if fails, else /ruleName/list view
   */
  @PostMapping("/ruleName/update/{id}")
  public String updateRuleName(
      @PathVariable("id") Integer id, @Valid RuleName ruleName, BindingResult result, Model model) {
    if (result.hasErrors()) {
      return "/ruleName/update";
    }

    ruleName.setId(id);
    ruleNameRepository.save(ruleName);
    model.addAttribute("ruleNames", ruleNameRepository.findAll());
    return "redirect:/ruleName/list";
  }

  /**
   * Method delete ruleName defined by it's id
   *
   * @param id
   * @param model
   * @return /ruleName/list view updated
   */
  @GetMapping("/ruleName/delete/{id}")
  public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
    RuleName rule =
        ruleNameRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid rule:" + id));
    ruleNameRepository.delete(rule);
    model.addAttribute("ruleNames", ruleNameRepository.findAll());
    return "redirect:/ruleName/list";
  }
}
