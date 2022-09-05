package disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import com.lmax.disruptor.dsl.ProducerType;

import java.nio.ByteBuffer;
import java.util.concurrent.ThreadFactory;

/**
 * lamda File Description
 *  Disruptor入门示例代码
 * @author jiao.liu
 * @version 0.1
 * @create.date 2022-09-03 20:59
 * @modify.date 2022-09-03 20:59
 * @since 0.1
 */
public class DSMain {

    public static void main(String[] args) {
        int ringBufferSize = 1024 * 1024;
        final OrderEventFactory orderEventFactory = new OrderEventFactory();
        final Disruptor<OrderEvent> disruptor = new Disruptor<OrderEvent>(orderEventFactory,
                ringBufferSize,
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        final Thread thread = new Thread(r);
                        return thread;
                    }
                }, ProducerType.SINGLE, new BlockingWaitStrategy());
        final EventHandlerGroup<OrderEvent> orderEventEventHandlerGroup = disruptor.handleEventsWith(new OrderEventHandler());
        disruptor.start();

        //生产者发送消息
        final RingBuffer<OrderEvent> ringBuffer = disruptor.getRingBuffer();

        final OrderEventProducer producer = new OrderEventProducer(ringBuffer);

        final ByteBuffer allocate = ByteBuffer.allocate(8);

        for (int i = 0; i < 100; i++) {
            allocate.putLong(0, i);
            producer.putData(allocate);
        }

        disruptor.shutdown();
    }
}
