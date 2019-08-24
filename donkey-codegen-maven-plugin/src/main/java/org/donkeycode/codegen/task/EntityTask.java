package org.donkeycode.codegen.task;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
 * Author GreedyStar
 * Date   2018/4/20
 */
public class EntityTask extends AbstractTask {

	public EntityTask(TableClass tableClass, List<ColumnField> columnFields) {
		super(tableClass, columnFields);
	}

	public EntityTask(TableClass tableClass) {
		super(tableClass);
	}

	@Override
	public void run() throws IOException, TemplateException {

		Path path = ConfigUtil.getConfiguration().getPath();
		// 生成Entity填充数据
		System.out.println("Generating " + className + ".java");
		Map<String, String> entityData = new HashMap<>();
		entityData.put("BasePackageName", ConfigUtil.getConfiguration().getPackageName());
		entityData.put("EntityPackageName", path.getEntity());
		entityData.put("Author", ConfigUtil.getConfiguration().getAuthor());
		entityData.put("Date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		entityData.put("ClassName", className);
		// 单表关系
		entityData.put("Properties", GeneratorUtil.generateEntityProperties(columnFields));
		entityData.put("Methods", GeneratorUtil.generateEntityMethods(columnFields));

		String filePath = FileUtil.getSourcePath() + StringUtil.package2Path(ConfigUtil.getConfiguration().getPackageName()) + StringUtil.package2Path(ConfigUtil.getConfiguration().getPath().getEntity());
		String fileName = className + ".java";
		// 生成Entity文件
		FileUtil.generateToJava(FreemarketConfigUtils.TYPE_ENTITY, entityData, filePath + fileName);
	}
}
