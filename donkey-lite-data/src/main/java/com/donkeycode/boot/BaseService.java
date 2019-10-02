package com.donkeycode.boot;

import com.donkeycode.boot.utils.BaseMapper;
import com.donkeycode.core.collectors.CollectorUtils;
import com.donkeycode.core.page.ListFilter;
import com.donkeycode.core.page.PageFilter;
import com.donkeycode.core.utils.ReflectionUtils;
import com.donkeycode.core.utils.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import tk.mybatis.mapper.weekend.Weekend;

import java.util.List;
import java.util.stream.Collectors;

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
        if (CollectorUtils.isEmpty(entitys)) {
            return 0;
        }
        return mapper.insertList(entitys);
    }

    @Override
    public int deleteByKey(Object id) {
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteByKeys(List<?> ids) {
        if (CollectorUtils.isEmpty(ids)) {
            return 0;
        }
        String joinId = ids.stream().map(id -> id.toString()).collect(Collectors.joining(","));
        log.debug("delete resouce ids:{}", joinId);
        return mapper.deleteByIds(joinId);
    }

    @Override
    public int updateByKeySelective(T entity) {
        return mapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public int updateByKey(T entity) {
        return mapper.updateByPrimaryKey(entity);
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
    public List<T> selectAllList() {
        return mapper.selectAll();
    }

    @Override
    public T selectByKey(Object id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public List<T> selectByKeys(List<String> ids) {
        return mapper.selectByIds(CollectorUtils.join(ids, ","));
    }


    @Override
    public List<T> selectList(String userId, ListFilter listFilter) {
        Weekend<T> weekend = new Weekend<>(this.entityClass);
        if (CollectorUtils.isNotEmpty(listFilter.getParams())) {

        }
        if (StringUtils.isNoneBlank(listFilter.getOrderBy()) && listFilter.isOrderBy()) {
            weekend.setOrderByClause(listFilter.getOrderBy());
        }
        return mapper.selectByExample(weekend);
    }

    @Override
    public PageInfo<T> selectPage(String userId, PageFilter pageFilter) {
        PageHelper.startPage(pageFilter.getPageNo(), pageFilter.getPageSize(), pageFilter.getOrderBy());
        return new PageInfo<>(selectList(userId, pageFilter.listFilter()));
    }
}
