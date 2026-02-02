package io.github.hgkimer.privateblog.domain.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class TagTest {

    static Tag mock() {
        return Tag.builder().name("test").slug("test").build();
    }

    @Test
    void build() {
        Tag tag = Tag.builder().name("test").slug("test").build();
        assertNotNull(tag);
        assertEquals("test", tag.getName());
        assertEquals("test", tag.getSlug());
    }

    @Test
    void update() {
        Tag tag = mock();
        tag.update("test2", "test2");
        assertEquals("test2", tag.getName());
        assertEquals("test2", tag.getSlug());
    }

    @Test
    void changePostCount() {
        Tag tag = mock();
        tag.increasePostCount();
        assertEquals(1, tag.getPostCount());

        tag.decreasePostCount();
        assertEquals(0, tag.getPostCount());
    }
}