package disruptor;

import com.lmax.disruptor.EventHandler;

/**
 * lamda File Description
 *
 * @author jiao.liu
 * @version 0.1
 * @create.date 2022-09-03 21:44
 * @modify.date 2022-09-03 21:44
 * @since 0.1
 */
public class OrderEventHandler implements EventHandler<OrderEvent> {
    @Override
    public void onEvent(OrderEvent orderEvent, long l, boolean b) throws Exception {
        System.out.println("消费者消费：" + orderEvent.getValue());
    }
}
