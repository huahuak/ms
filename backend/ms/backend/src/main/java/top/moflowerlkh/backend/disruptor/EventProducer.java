package top.moflowerlkh.backend.disruptor;


import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import lombok.AllArgsConstructor;
import lombok.Setter;


@Setter
@AllArgsConstructor
public abstract class EventProducer<T extends ArgEventInterface<A>, A> {
    private RingBuffer<T> ringBuffer;

    private final EventTranslatorOneArg<T, A> TRANSLATOR = (event, sequence, data) -> {
        event.setArg(data);
    };

    public void publishEvent(A arg) {
        ringBuffer.publishEvent(TRANSLATOR, arg);
    }

}
