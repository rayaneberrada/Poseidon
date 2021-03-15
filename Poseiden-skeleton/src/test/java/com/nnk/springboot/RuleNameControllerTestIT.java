package com.nnk.springboot;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.repositories.RuleNameRepository;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RuleNameControllerTestIT {

  @Autowired private MockMvc mockMvc;

  @Autowired private RuleNameRepository ruleNameRepository;

  @Test
  public void Request_Get_RatingList() throws Exception {
    mockMvc
        .perform(
            get("/ruleName/list")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("demo", "demo")))
        .andExpect(status().isOk())
        .andExpect(view().name("ruleName/list"));
  }

  @Test
  public void Request_Get_RatingAddPage() throws Exception {
    mockMvc
        .perform(
            get("/ruleName/add")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("demo", "demo")))
        .andExpect(status().isOk())
        .andExpect(view().name("ruleName/add"));
  }

  @Test
  public void Post_ValidateFail_AddingRating() throws Exception {
    mockMvc
        .perform(
            post("/ruleName/validate")
                .param("moodysRating", "failBecauseNumber")
                .param("sandPRating", "1010")
                .param("fitchRating", "100")
                .param("orderNumber", "100")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("demo", "demo"))
                .with(csrf()))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/ruleName/list"));
  }

  @Test
  public void Post_ValidateSuccess_AddingRating() throws Exception {
    mockMvc
        .perform(
            post("/ruleName/validate")
                .param("moodysRating", "100")
                .param("sandPRating", "1010")
                .param("fitchRating", "100")
                .param("orderNumber", "100")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("demo", "demo"))
                .with(csrf()))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/ruleName/list"));
  }

  @Test
  public void Post_UpdatedForm_WithSuccess() throws Exception {
    mockMvc
        .perform(
            post("/ruleName/update/{id}", 1)
                .param("moodysRating", "100")
                .param("sandPRating", "1010")
                .param("fitchRating", "100")
                .param("orderNumber", "100")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("demo", "demo"))
                .with(csrf()))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/ruleName/list"));
  }

  @Test
  public void Post_UpdatedForm_WithFailure() throws Exception {
    mockMvc
        .perform(
            post("/ruleName/update/{id}", 1)
                .param("moodysRating", "FailureBecauseNotNumber")
                .param("sandPRating", "1010")
                .param("fitchRating", "100")
                .param("orderNumber", "100")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("demo", "demo"))
                .with(csrf()))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/ruleName/list"));
  }

  @Test
  public void Get_ShowUpdatedForm_WithSuccess() throws Exception {
    List<RuleName> rule = ruleNameRepository.findAll();

    mockMvc
        .perform(
            get("/ruleName/update/{id}", rule.get(rule.size() - 1).getId())
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("demo", "demo")))
        .andExpect(status().isOk())
        .andExpect(view().name("ruleName/update"));
  }

  @Test
  public void Get_DeleteRating_WithSuccess() throws Exception {
    List<RuleName> rule = ruleNameRepository.findAll();
    mockMvc
        .perform(
            get("/ruleName/delete/{id}", rule.get(rule.size() - 1).getId())
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("demo", "demo")))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/ruleName/list"));
  }
}
