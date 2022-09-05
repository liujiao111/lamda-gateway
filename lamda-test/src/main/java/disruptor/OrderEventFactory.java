package disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * lamda File Description
 *
 * @author jiao.liu
 * @version 0.1
 * @create.date 2022-09-03 21:42
 * @modify.date 2022-09-03 21:42
 * @since 0.1
 */
public class OrderEventFactory implements EventFactory {
    @Override
    public Object newInstance() {
        return new OrderEvent();
    }
}
