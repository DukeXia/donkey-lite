package org.donkeycode.codegen.task;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.donkeycode.codegen.ContextConfiguration;
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
public class InterfaceTask extends AbstractTask {

	public InterfaceTask(TableClass tableClass) {
		super(tableClass);
	}

	@Override
	public void run() throws IOException, TemplateException {
		ContextConfiguration.ServiceGenerator serviceGenerator = ConfigUtil.getConfiguration().getServiceGenerator();
		log.info("Generating " + tableClass.getShortClassName() + "Service.java");
		Map<String, Object> interfaceData = new HashMap<>();
		interfaceData.put("tableClass", tableClass);
		interfaceData.put("targetPackage", serviceGenerator.getTargetPackage());
		interfaceData.put("author", ConfigUtil.getConfiguration().getAuthor());
		interfaceData.put("date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		String filePath = FileUtil.getSourcePath() + StringUtil.package2Path(serviceGenerator.getTargetProject()) + StringUtil.package2Path(serviceGenerator.getTargetPackage());
		String fileName = tableClass.getShortClassName() + "Service.java";
		FileUtil.generateToJava(FreemarketConfigUtils.TYPE_INTERFACE, interfaceData, filePath + fileName);
	}
}
