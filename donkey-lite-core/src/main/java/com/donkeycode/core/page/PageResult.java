package com.donkeycode.core.page;

import java.util.Collections;
import java.util.List;

import com.donkeycode.core.BaseEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * @param <E>
 * @author donkey
 * @since 2019年5月13日
 */
@SuppressWarnings("serial")
@Getter
@Setter
public class PageResult<E> extends BaseEntity {

    public final long total;
    public final List<E> data;
    private final List<?> footer;

    public PageResult() {
        this.total = 0;
        this.data = Collections.emptyList();
        this.footer = Collections.emptyList();
    }

    public PageResult(long total, List<E> list) {
        this.total = total;
        this.data = list;
        this.footer = Collections.emptyList();
    }

    public PageResult(long total, List<E> list, List<?> footer) {
        this.total = total;
        this.data = list;
        this.footer = footer;
    }
}
