package io.github.hgkimer.privateblog.web.dto.response;

import io.github.hgkimer.privateblog.domain.entity.Tag;

public record TagResponseDto(
    Long id,
    String name,
    String slug,
    Long postCount
) {

  public static TagResponseDto from(Tag tag) {
    return new TagResponseDto(tag.getId(), tag.getName(), tag.getSlug(), 0L);
  }
}
