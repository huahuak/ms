package top.moflowerlkh.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("top.moflowerlkh.msg")
@ComponentScan("top.moflowerlkh.backend")
@EnableJpaRepositories("top.moflowerlkh.msg.dao.repo")
@EntityScan("top.moflowerlkh.msg.dao.po")
@EnableJpaAuditing
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

}
