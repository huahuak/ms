package top.moflowerlkh.backend.disruptor.config;

import com.lmax.disruptor.dsl.Disruptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import top.moflowerlkh.backend.cache.UserOrderRecordCache;
import top.moflowerlkh.backend.cache.impl.BankAccountCache;
import top.moflowerlkh.backend.cache.impl.GoodsCache;
import top.moflowerlkh.backend.disruptor.ms.MsEvent;
import top.moflowerlkh.backend.disruptor.ms.MsEventFactory;
import top.moflowerlkh.backend.disruptor.ms.MsEventProducer;
import top.moflowerlkh.backend.disruptor.ms.hadnler.MsBusinessHandler;
import top.moflowerlkh.backend.disruptor.ms.hadnler.MsGcHandler;
import top.moflowerlkh.backend.disruptor.ms.hadnler.MsPersistHandler;
import top.moflowerlkh.backend.disruptor.ms.hadnler.MsResponseHandler;
import top.moflowerlkh.backend.disruptor.persist.PersistEvent;
import top.moflowerlkh.backend.disruptor.persist.PersistEventFactory;
import top.moflowerlkh.backend.disruptor.persist.PersistGcHandler;
import top.moflowerlkh.backend.disruptor.persist.bankaccount.BankOperationRecordBuffer;
import top.moflowerlkh.backend.disruptor.persist.bankaccount.PersistBankAccountEventProducer;
import top.moflowerlkh.backend.disruptor.persist.bankaccount.PersistBankAccountHandler;
import top.moflowerlkh.backend.disruptor.persist.goodsorder.GoodsOrderBuffer;
import top.moflowerlkh.backend.disruptor.persist.goodsorder.PersistGoodsOrderEventProducer;
import top.moflowerlkh.backend.disruptor.persist.goodsorder.PersistGoodsOrderHandler;
import top.moflowerlkh.msg.dao.po.acount.BankOperationRecord;
import top.moflowerlkh.msg.dao.po.goods.GoodsOrder;
import top.moflowerlkh.msg.service.ResponseService;

import java.util.concurrent.Executors;

@Configuration
public class DisruptorConfig {
    @Autowired
    private ConfigurableBeanFactory configurableBeanFactory;

    @Autowired
    private PersistExecutorRouter persistExecutorRouter;

    @Autowired
    private ResponseService responseService;

    @Autowired
    private GoodsCache goodsCache;

    @Autowired
    private BankAccountCache bankAccountCache;

    @Autowired
    private UserOrderRecordCache userOrderRecordCachea;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private GoodsOrderBuffer goodsOrderBuffer;

    @Autowired
    private BankOperationRecordBuffer bankOperationRecordBuffer;

    private final static String LIFE = "Life";
    private final static String GOODS_ORDER_EVENT_PRODUCER = "goodsOrderEventProducer";
    private final static String PERSIST_EVENT_PRODUCER = "goodsOrderPersistEventProducer";
    private final static String GOODS_ORDER_SUFFIX = "_GoodsOrder";
    private final static String BANK_OPERATION_RECORD_SUFFIX = "_BankOperationRecord";

    @Bean
    public MsEventProducer MsEventProducer() {
        // todo set to properties
        Disruptor<MsEvent> disruptor = new Disruptor<>(
                new MsEventFactory(goodsCache, bankAccountCache, userOrderRecordCachea),
                1024 * 1024,
                Executors.defaultThreadFactory()
        );
        MsEventProducer goodsOrderEventProducer = new MsEventProducer(disruptor.getRingBuffer());

        // -- handler -- //
        MsBusinessHandler goodsOrderBusinessHandler = new MsBusinessHandler();
        MsGcHandler msGcHandler = new MsGcHandler();

        MsResponseHandler goodsOrderResponseHandler = new MsResponseHandler();
        goodsOrderResponseHandler.setResponseService(responseService);

        MsPersistHandler goodsOrderPersistHandler = new MsPersistHandler();
        goodsOrderPersistHandler.setPersistExecutorRouter(persistExecutorRouter);


        disruptor.handleEventsWith(goodsOrderBusinessHandler)
                .then(goodsOrderPersistHandler, goodsOrderResponseHandler)
                .then(msGcHandler);

        // -- start register -- //
        configurableBeanFactory.registerSingleton(GOODS_ORDER_EVENT_PRODUCER + LIFE,
                new DisruptorLifeCycleConfig(
                        GOODS_ORDER_EVENT_PRODUCER,
                        disruptor,
                        DisruptorLifeCycleConfig.PHASE_ONE
                ));

        return goodsOrderEventProducer;
    }

    @Bean
    public PersistGoodsOrderEventProducer goodsOrderPersistEventProducer() {
        Disruptor<PersistEvent<GoodsOrder>> disruptor = new Disruptor<>(
                new PersistEventFactory<>(),
                1024 * 1024,
                Executors.defaultThreadFactory()
        );
        PersistGoodsOrderEventProducer goodsOrderPersistEventProducer =
                new PersistGoodsOrderEventProducer(disruptor.getRingBuffer());


        PersistGoodsOrderHandler persistGoodsOrderHandler = new PersistGoodsOrderHandler();
        persistGoodsOrderHandler.setGoodsOrderBuffer(goodsOrderBuffer);
        PersistGcHandler<GoodsOrder> goodsOrderPersistGcHandler = new PersistGcHandler<>();
        disruptor.handleEventsWith(persistGoodsOrderHandler)
                .then(goodsOrderPersistGcHandler);

        configurableBeanFactory.registerSingleton(PERSIST_EVENT_PRODUCER + GOODS_ORDER_SUFFIX + LIFE,
                new DisruptorLifeCycleConfig(
                        PERSIST_EVENT_PRODUCER,
                        disruptor,
                        DisruptorLifeCycleConfig.PHASE_TWO
                ));

        persistExecutorRouter.registerExecutor(goodsOrderPersistEventProducer);
        return goodsOrderPersistEventProducer;
    }

    @Bean
    public PersistBankAccountEventProducer persistBankAccountEventProducer() {
        Disruptor<PersistEvent<BankOperationRecord>> disruptor = new Disruptor<>(
                new PersistEventFactory<>(),
                1024 * 1024,
                Executors.defaultThreadFactory()
        );
        PersistBankAccountEventProducer persistBankAccountEventProducer =
                new PersistBankAccountEventProducer(disruptor.getRingBuffer());

        PersistBankAccountHandler persistBankAccountHandler = new PersistBankAccountHandler();
        persistBankAccountHandler.setBankOperationRecordBuffer(bankOperationRecordBuffer);
        PersistGcHandler<BankOperationRecord> bankOperationRecordPersistGcHandler = new PersistGcHandler<>();

        disruptor.handleEventsWith(persistBankAccountHandler)
                .then(bankOperationRecordPersistGcHandler);

        configurableBeanFactory.registerSingleton(PERSIST_EVENT_PRODUCER + BANK_OPERATION_RECORD_SUFFIX + LIFE,
                new DisruptorLifeCycleConfig(
                        PERSIST_EVENT_PRODUCER,
                        disruptor,
                        DisruptorLifeCycleConfig.PHASE_TWO
                ));
        persistExecutorRouter.registerExecutor(persistBankAccountEventProducer);
        return persistBankAccountEventProducer;
    }

}
