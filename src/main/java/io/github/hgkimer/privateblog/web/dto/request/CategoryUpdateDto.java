package io.github.hgkimer.privateblog.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;

public record CategoryUpdateDto(
    @NotBlank(message = "Category name cannot be empty.")
    String name,
    @NotNull(message = "Slug cannot be null.")
    @Pattern(regexp = "^[a-z0-9-]+$", message = "Slug must be lowercase and can only contain letters, numbers and hyphens.")
    String slug,
    @NotNull(message = "Display order cannot be null.")
    @PositiveOrZero(message = "Display order must be positive or zero.")
    Integer displayOrder
) {

}
