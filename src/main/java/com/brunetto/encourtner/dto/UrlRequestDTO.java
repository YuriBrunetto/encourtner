package com.brunetto.encourtner.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class UrlRequestDTO {
    @NotBlank(message = "A URL não pode estar em branco.")
    @URL(message = "O formato da URL é inválido.")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
