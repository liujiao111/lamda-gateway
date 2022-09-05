package disruptor;

import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * lamda File Description
 *
 * @author jiao.liu
 * @version 0.1
 * @create.date 2022-09-03 21:46
 * @modify.date 2022-09-03 21:46
 * @since 0.1
 */
public class OrderEventProducer {

    private RingBuffer<OrderEvent> ringBuffer;

    public OrderEventProducer(RingBuffer<OrderEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    /**
     * 发送数据到环形队列
     * @param allocate
     */
    public void putData(ByteBuffer allocate) {
        //获取到下一个可用的序列号
        final long sequence = ringBuffer.next();
        //通过可用的序列号获取对应的OrderEvent
        try {
            final OrderEvent orderEvent = ringBuffer.get(sequence);
            orderEvent.setValue(allocate.getLong(0));
        } finally {
            //publish
            ringBuffer.publish(sequence);
        }

    }
}
