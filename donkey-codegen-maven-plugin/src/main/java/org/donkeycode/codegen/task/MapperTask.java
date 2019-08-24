package org.donkeycode.codegen.task;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.donkeycode.codegen.ContextConfiguration.Path;
import org.donkeycode.codegen.entity.ColumnField;
import org.donkeycode.codegen.entity.TableClass;
import org.donkeycode.codegen.task.base.AbstractTask;
import org.donkeycode.codegen.utils.ConfigUtil;
import org.donkeycode.codegen.utils.FileUtil;
import org.donkeycode.codegen.utils.FreemarketConfigUtils;
import org.donkeycode.codegen.utils.GeneratorUtil;
import org.donkeycode.codegen.utils.StringUtil;

import freemarker.template.TemplateException;

/**
 * Author GreedyStar Date 2018/4/20
 */
public class MapperTask extends AbstractTask {

	public MapperTask(TableClass tableClass) {
		super(tableClass);
	}

	public MapperTask(TableClass tableClass, List<ColumnField> columnFields) {
		super(tableClass, columnFields);
	}

	@Override
	public void run() throws IOException, TemplateException {
		// 生成Mapper填充数据
		Path path = ConfigUtil.getConfiguration().getPath();

		System.out.println("Generating " + className + "Mapper.xml");
		Map<String, String> mapperData = new HashMap<>();
		mapperData.put("PackageName", ConfigUtil.getConfiguration().getPackageName() + "." + path.getDao());
		mapperData.put("BasePackageName", ConfigUtil.getConfiguration().getPackageName());
		mapperData.put("DaoPackageName", path.getDao());
		mapperData.put("EntityPackageName", path.getEntity());
		mapperData.put("ClassName", className);
		mapperData.put("EntityName", StringUtil.firstToLowerCase(className));
		mapperData.put("TableName", tableName);
		mapperData.put("InsertProperties", GeneratorUtil.generateMapperInsertProperties(columnFields));
		mapperData.put("PrimaryKey", getPrimaryKeyColumnInfo(columnFields).getColumnName());
		mapperData.put("WhereId", "#{" + getPrimaryKeyColumnInfo(columnFields).getPropertyName() + "}");
		mapperData.put("Id", "#{id}");

		mapperData.put("ColumnMap", GeneratorUtil.generateMapperColumnMap(tableName, columnFields));
		mapperData.put("ResultMap", GeneratorUtil.generateMapperResultMap(columnFields));
		mapperData.put("Association", "");
		mapperData.put("Collection", "");
		mapperData.put("InsertBatchValues", GeneratorUtil.generateMapperInsertBatchValues(columnFields, StringUtil.firstToLowerCase(className)));
		mapperData.put("InsertValues", GeneratorUtil.generateMapperInsertValues(columnFields));
		mapperData.put("UpdateProperties", GeneratorUtil.generateMapperUpdateProperties(columnFields));
		mapperData.put("Joins", "");
		String filePath = FileUtil.getResourcePath() + StringUtil.package2Path(path.getMapper());
		String fileName = className + "Mapper.xml";
		// 生成Mapper文件
		FileUtil.generateToJava(FreemarketConfigUtils.TYPE_MAPPER, mapperData, filePath + fileName);
	}

	private ColumnField getPrimaryKeyColumnInfo(List<ColumnField> list) {
		for (ColumnField columnField : list) {
			if (columnField.isPrimaryKey()) {
				return columnField;
			}
		}
		return null;
	}

}
