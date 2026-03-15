package io.github.hgkimer.privateblog.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record TagCreateDto(
    @NotBlank(message = "Tag name cannot be empty.")
    String name,
    @NotNull(message = "Slug cannot be null.")
    @Length(min = 1, max = 50, message = "Slug must be between 1 and 50 characters long.")
    @Pattern(regexp = "^[a-z0-9-]+$", message = "Slug must be lowercase and can only contain letters, numbers and hyphens.")
    String slug
) {

}
