package io.github.hgkimer.privateblog.persistence.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import io.github.hgkimer.privateblog.domain.entity.Tag;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class TagRepositoryTest {

    @Autowired
    EntityManager entityManager;
    @Autowired
    private TagRepository tagRepository;
    private Tag tag;

    @BeforeEach
    void setup() {
        tag = Tag.builder().name("test").slug("test").build();
    }


    @Test
    void createTag() {
        assertNull(tag.getId());
        tagRepository.save(tag);
        assertNotNull(tag.getId());
    }

    @Test
    void deleteTag() {
        tagRepository.save(tag);
        tagRepository.delete(tag);
        entityManager.flush();
        assertEquals(0, tagRepository.findAll().size());
    }

    @Test
    void updateTag() {
        tagRepository.save(tag);
        tag.update("test2", "test2");
        entityManager.flush();
        assertEquals("test2", tag.getName());
        assertEquals("test2", tag.getSlug());
    }

}