package com.nnk.springboot;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.repositories.CurvePointRepository;
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
public class CurveControllerTestIT {

  @Autowired private MockMvc mockMvc;

  @Autowired private CurvePointRepository curvePointRepository;

  @Test
  public void Request_Get_CurvePointList() throws Exception {
    mockMvc
        .perform(
            get("/curvePoint/list")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("demo", "demo")))
        .andExpect(status().isOk())
        .andExpect(view().name("curvePoint/list"));
  }

  @Test
  public void Request_Get_CurvePointAddPage() throws Exception {
    mockMvc
        .perform(
            get("/curvePoint/add")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("demo", "demo")))
        .andExpect(status().isOk())
        .andExpect(view().name("curvePoint/add"));
  }

  @Test
  public void Post_ValidateFail_AddingBid() throws Exception {
    mockMvc
        .perform(
            post("/curvePoint/validate")
                .param("curveId", "failBecauseNumber")
                .param("term", "1010")
                .param("value", "100")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("demo", "demo"))
                .with(csrf()))
        .andExpect(status().isOk())
        .andExpect(view().name("curvePoint/add"));
  }

  @Test
  public void Post_ValidateSuccess_AddingBid() throws Exception {
    mockMvc
        .perform(
            post("/curvePoint/validate")
                .param("account", "enric bold")
                .param("type", "normal")
                .param("bidQuantity", "100")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("demo", "demo"))
                .with(csrf()))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/curvePoint/list"));
  }

  @Test
  public void Get_ShowUpdatedForm_WithSuccess() throws Exception {
    mockMvc
        .perform(
            get("/curvePoint/update/{id}", 2)
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("demo", "demo")))
        .andExpect(status().isOk())
        .andExpect(view().name("curvePoint/update"));
  }

  @Test
  public void Post_UpdatedForm_WithSuccess() throws Exception {
    mockMvc
        .perform(
            post("/curvePoint/update/{id}", 1)
                .param("curveId", "23")
                .param("term", "12")
                .param("value", "100")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("demo", "demo"))
                .with(csrf()))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/curvePoint/list"));
  }

  @Test
  public void Post_UpdatedForm_WithFailure() throws Exception {
    mockMvc
        .perform(
            post("/curvePoint/update/{id}", 1)
                .param("curveId", "1001")
                .param("term", "1010")
                .param("value", "100")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("demo", "demo"))
                .with(csrf()))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/curvePoint/list"));
  }

  @Test
  public void Get_DeleteBid_WithSuccess() throws Exception {
    List<CurvePoint> curvePoint = curvePointRepository.findAll();
    mockMvc
        .perform(
            get("/curvePoint/delete/{id}", curvePoint.get(curvePoint.size() - 1).getId())
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("demo", "demo")))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/curvePoint/list"));
  }
}
