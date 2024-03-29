package com.donkeycode.boot;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.donkeycode.boot.utils.BaseMapper;
import com.donkeycode.core.page.PageFilter;
import com.donkeycode.core.page.PageResult;
import com.donkeycode.core.utils.CollectionUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.Weekend;
import tk.mybatis.mapper.weekend.WeekendCriteria;

/**
 * 关系型数据库持久化
 *
 * @param <T>
 * @author donkey
 * @since 2019年5月13日
 */
@Slf4j
public abstract class BaseService<T> implements IBaseService<T> {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    protected BaseMapper<T> mapper;

    @Autowired
    protected SqlMapper sqlmapper;

    private Class<T> entityClass;

    protected BaseService() {
        entityClass = ReflectionUtils.getSuperClassGenricType(getClass());
    }

    @Override
    public int insert(T entity) {
        return mapper.insert(entity);
    }

    @Override
    public int insertSelective(T entity) {
        return mapper.insertSelective(entity);
    }

    @Override
    public int insertList(List<T> entitys) {
        if (CollectionUtils.isEmpty(entitys)) {
            return 0;
        }
        return mapper.insertList(entitys);
    }

    @Override
    public int deleteByPrimaryKey(Object id) {
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteByPrimaryKeys(List<?> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return 0;
        }
        String joinId = ids.stream().map(id -> id.toString()).collect(Collectors.joining(","));
        log.debug("delete resouce ids:{}", joinId);
        return mapper.deleteByIds(joinId);
    }

    @Override
    public int updateByPrimaryKeySelective(T entity) {
        return mapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public int updateByPrimaryKey(T entity) {
        return mapper.updateByPrimaryKey(entity);
    }

    @Override
    public List<T> selectByPrimaryKeys(List<String> ids) {
        return mapper.selectByIds(ids.stream().collect(Collectors.joining(",")));
    }

    @Override
    public T selectOne(T entity) {
        return mapper.selectOne(entity);
    }

    @Override
    public List<T> selectList(T entity) {
        return mapper.select(entity);
    }

    @Override
    public T selectOneByExample(Example example) {
        return mapper.selectOneByExample(example);
    }

    @Override
    public List<T> selectByExample(Example example) {
        return mapper.selectByExample(example);
    }

    @Override
    public PageResult<Map<String, Object>> selectPageBySQL(String sql, PageFilter pageFilter) {
        PageHelper.startPage(pageFilter.getPageNum(), pageFilter.getPageSize(), pageFilter.getOrder());
        List<Map<String, Object>> list = sqlmapper.selectList(sql);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(list);
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public T selectByPrimaryKey(Object id) {
        return mapper.selectByPrimaryKey(id);
    }

    /**
     * 子类重新此功能
     */
    @Override
    public List<T> selectList(Map<String, String> param) {
        Weekend<T> weekend = new Weekend<>(this.entityClass);
        WeekendCriteria<T, Object> criteria = weekend.weekendCriteria();
        if (CollectionUtils.isNotEmpty(param)) {

        }
        return selectList(weekend);
    }

    /**
     * 子类重新此功能
     */
    @Override
    public List<T> selectList(Weekend<T> weekend) {
        return mapper.selectByExample(weekend);
    }

    @Override
    public PageInfo<T> selectPage(PageFilter pageFilter) {
        PageHelper.startPage(pageFilter.getPageNum(), pageFilter.getPageSize(), pageFilter.getOrder());
        return new PageInfo<>(selectList(pageFilter.getParams()));
    }
}
