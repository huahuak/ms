package top.moflowerlkh.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("top.moflowerlkh.msg")
@ComponentScan("top.moflowerlkh.web")
@EnableJpaRepositories("top.moflowerlkh.msg.dao.repo")
@EntityScan("top.moflowerlkh.msg.dao.po")
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
