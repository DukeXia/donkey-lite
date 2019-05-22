package com.donkeycode.boot;

import java.util.List;
import java.util.Map;

import com.donkeycode.core.page.PageFilter;
import com.donkeycode.core.page.PageResult;

/**
 * BaseService 通用接口定义
 *
 * @param <T>
 * @author yanjun.xue
 * @since 2019年5月13日
 */
public interface IBaseService<T> {

    /**
     * 新增 Entit 到DB，会替换数据默认值
     *
     * @param entity
     * @return
     */
    int insert(T entity);

    /**
     * 新增 Entit 到DB，不会替换数据默认值
     *
     * @param entity
     * @return
     */
    int insertSelective(T entity);

    int insertList(List<T> entitys);

    /**
     * 通过主键物理删除
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Object id);

    int deleteByPrimaryKeys(List<?> ids);

    List<T> selectByPrimaryKeys(List<String> ids);

    T selectByPrimaryKey(Object id);

    int updateByPrimaryKeySelective(T entity);

    int updateByPrimaryKey(T entity);

    PageResult<T> selectPageByExample(PageFilter query);

    PageResult<Map<String, Object>> selectPageBySQL(String sql, PageFilter query);
}