package com.donkeycode.boot.pubinterface;

import java.util.Map;

import com.donkeycode.boot.pubinterface.info.DataEditInfo;
import com.donkeycode.boot.pubinterface.info.LinkedInfo;
import com.donkeycode.core.exception.FunctUnrealizedException;

/**
 * @author yanjun.xue
 * @since 2019年5月19日
 */
public interface DataEditService {

    String DEFAULT_MESSAGE_KEY = "未实现该功能.";

    /**
     * @param dataEditInfo
     * @return
     */
    default LinkedInfo insert(DataEditInfo dataEditInfo) {
        throw new FunctUnrealizedException(DEFAULT_MESSAGE_KEY);
    }

    /**
     * @param dataEditInfo
     * @return
     */
    default LinkedInfo update(DataEditInfo dataEditInfo) {
        throw new FunctUnrealizedException(DEFAULT_MESSAGE_KEY);
    }

    /**
     * @param params
     * @return
     */
    default LinkedInfo update(Map<String, String> params) {
        throw new FunctUnrealizedException(DEFAULT_MESSAGE_KEY);
    }

    /**
     * @param dataEditInfo
     * @return
     */
    default LinkedInfo delete(DataEditInfo dataEditInfo) {
        throw new FunctUnrealizedException(DEFAULT_MESSAGE_KEY);
    }
}
