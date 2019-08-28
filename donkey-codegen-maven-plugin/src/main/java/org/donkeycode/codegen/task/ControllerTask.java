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
public class ControllerTask extends AbstractTask {

	public ControllerTask(TableClass tableClass) {
		super(tableClass);
	}

	@Override
	public void run() throws IOException, TemplateException {
		ContextConfiguration.ControllerGenerator controller = ConfigUtil.getConfiguration().getControllerGenerator();
		ContextConfiguration.ServiceGenerator service = ConfigUtil.getConfiguration().getServiceGenerator();
		log.info("Generating " + tableClass.getShortClassName() + "Controller.java");
		Map<String, Object> controllerData = new HashMap<>();
		controllerData.put("tableClass", tableClass);
		controllerData.put("targetPackage", controller.getTargetPackage());
		controllerData.put("servicePackage", service.getTargetPackage());
		controllerData.put("author", ConfigUtil.getConfiguration().getAuthor());
		controllerData.put("date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		String filePath = FileUtil.getSourcePath() + StringUtil.package2Path(controller.getTargetProject()) + StringUtil.package2Path(controller.getTargetPackage());
		String fileName = tableClass.getShortClassName() + "Controller.java";
		FileUtil.generateToJava(FreemarketConfigUtils.TYPE_CONTROLLER, controllerData, filePath + fileName);
	}
}
