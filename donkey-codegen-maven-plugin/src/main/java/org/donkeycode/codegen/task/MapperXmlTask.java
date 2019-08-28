package org.donkeycode.codegen.task;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.donkeycode.codegen.ContextConfiguration;
import org.donkeycode.codegen.entity.ColumnField;
import org.donkeycode.codegen.entity.TableClass;
import org.donkeycode.codegen.task.base.AbstractTask;
import org.donkeycode.codegen.utils.ConfigUtil;
import org.donkeycode.codegen.utils.FileUtil;
import org.donkeycode.codegen.utils.FreemarketConfigUtils;
import org.donkeycode.codegen.utils.StringUtil;

import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author nanfeng
 *
 */
@Slf4j
public class MapperXmlTask extends AbstractTask {

	public MapperXmlTask(TableClass tableClass) {
		super(tableClass);
	}

	public MapperXmlTask(TableClass tableClass, List<ColumnField> columnFields) {
		super(tableClass, columnFields);
	}

	@Override
	public void run() throws IOException, TemplateException {
		ContextConfiguration.DataGenerator data = ConfigUtil.getConfiguration().getDataGenerator();
		log.info("Generating " + tableClass.getShortClassName() + "Mapper.xml");
		Map<String, Object> mapperData = new HashMap<>();
		mapperData.put("tableClass", tableClass);
		mapperData.put("mapperPackage", data.getMapperPackage());
		String filePath = FileUtil.getResourcePath() + StringUtil.package2Path(data.getMapperPackage());
		String fileName = tableClass.getShortClassName() + "Mapper.xml";
		FileUtil.generateToJava(FreemarketConfigUtils.TYPE_MAPPER, mapperData, filePath + fileName);
	}
}
