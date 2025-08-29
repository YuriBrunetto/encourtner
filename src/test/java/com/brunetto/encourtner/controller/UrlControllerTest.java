package com.brunetto.encourtner.controller;

import com.brunetto.encourtner.dto.UrlRequestDTO;
import com.brunetto.encourtner.model.Url;
import com.brunetto.encourtner.service.UrlService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UrlController.class)
public class UrlControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UrlService urlService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateShortenUrl_whenRequestIsValid() throws Exception {
        String longUrl = "https://google.com/";

        UrlRequestDTO requestDTO = new UrlRequestDTO();
        requestDTO.setUrl(longUrl);

        Url returnUrl = new Url();
        returnUrl.setLongUrl(longUrl);
        returnUrl.setShortCode("abc123");
        returnUrl.setId("mongo-id-123");

        Mockito.when(urlService.generateShortUrl(Mockito.any(String.class))).thenReturn(returnUrl);

        ResultActions resultActions = mockMvc.perform(post("/api/v1/shorten")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO))
        );
        resultActions.andExpect(status().isCreated());

        resultActions.andExpect(jsonPath("$.longUrl").value(longUrl));
        resultActions.andExpect(jsonPath("$.shortCode").value("abc123"));
    }
}
