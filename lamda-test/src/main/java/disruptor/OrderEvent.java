package disruptor;

/**
 * lamda File Description
 *
 * @author jiao.liu
 * @version 0.1
 * @create.date 2022-09-03 21:40
 * @modify.date 2022-09-03 21:40
 * @since 0.1
 */
public class OrderEvent {

    private long value;

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
