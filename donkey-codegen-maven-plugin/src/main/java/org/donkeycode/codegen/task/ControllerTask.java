package org.donkeycode.codegen.task;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.donkeycode.codegen.ContextConfiguration;
import org.donkeycode.codegen.entity.TableClass;
import org.donkeycode.codegen.task.base.AbstractTask;
import org.donkeycode.codegen.utils.FileUtil;
import org.donkeycode.codegen.utils.FreemarketConfigUtils;
import org.donkeycode.codegen.utils.StringUtil;

import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author nanfeng
 * @data 2019年8月29日
 */
@Slf4j
public class ControllerTask extends AbstractTask {

    public ControllerTask(ContextConfiguration configuration, TableClass tableClass) {
        super(configuration, tableClass);
    }

    @Override
    public void run() throws IOException, TemplateException {
        ContextConfiguration.ControllerGenerator controller = configuration.getControllerGenerator();
        ContextConfiguration.ServiceGenerator service = configuration.getServiceGenerator();
        log.info("Generating " + tableClass.getShortClassName() + "Controller.java");
        Map<String, Object> controllerData = new HashMap<>();
        controllerData.put("tableClass", tableClass);
        controllerData.put("targetPackage", controller.getTargetPackage());
        controllerData.put("servicePackage", service.getTargetPackage());
        controllerData.put("author", configuration.getAuthor());
        controllerData.put("date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        String filePath = configuration.getSourcePath() + StringUtil.package2Path(controller.getTargetProject()) + StringUtil.package2Path(controller.getTargetPackage());
        String fileName = tableClass.getShortClassName() + "Controller.java";
        FileUtil.generateToJava(FreemarketConfigUtils.TYPE_CONTROLLER, controllerData, filePath + fileName);
    }
}
