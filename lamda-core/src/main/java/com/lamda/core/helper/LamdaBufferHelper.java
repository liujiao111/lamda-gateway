package com.lamda.core.helper;

/**
 * lamda File Description
 *
 * @author jiao.liu
 * @version 0.1
 * @create.date 2022-09-03 16:06
 * @modify.date 2022-09-03 16:06
 * @since 0.1
 */
public interface LamdaBufferHelper {

    String FLUSHER = "FLUSHER";

    String MPMC = "MPMC";

    static boolean isMpmc(String bufferType) {
        return MPMC.equals(bufferType);
    }

    static boolean isFlusher(String bufferType) {
        return FLUSHER.equals(bufferType);
    }
}
