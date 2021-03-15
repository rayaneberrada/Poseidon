package com.nnk.springboot;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.repositories.TradeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TradeControllerTestIT {

  @Autowired private MockMvc mockMvc;

  @Autowired private TradeRepository tradeRepository;

  @Test
  public void Request_Get_TradeList() throws Exception {
    mockMvc
        .perform(
            get("/trade/list").with(SecurityMockMvcRequestPostProcessors.httpBasic("demo", "demo")))
        .andExpect(status().isOk())
        .andExpect(view().name("trade/list"));
  }

  @Test
  public void Request_Get_TradeAddPage() throws Exception {
    mockMvc
        .perform(
            get("/trade/add").with(SecurityMockMvcRequestPostProcessors.httpBasic("demo", "demo")))
        .andExpect(status().isOk())
        .andExpect(view().name("trade/add"));
  }

  @Test
  public void Post_ValidateFail_AddingTrade() throws Exception {
    mockMvc
        .perform(
            post("/trade/validate")
                .param("moodysTrade", "failBecauseNumber")
                .param("sandPTrade", "1010")
                .param("fitchTrade", "100")
                .param("orderNumber", "100")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("demo", "demo"))
                .with(csrf()))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/trade/list"));
  }

  @Test
  public void Post_ValidateSuccess_AddingTrade() throws Exception {
    mockMvc
        .perform(
            post("/trade/validate")
                .param("moodysTrade", "100")
                .param("sandPTrade", "1010")
                .param("fitchTrade", "100")
                .param("orderNumber", "100")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("demo", "demo"))
                .with(csrf()))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/trade/list"));
  }

  @Test
  public void Post_UpdatedForm_WithSuccess() throws Exception {
    mockMvc
        .perform(
            post("/trade/update/{id}", 1)
                .param("moodysTrade", "100")
                .param("sandPTrade", "1010")
                .param("fitchTrade", "100")
                .param("orderNumber", "100")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("demo", "demo"))
                .with(csrf()))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/trade/list"));
  }

  @Test
  public void Post_UpdatedForm_WithFailure() throws Exception {
    mockMvc
        .perform(
            post("/trade/update/{id}", 1)
                .param("moodysTrade", "FailureBecauseNotNumber")
                .param("sandPTrade", "1010")
                .param("fitchTrade", "100")
                .param("orderNumber", "100")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("demo", "demo"))
                .with(csrf()))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/trade/list"));
  }

  @Test
  public void Get_ShowUpdatedForm_WithSuccess() throws Exception {
    List<Trade> trades = tradeRepository.findAll();
    mockMvc
        .perform(
            get("/trade/update/{id}", trades.get(trades.size() - 1).getTradeId())
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("demo", "demo")))
        .andExpect(status().isOk())
        .andExpect(view().name("trade/update"));
  }

  @Test
  public void Get_DeleteTrade_WithSuccess() throws Exception {
    List<Trade> trades = tradeRepository.findAll();
    mockMvc
        .perform(
            get("/trade/delete/{id}", trades.get(trades.size() - 1).getTradeId())
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("demo", "demo")))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/trade/list"));
  }
}
