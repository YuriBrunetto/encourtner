package com.brunetto.encourtner.controller;

import com.brunetto.encourtner.model.Url;
import com.brunetto.encourtner.service.UrlService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RedirectController.class)
class RedirectControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UrlService urlService;


    @Test
    void shouldRedirectToOriginalUrl_whenShortCodeExists() throws Exception {
        String shortCode = "abc123";
        String longUrl = "https://google.com/";
        Url returnUrl = new Url();
        returnUrl.setLongUrl(longUrl);

        Mockito.when(urlService.getOriginalUrlAndIncrementViews(shortCode)).thenReturn(returnUrl);

        ResultActions resultActions = mockMvc.perform(get("/" + shortCode));

        resultActions.andExpect(status().isFound());
        resultActions.andExpect(header().string("Location", longUrl));
    }
}
