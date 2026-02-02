package io.github.hgkimer.privateblog.service;

import io.github.hgkimer.privateblog.domain.entity.Tag;
import io.github.hgkimer.privateblog.persistence.jpa.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagService tagService;

    private Tag tag;

    @BeforeEach
    void setUp() {
        tag = Tag.builder().name("test").slug("test").build();
    }

    @Test
    void createCategory() {
        tagService.createTage(tag);
    }

    @Test
    void deleteTag() {
        tagService.deleteTag(tag.getId());
    }

    @Test
    void updateTag() {
        Tag updatedTag = Tag.builder().name("test2").slug("test2").build();
//        tagService.updateTag(tag.getId(), updatedTag);
    }

    @Test
    void getTagById() {
//        tagService.getTagById(tag.getId());
    }
}