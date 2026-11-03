package com.abhinavlearning.url_shortner.records.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UrlRequest(
    @NotBlank
    @Pattern(
        regexp = "https?://.+",
        message = "URL must start with http:// or https://"
    )
    String url
) {
}
