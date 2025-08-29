package com.brunetto.encourtner.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class UrlRequestDTO {
    @NotBlank(message = "A URL não pode estar em branco.")
    @URL(message = "O formato da URL é inválido.")
    private String url;
//    private String expirationDate;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

//    public String getExpirationDate() {
//        return expirationDate;
//    }
//
//    public void setExpirationDate(String expirationDate) {
//        this.expirationDate = expirationDate;
//    }
}
