package com.nnk.springboot;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
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
public class RatingControllerTestIT {

  @Autowired private MockMvc mockMvc;

  @Autowired private RatingRepository ratingRepository;

  @Test
  public void Request_Get_RatingList() throws Exception {
    mockMvc
        .perform(
            get("/rating/list")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("demo", "demo")))
        .andExpect(status().isOk())
        .andExpect(view().name("rating/list"));
  }

  @Test
  public void Request_Get_RatingAddPage() throws Exception {
    mockMvc
        .perform(
            get("/rating/add").with(SecurityMockMvcRequestPostProcessors.httpBasic("demo", "demo")))
        .andExpect(status().isOk())
        .andExpect(view().name("rating/add"));
  }

  @Test
  public void Post_ValidateFail_AddingRating() throws Exception {
    mockMvc
        .perform(
            post("/rating/validate")
                .param("moodysRating", "failBecauseNumber")
                .param("sandPRating", "1010")
                .param("fitchRating", "100")
                .param("orderNumber", "100")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("demo", "demo"))
                .with(csrf()))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/rating/list"));
  }

  @Test
  public void Post_ValidateSuccess_AddingRating() throws Exception {
    mockMvc
        .perform(
            post("/rating/validate")
                .param("moodysRating", "100")
                .param("sandPRating", "1010")
                .param("fitchRating", "100")
                .param("orderNumber", "100")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("demo", "demo"))
                .with(csrf()))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/rating/list"));
  }

  @Test
  public void Get_ShowUpdatedForm_WithSuccess() throws Exception {
    mockMvc
        .perform(
            get("/rating/update/{id}", 2)
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("demo", "demo")))
        .andExpect(status().isOk())
        .andExpect(view().name("rating/update"));
  }

  @Test
  public void Post_UpdatedForm_WithSuccess() throws Exception {
    mockMvc
        .perform(
            post("/rating/update/{id}", 1)
                .param("moodysRating", "100")
                .param("sandPRating", "1010")
                .param("fitchRating", "100")
                .param("orderNumber", "100")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("demo", "demo"))
                .with(csrf()))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/rating/list"));
  }

  @Test
  public void Post_UpdatedForm_WithFailure() throws Exception {
    mockMvc
        .perform(
            post("/rating/update/{id}", 1)
                .param("moodysRating", "FailureBecauseNotNumber")
                .param("sandPRating", "1010")
                .param("fitchRating", "100")
                .param("orderNumber", "100")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("demo", "demo"))
                .with(csrf()))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/rating/list"));
  }

  @Test
  public void Get_DeleteRating_WithSuccess() throws Exception {
    List<Rating> rating = ratingRepository.findAll();
    mockMvc
        .perform(
            get("/rating/delete/{id}", rating.get(rating.size() - 1).getId())
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("demo", "demo")))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/rating/list"));
  }
}
