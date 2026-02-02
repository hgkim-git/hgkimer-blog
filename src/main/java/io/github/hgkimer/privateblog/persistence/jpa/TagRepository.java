package io.github.hgkimer.privateblog.persistence.jpa;

import io.github.hgkimer.privateblog.domain.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {


}
