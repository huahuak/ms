package top.moflowerlkh.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BackendApplicationTests {

    /**
     * 1. 一个用例，
     *  1.1 保证两方转账正常，商品数也正常。
     *  1.2 消息服务等级，web -> mq -> backend 链路中间可能出现的问题
     *      /1 mq 消息重复
     *      /2 web -> mq 消息丢失
     *      /3 消息并发消费
     *  1.3 在出现异常情况下，能不能保证正常回滚，前端现实正常。
     * 2. 压测
     *  2.1 超卖、上锁的问题
     *  2.2 性能问题
     *      / tomcat 200 thread
     *      / skywalking
     * 3. 部署
     *  3.1 服务探活、自动重启
     *      / 写一个脚本，能够探活，如果对应端口没有响应，就直接重启
     *  3.2 保证 oom 2C4G
     *      / redis persist
     *      / mq （broker、nameserver）
     *      / ms backend & web
     *      / design system
     *      / nginx
     *      / docker
     */
    @Test
    void contextLoads() {
    }

}
