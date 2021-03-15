package com.nnk.springboot;

import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BidListControllerTestIT {

  @Autowired private MockMvc mockMvc;

  @Autowired private BidListRepository bidListRepository;

  @Test
  public void Request_Get_BidListList() throws Exception {
    mockMvc
        .perform(
            get("/bidList/list")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("demo", "demo")))
        .andExpect(status().isOk())
        .andExpect(view().name("bidList/list"));
  }

  @Test
  public void Request_Get_BidListAddPage() throws Exception {
    mockMvc
        .perform(
            get("/bidList/add")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("demo", "demo")))
        .andExpect(status().isOk())
        .andExpect(view().name("bidList/add"));
  }

  @Test
  public void Post_ValidateFail_AddingBid() throws Exception {
    mockMvc
        .perform(
            post("/bidList/validate")
                .param("bidQuantity", "shouldBeInteger")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("demo", "demo"))
                .with(csrf()))
        .andExpect(status().isOk())
        .andExpect(view().name("bidList/add"));
  }

  @Test
  public void Post_ValidateSuccess_AddingBid() throws Exception {
    mockMvc
        .perform(
            post("/bidList/validate")
                .param("account", "enric bold")
                .param("type", "normal")
                .param("bidQuantity", "100")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("demo", "demo"))
                .with(csrf()))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/bidList/list"));
  }

  @Test
  public void Post_UpdatedForm_WithSuccess() throws Exception {
    mockMvc
        .perform(
            post("/bidList/update/{id}", 1)
                .param("account", "enric bold")
                .param("type", "normal")
                .param("bidQuantity", "100")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("demo", "demo"))
                .with(csrf()))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/bidList/list"));
  }

  @Test
  public void Post_UpdatedForm_WithFailure() throws Exception {
    mockMvc
        .perform(
            post("/bidList/update/{id}", 1)
                .param("bidQuantity", "shouldBeInteger")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("demo", "demo"))
                .with(csrf()))
        .andExpect(status().isOk())
        .andExpect(view().name("bidlist/update"));
  }

  @Test
  public void Get_ShowUpdatedForm_WithSuccess() throws Exception {
    List<BidList> bids = bidListRepository.findAll();
    mockMvc
        .perform(
            get("/bidList/update/{id}", bids.get(bids.size() - 1).getBidListId())
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("demo", "demo")))
        .andExpect(status().isOk())
        .andExpect(view().name("bidList/update"));
  }

  @Test
  public void Get_DeleteBid_WithSuccess() throws Exception {
    List<BidList> bids = bidListRepository.findAll();
    mockMvc
        .perform(
            get("/bidList/delete/{id}", bids.get(bids.size() - 1).getBidListId())
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("demo", "demo")))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/bidList/list"));
  }
}
