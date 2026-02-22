package io.github.hgkimer.privateblog.web.dto.request;


import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CategoryCreateDtoTest {

  private Validator validator;

  @BeforeEach
  void setUp() {
    validator = Validation.buildDefaultValidatorFactory().getValidator();
  }

  @Test
  void givenValidDto_whenCreateDto_thenSuccess() {
    CategoryCreateDto dto = new CategoryCreateDto("test", "test");
    assertThat(dto).isNotNull();
    Set<ConstraintViolation<CategoryCreateDto>> violations = validator.validate(dto);
    assertThat(violations).isEmpty();
  }

  @Test
  void givenInvalidName_whenCreateDto_thenHaveViolation() {
    CategoryCreateDto dto = new CategoryCreateDto("", "test");
    Set<ConstraintViolation<CategoryCreateDto>> violations = validator.validate(dto);
    assertThat(violations).isNotEmpty().hasSize(1);
    assertThat(violations).
        allSatisfy(
            violation -> assertThat(violation.getMessage()).isEqualTo(
                "Category name cannot be empty."));
  }

  @Test
  void givenInvalidSlug_whenCreateDto_thenHaveViolation() {
    // null check
    CategoryCreateDto nullSlug = new CategoryCreateDto("test", null);
    Set<ConstraintViolation<CategoryCreateDto>> nullViolations = validator.validate(nullSlug);
    assertThat(nullViolations).isNotEmpty().hasSize(1);

    // Invalid Pattern
    CategoryCreateDto capital = new CategoryCreateDto("test", "CAPITAL-SLUG");
    CategoryCreateDto emptySlug = new CategoryCreateDto("test", "");
    CategoryCreateDto spaceSlug = new CategoryCreateDto("test", "space slug");
    List<CategoryCreateDto> invalidCases = List.of(capital, emptySlug, spaceSlug);
    for (CategoryCreateDto invalidDto : invalidCases) {
      Set<ConstraintViolation<CategoryCreateDto>> violations = validator.validate(invalidDto);
      assertThat(violations).isNotEmpty().hasSize(1);
      assertThat(violations).map(ConstraintViolation::getMessage).allMatch(
          message -> message.contains(
              "Slug must be lowercase and can only contain letters, numbers and hyphens."));
    }

  }
}