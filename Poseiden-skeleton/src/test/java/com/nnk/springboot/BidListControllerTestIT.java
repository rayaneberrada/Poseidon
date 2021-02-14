package com.nnk.springboot;

import com.nnk.springboot.controllers.BidListController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BidListControllerTestIT {

  @Autowired private MockMvc mockMvc;

  @Test
  public void Request_Get_BidListList() throws Exception {
    mockMvc
        .perform(
            get("/bidList/list")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("demo", "demo")))
        .andExpect(status().isOk())
        .andReturn()
        .equals("/bidList/list");
  }
}
