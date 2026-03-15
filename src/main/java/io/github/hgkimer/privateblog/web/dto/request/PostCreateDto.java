package io.github.hgkimer.privateblog.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import org.hibernate.validator.constraints.Length;

public record PostCreateDto(
    Long categoryId,
    @NotBlank(message = "Title cannot be empty.")
    String title,
    @NotNull(message = "Content cannot be null.")
    String content,
    String summary,
    @NotBlank(message = "Slug cannot be empty.")
    @Length(min = 1, max = 250, message = "Slug must be between 1 and 250 characters long.")
    @Pattern(regexp = "^[a-z0-9-]+$", message = "Slug must be lowercase and can only contain letters, numbers and hyphens.")
    String slug,
    @NotBlank(message = "Status cannot be empty.")
    @Pattern(regexp = "^(?i)(DRAFT|PUBLISHED)$", message = "Status must be either DRAFT or PUBLISHED.")
    String status,
    List<Long> tagIds
) {

}
