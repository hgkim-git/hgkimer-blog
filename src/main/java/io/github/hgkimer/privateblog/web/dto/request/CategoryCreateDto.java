package io.github.hgkimer.privateblog.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CategoryCreateDto(
    @NotBlank(message = "Category name cannot be empty.")
    String name,
    @NotNull(message = "Slug cannot be null")
    @Pattern(regexp = "^[a-z0-9-]+$", message = "Slug must be lowercase and can only contain letters, numbers and hyphens.")
    String slug
) {

}
