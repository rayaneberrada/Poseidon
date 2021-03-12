package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.BidListRepository;
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
public class BidListController {
  @Autowired BidListRepository bidListRepository;

  private static Logger logger = LoggerFactory.getLogger(BidListController.class);

  @RequestMapping("/bidList/list")
  public String home(Model model) {
    logger.info("http://localhost:8080/bidList/list");
    model.addAttribute("bids", bidListRepository.findAll());
    return "bidList/list";
  }

  @GetMapping("/bidList/add")
  public String addBidForm(BidList bid) {
    logger.info("http://localhost:8080/bidList.add");
    return "bidList/add";
  }

  @PostMapping("/bidList/validate")
  public String validate(@Valid BidList bid, BindingResult result, Model model) {
    logger.info("http://localhost:8080/bidList/validate");
    if (!result.hasErrors()) {
      bidListRepository.save(bid);
      model.addAttribute("bids", bidListRepository.findAll());
      return "redirect:/bidList/list";
    }
    return "bidList/add";
  }

  @GetMapping("/bidList/update/{id}")
  public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
    logger.info("http://localhost:8080/bidList/update/" + id);
    BidList bid =
        bidListRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Bid id invalid:" + id));
    model.addAttribute("bidList", bid);
    return "bidList/update";
  }

  @PostMapping("/bidList/update/{id}")
  public String updateBid(
      @PathVariable("id") Integer id, @Valid BidList bidList, BindingResult result, Model model) {
    logger.info("http://localhost:8080/bidList/update/" + id);
    if (result.hasErrors()) {
      return "bidlist/update";
    }

    bidList.setBidListId(id);
    bidListRepository.save(bidList);
    model.addAttribute("bids", bidListRepository.findAll());
    return "redirect:/bidList/list";
  }

  @GetMapping("/bidList/delete/{id}")
  public String deleteBid(@PathVariable("id") Integer id, Model model) {
    logger.info("http://localhost:8080/bidList/delete/" + id);
    BidList bid =
        bidListRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid bid Id:" + id));
    bidListRepository.delete(bid);
    model.addAttribute("bids", bidListRepository.findAll());
    return "redirect:/bidList/list";
  }
}
