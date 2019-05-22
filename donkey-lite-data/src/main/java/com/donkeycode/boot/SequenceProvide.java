package com.donkeycode.boot;

import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xuanner.seq.range.impl.db.DbSeqRangeMgr;
import com.xuanner.seq.sequence.impl.DefaultRangeSequence;

/**
 * 基于DB方式生成业务编号
 *
 * @author yanjun.xue
 * @since 2019年5月13日
 */
@Component
public class SequenceProvide {

    @Autowired
    DataSource dataSource;
    //构建序列号生成器
    DefaultRangeSequence defaultRangeSequence;
    DbSeqRangeMgr dbSeqRangeMgr;

    /**
     * 初始化业务编号生成规则
     *
     */
    @PostConstruct
    public void initSequence() {
        //利用DB获取区间管理器
        dbSeqRangeMgr = new DbSeqRangeMgr();
        dbSeqRangeMgr.setDataSource(dataSource);
        dbSeqRangeMgr.setTableName("sequence");
        dbSeqRangeMgr.setRetryTimes(100);
        dbSeqRangeMgr.setStep(100);
        dbSeqRangeMgr.setStepStart(1);
        dbSeqRangeMgr.init();
        //构建序列号生成器
        defaultRangeSequence = new DefaultRangeSequence();
    }

    /**
     * 根据业务类型计算从编号
     *
     * @param type
     * @return
     */
    public String getSequenceNoByType(String type) {
        Objects.requireNonNull(type);
        //构建序列号生成器
        defaultRangeSequence.setName(type);
        defaultRangeSequence.setSeqRangeMgr(dbSeqRangeMgr);
        com.xuanner.seq.sequence.Sequence userSeq = defaultRangeSequence;
        return type + String.format("%010d", userSeq.nextValue());
    }
}
