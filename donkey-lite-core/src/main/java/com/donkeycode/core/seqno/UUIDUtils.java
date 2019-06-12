package com.donkeycode.core.seqno;

import java.util.UUID;

/**
 * @author donkey
 */
public class UUIDUtils {

    public static String getRandomUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
