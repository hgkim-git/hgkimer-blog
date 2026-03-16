package io.github.hgkimer.privateblog.web.dto.request;


import static org.assertj.core.api.Assertions.assertThat;

import io.github.hgkimer.privateblog.domain.enums.PostStatus;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PostCreateDtoTest {

  private Validator validator;

  @BeforeEach
  void setUp() {
    validator = Validation.buildDefaultValidatorFactory().getValidator();
  }

  @Test
  void givenValidDto_whenCreateDto_thenSuccess() {
    PostCreateDto dto = DtoBuilder.builder().build();
    Set<ConstraintViolation<PostCreateDto>> violations = validator.validate(dto);
    assertThat(violations).isEmpty();
  }

  @Test
  void givenInvalidStatus_whenValidate_thenHaveViolation() {
    PostCreateDto dto = DtoBuilder.builder().status("INVALID").build();
    Set<ConstraintViolation<PostCreateDto>> violations = validator.validate(dto);
    assertThat(violations).isNotEmpty().hasSize(1);
    assertThat(violations).map(ConstraintViolation::getMessage)
        .contains("Status must be either DRAFT or PUBLISHED.");
  }

  static class DtoBuilder {

    private final List<Long> tagIds = List.of();
    private Long categoryId;
    private String title;
    private String content;
    private String summary;
    private String slug;
    private String status;

    static DtoBuilder builder() {
      return new DtoBuilder();
    }

    DtoBuilder categoryId(String category) {
      this.categoryId = Long.parseLong(category);
      return this;
    }

    DtoBuilder title(String title) {
      this.title = title;
      return this;
    }

    DtoBuilder content(String content) {
      this.content = content;
      return this;
    }

    DtoBuilder summary(String summary) {
      this.summary = summary;
      return this;
    }

    DtoBuilder slug(String slug) {
      this.slug = slug;
      return this;
    }

    DtoBuilder status(String status) {
      this.status = status;
      return this;
    }

    PostCreateDto build() {
      if (title == null) {
        title = "title";
      }
      if (content == null) {
        content = "content";
      }
      if (summary == null) {
        summary = "summary";
      }
      if (slug == null) {
        slug = "test-slug";
      }
      if (status == null) {
        status = PostStatus.DRAFT.name();
      }
      return new PostCreateDto(
          categoryId,
          title,
          content,
          summary,
          slug,
          status,
          tagIds);
    }
  }
}