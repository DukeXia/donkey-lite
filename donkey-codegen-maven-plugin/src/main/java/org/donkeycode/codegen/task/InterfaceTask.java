package org.donkeycode.codegen.task;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.donkeycode.codegen.ContextConfiguration.Path;
import org.donkeycode.codegen.entity.TableClass;
import org.donkeycode.codegen.task.base.AbstractTask;
import org.donkeycode.codegen.utils.ConfigUtil;
import org.donkeycode.codegen.utils.FileUtil;
import org.donkeycode.codegen.utils.FreemarketConfigUtils;
import org.donkeycode.codegen.utils.StringUtil;

import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;

/**
 * Author GreedyStar
 * Date   2019/1/24
 */
@Slf4j
public class InterfaceTask extends AbstractTask {

    public InterfaceTask(TableClass tableClass) {
		super(tableClass);
	}

    @Override
    public void run() throws IOException, TemplateException {
    	Path path = ConfigUtil.getConfiguration().getPath();
        // 生成Service接口填充数据
        log.info("Generating " + className + "Service.java");
        Map<String, String> interfaceData = new HashMap<>();
        interfaceData.put("BasePackageName", ConfigUtil.getConfiguration().getPackageName());
        interfaceData.put("InterfacePackageName", path.getInterf());
        interfaceData.put("EntityPackageName", path.getEntity());
        interfaceData.put("Author", ConfigUtil.getConfiguration().getAuthor());
        interfaceData.put("Date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        interfaceData.put("ClassName", className);
        interfaceData.put("EntityName", StringUtil.firstToLowerCase(className));
        String filePath = FileUtil.getSourcePath() + StringUtil.package2Path(ConfigUtil.getConfiguration().getPackageName()) + StringUtil.package2Path(ConfigUtil.getConfiguration().getPath().getInterf());
        String fileName = className + "Service.java";
        // 生成Service接口文件
        FileUtil.generateToJava(FreemarketConfigUtils.TYPE_INTERFACE, interfaceData, filePath + fileName);
    }
}
