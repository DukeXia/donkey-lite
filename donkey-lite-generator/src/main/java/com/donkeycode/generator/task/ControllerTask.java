package com.donkeycode.generator.task;

import freemarker.template.TemplateException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.donkeycode.generator.task.base.AbstractTask;
import com.donkeycode.generator.utils.ConfigUtil;
import com.donkeycode.generator.utils.FileUtil;
import com.donkeycode.generator.utils.FreemarketConfigUtils;
import com.donkeycode.generator.utils.StringUtil;

/**
 * Author GreedyStar
 * Date   2018/4/20
 */
public class ControllerTask extends AbstractTask {

    public ControllerTask(String className) {
        super(className);
    }

    @Override
    public void run() throws IOException, TemplateException {
        // 生成Controller填充数据
        System.out.println("Generating " + className + "Controller.java");
        Map<String, String> controllerData = new HashMap<>();
        controllerData.put("BasePackageName", ConfigUtil.getConfiguration().getPackageName());
        controllerData.put("ControllerPackageName", ConfigUtil.getConfiguration().getPath().getController());
        if (StringUtil.isBlank(ConfigUtil.getConfiguration().getPath().getInterf())) {
            controllerData.put("ServicePackageName", ConfigUtil.getConfiguration().getPath().getService());
        } else {
            controllerData.put("ServicePackageName", ConfigUtil.getConfiguration().getPath().getInterf());
        }
        controllerData.put("EntityPackageName", ConfigUtil.getConfiguration().getPath().getEntity());
        controllerData.put("Author", ConfigUtil.getConfiguration().getAuthor());
        controllerData.put("Date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        controllerData.put("ClassName", className);
        controllerData.put("EntityName", StringUtil.firstToLowerCase(className));
        String filePath = FileUtil.getSourcePath() + StringUtil.package2Path(ConfigUtil.getConfiguration().getPackageName()) + StringUtil.package2Path(ConfigUtil.getConfiguration().getPath().getController());
        String fileName = className + "Controller.java";
        // 生成Controller文件
        FileUtil.generateToJava(FreemarketConfigUtils.TYPE_CONTROLLER, controllerData, filePath + fileName);
    }
}