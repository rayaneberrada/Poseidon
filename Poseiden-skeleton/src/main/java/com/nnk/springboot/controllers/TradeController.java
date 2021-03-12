package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
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
public class TradeController {
  @Autowired private TradeRepository tradeRepository;

  private static Logger logger = LoggerFactory.getLogger(TradeController.class);

  @RequestMapping("/trade/list")
  public String home(Model model) {
    logger.info("http://localhost:8080/trade/list");
    model.addAttribute("trades", tradeRepository.findAll());
    return "trade/list";
  }

  @GetMapping("/trade/add")
  public String addUser(Trade bid) {
    logger.info("http://localhost:8080/trade/add");
    return "trade/add";
  }

  @PostMapping("/trade/validate")
  public String validate(@Valid Trade trade, BindingResult result, Model model) {
    logger.info("http://localhost:8080/trade/validate");
    if (!result.hasErrors()) {
      tradeRepository.save(trade);
      model.addAttribute("trades", tradeRepository.findAll());
      return "redirect:/trade/list";
    }
    return "trade/add";
  }

  @GetMapping("/trade/update/{id}")
  public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
    logger.info("http://localhost:8080/trade/update/" + id);
    Trade trade =
        tradeRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalide trade id" + id));
    model.addAttribute("trade", trade);
    return "trade/update";
  }

  @PostMapping("/trade/update/{id}")
  public String updateTrade(
      @PathVariable("id") Integer id, @Valid Trade trade, BindingResult result, Model model) {
    logger.info("http://localhost:8080/trade/update/" + id);
    if (result.hasErrors()) {
      return "trade/update";
    }

    trade.setTradeId(id);
    tradeRepository.save(trade);
    model.addAttribute("trades", tradeRepository.findAll());
    return "redirect:/trade/list";
  }

  @GetMapping("/trade/delete/{id}")
  public String deleteTrade(@PathVariable("id") Integer id, Model model) {
    logger.info("http://localhost:8080/trade/delete/" + id);
    Trade trade =
        tradeRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalide trade id" + id));
    tradeRepository.delete(trade);
    model.addAttribute("trades", tradeRepository.findAll());
    return "redirect:/trade/list";
  }
}
