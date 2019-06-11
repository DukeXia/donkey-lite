package com.donkeycode.boot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.donkeycode.boot.utils.BaseMapper;
import com.donkeycode.core.page.PageFilter;
import com.donkeycode.core.page.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

/**
 * @param <T>
 * @author donkey
 * @since 2019年5月13日
 */
@Slf4j
@Service
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
        return mapper.insertList(entitys);
    }

    @Override
    public int deleteByPrimaryKey(Object id) {
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteByPrimaryKeys(List<?> ids) {
        String joinId = ids.stream().map(id -> id.toString()).collect(Collectors.joining(","));
        log.debug("delete resouce ids:{}", joinId);
        return mapper.deleteByIds(joinId);
    }

    @Override
    public List<T> selectByPrimaryKeys(List<String> ids) {
        return mapper.selectByIds(ids.stream().collect(Collectors.joining(",")));
    }

    @Override
    public T selectByPrimaryKey(Object id) {
        return mapper.selectByPrimaryKey(id);
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
    public PageResult<T> selectPageByExample(PageFilter query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize(), query.getOrderBy());
        Example example = new Example(this.entityClass);
        List<T> list = mapper.selectByExample(example);
        PageInfo<T> pageInfo = new PageInfo<T>(list);
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public PageResult<Map<String, Object>> selectPageBySQL(String sql, PageFilter query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize(), query.getOrderBy());
        List<Map<String, Object>> list = sqlmapper.selectList(sql);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(list);
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
    }
}
