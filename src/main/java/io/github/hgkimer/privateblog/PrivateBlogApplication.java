package io.github.hgkimer.privateblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
// JPA Auditing 활성화를 통해 엔티티의 생성/수정 시간 정보를 자동으로 관리
@EnableJpaAuditing
public class PrivateBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(PrivateBlogApplication.class, args);
    }

}
