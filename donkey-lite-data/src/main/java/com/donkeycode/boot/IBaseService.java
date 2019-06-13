package com.donkeycode.boot;

import java.util.List;
import java.util.Map;

import com.donkeycode.core.page.PageFilter;
import com.donkeycode.core.page.PageResult;
import com.github.pagehelper.PageInfo;

/**
 * BaseService 通用接口定义
 *
 * @param <T>
 * @author donkey
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

    /**
     * 批量插入多个PO
     *
     * @param entitys
     * @return
     */
    int insertList(List<T> entitys);

    /**
     * 通过主键物理删除
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Object id);

    /**
     * 通过多个主键删除PO
     *
     * @param ids
     * @return
     */
    int deleteByPrimaryKeys(List<?> ids);

    /**
     * 通过多个主键获取PO
     *
     * @param ids
     * @return
     */
    List<T> selectByPrimaryKeys(List<String> ids);

    /**
     * 通过主键获取PO
     *
     * @param id
     * @return
     */
    T selectByPrimaryKey(Object id);

    /**
     * 通过主键更新有值的字段
     *
     * @param entity
     * @return
     */
    int updateByPrimaryKeySelective(T entity);

    /**
     * 通过主键更新所有字段
     *
     * @param entity
     * @return
     */
    int updateByPrimaryKey(T entity);

    /**
     * 通过example对象查询
     *
     * @param query
     * @return
     */
    PageResult<T> selectPageByExample(PageFilter query);

    /**
     * 通过sql查询数据
     *
     * @param sql
     * @param query
     * @return
     */
    PageResult<Map<String, Object>> selectPageBySQL(String sql, PageFilter query);
    
    /**
     * 
     * @param param
     * @return
     */
    List<T> getList(Map<String, String> param);

    /**
     * 
     * @param pageFilter
     * @return
     */
    PageInfo<T> getPageList(PageFilter pageFilter);
}