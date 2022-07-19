//package mq.uuid;
//
//import com.baomidou.mybatisplus.core.toolkit.IdWorker;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.UUID;
//
//@Component
//public class UidGenerator extends IdWorker implements InitializingBean {
//    private final Logger logger = LoggerFactory.getLogger(getClass());
//
//    @Autowired
//    private SequenceService sequenceService;
//
//    /**
//     * 使用sequence作为workerId，达到最大值时重置
//     *
//     * @param maxWorkerId 允许的最大workerId
//     * @return
//     */
//    @Override
//    protected long getWorkerId(long maxWorkerId) {
//        long workerId = sequenceService.nextValue("WORKER_ID_NO");
//        logger.info("current worker id of uid generate server is:{}", workerId);
//
//        if(workerId > maxWorkerId) {
//            throw new IllegalArgumentException("current worker id beyond limit of maxWorkerId: " + maxWorkerId);
//        }
//        return workerId;
//    }
//
//    public long getId() {
//        return super.nextId();
//    }
//
//    public String getIdStr() {
//        return Long.toString(super.nextId());
//    }
//
//    /**
//     * <p>
//     * 获取去掉"-" UUID
//     * </p>
//     */
//    public static String UUID() {
//        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
//    }
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        super.init();
//    }
//}
