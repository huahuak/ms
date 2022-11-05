package top.moflowerlkh.msg.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "custom.rocketmq.group")
public class RocketMQConsumerGroupConst {
    private String consumer;
}