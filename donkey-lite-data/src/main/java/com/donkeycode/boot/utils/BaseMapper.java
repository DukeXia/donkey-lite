package com.donkeycode.boot.utils;

import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 通用Mapper
 *
 * @param <T>
 * @author donkey
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T>, IdsMapper<T> {

}