package top.moflowerlkh.backend.disruptor.config;

import com.lmax.disruptor.dsl.Disruptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.SmartLifecycle;

@Slf4j
public class DisruptorLifeCycleConfig implements SmartLifecycle {

    private boolean isRunning = false;
    private String name;
    private Disruptor disruptor;
    private final int phase;

    public static final int PHASE_ONE = 1;
    public static final int PHASE_TWO = 2;
    public static final int PHASE_THREE = 3;

    public DisruptorLifeCycleConfig(String name, Disruptor disruptor, int phase) {
        this.name = name;
        this.disruptor = disruptor;
        this.phase = phase;
    }


    @Override
    public void start() {
        log.info("disruptor -> {} start", name);
        disruptor.start();
        this.isRunning = true;
    }

    @Override
    public void stop() {
        log.info("disruptor -> {} stop", name);
        disruptor.shutdown();
        this.isRunning = false;
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public boolean isAutoStartup() {
        return true;
    }

    @Override
    public void stop(Runnable callback) {
        this.stop();
        callback.run();
    }

    @Override
    public int getPhase() {
        return phase;
    }
}
