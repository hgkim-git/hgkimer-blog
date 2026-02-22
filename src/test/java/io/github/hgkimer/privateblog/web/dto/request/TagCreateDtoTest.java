package io.github.hgkimer.privateblog.web.dto.request;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TagCreateDtoTest {

  private Validator validator;

  @BeforeEach
  void setUp() {
    validator = Validation.buildDefaultValidatorFactory().getValidator();
  }

  @Test
  void givenValid_WhenValidating_ThenConstraintViolations() {
    TagCreateDto dto = new TagCreateDto("valid", "valid");
    Set<ConstraintViolation<TagCreateDto>> violations = validator.validate(dto);
    assertThat(violations).isEmpty();
  }

  @Test
  void givenNullOrEmpty_WhenValidating_ThenConstraintViolations() {
    TagCreateDto nullName = new TagCreateDto(null, "slug");
    TagCreateDto emptyName = new TagCreateDto("", "slug");
    TagCreateDto nullSlug = new TagCreateDto("name", null);

    List<TagCreateDto> nullOrEmptyNameDto = List.of(nullName, emptyName);
    nullOrEmptyNameDto.forEach(dto -> {
      Set<ConstraintViolation<TagCreateDto>> violations = validator.validate(dto);
      assertThat(violations).isNotEmpty().hasSize(1);
      assertThat(violations).map(ConstraintViolation::getMessage)
          .contains("Tag name cannot be empty.");
    });

    List<TagCreateDto> nullSlugDto = List.of(nullSlug);
    nullSlugDto.forEach(dto -> {
      Set<ConstraintViolation<TagCreateDto>> violations = validator.validate(dto);
      assertThat(violations).isNotEmpty().hasSize(1);
      assertThat(violations).map(ConstraintViolation::getMessage)
          .contains("Slug cannot be null.");
    });
  }

  @Test
  void givenInvalidSlug_WhenValidating_ThenConstraintViolations() {
    TagCreateDto invalidSlug = new TagCreateDto("name", "CAPITAL-contain");

    Set<ConstraintViolation<TagCreateDto>> violations = validator.validate(invalidSlug);
    assertThat(violations).isNotEmpty().hasSize(1);
    assertThat(violations).map(ConstraintViolation::getMessage)
        .contains("Slug must be lowercase and can only contain letters, numbers and hyphens.");
  }
}