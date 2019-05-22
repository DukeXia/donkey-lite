package com.donkeycode.core.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.reflect.FieldUtils;

import com.donkeycode.core.collection.CollectionUtils;

import net.sf.cglib.beans.BeanMap;

public class StructureConvertUtils {

	/**
	 * 封装树
	 *
	 * @param pNodes       要封装为树的父对象集合
	 * @param originaNodes 原始list数据
	 * @param keyName      作为唯一标示的字段名称
	 * @param pFieldName   模型中作为parent字段名称
	 * @param subFieldName 模型中作为children的字段名称
	 */
	public static <T> void buildTree(List<T> pNodes, List<T> originaNodes, String keyName, String pFieldName, String subFieldName) {

		CollectionUtils.forEach(pNodes, (index, node) -> {
			try {
				List<T> subNodes = getChildren(pNodes.get(index), originaNodes, keyName, pFieldName, subFieldName);
				if (!subNodes.isEmpty()) {
					originaNodes.removeAll(subNodes);
					buildTree(subNodes, originaNodes, keyName, pFieldName, subFieldName);
				}
			} catch (IllegalAccessException e) {
				throw new RuntimeException("");
			} catch (NoSuchMethodException e) {
				throw new RuntimeException("");
			} catch (InvocationTargetException e) {
				throw new RuntimeException("");
			}
		});
	}

	/**
	 * 封装子对象
	 *
	 * @param pNode        父对象
	 * @param originals    待处理对象集合
	 * @param keyName      作为唯一标示的字段名称
	 * @param pFieldName   模型中作为parent字段名称
	 * @param subFieldName 模型中作为children的字段名称
	 */
	private static <T> List<T> getChildren(T pNode, List<T> originals, String keyName, String pFieldName, String subFieldName) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {

		List<T> nodes = new ArrayList<>();
		String pIdVal = BeanMap.create(pNode).get(keyName).toString();
		for (T node : originals) {
			String subPId = BeanMap.create(node).get(pFieldName).toString();
			if (pIdVal.equals(subPId)) {
				nodes.add(node);
			}
		}
		if (!nodes.isEmpty()) {
			FieldUtils.writeDeclaredField(pNode, subFieldName, nodes, true);
		}
		return nodes;
	}
}
