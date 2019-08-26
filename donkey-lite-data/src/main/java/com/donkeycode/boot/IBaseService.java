package com.donkeycode.boot;

import java.util.List;

import com.donkeycode.core.page.ListFilter;
import com.donkeycode.core.page.PageFilter;
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
	int deleteByKey(Object id);

	/**
	 * 通过多个主键删除PO
	 *
	 * @param ids
	 * @return
	 */
	int deleteByKeys(List<?> ids);

	/**
	 * 通过主键更新有值的字段
	 *
	 * @param entity
	 * @return
	 */
	int updateByKeySelective(T entity);

	/**
	 * 通过主键更新所有字段
	 *
	 * @param entity
	 * @return
	 */
	int updateByKey(T entity);

	/**
	 * 通过多个主键获取PO
	 *
	 * @param ids
	 * @return
	 */
	List<T> selectByKeys(List<String> ids);

	/**
	 * 通过主键获取PO
	 *
	 * @param id
	 * @return
	 */
	T selectByKey(Object id);

	/**
	 * @param entity
	 * @return
	 */
	T selectOne(T entity);

	/**
	 * @param entity
	 * @return
	 */
	List<T> selectList(T entity);

	/**
	 * @param userId
	 * @param listFilter
	 * @return
	 */
	List<T> selectList(String userId, ListFilter listFilter);

	/**
	 * 资源分页查找
	 *
	 * @param pageFilter
	 * @return
	 */
	default PageInfo<T> selectPage(PageFilter pageFilter) {
		return selectPage(null, pageFilter);
	}

	/**
	 * 资源分页查找
	 *
	 * @param userId
	 *            用户Id
	 * @param pageFilter
	 *            分页过滤参数
	 * @return
	 */
	PageInfo<T> selectPage(String userId, PageFilter pageFilter);

}