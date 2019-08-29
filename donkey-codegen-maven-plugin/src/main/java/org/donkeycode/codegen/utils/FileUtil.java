package org.donkeycode.codegen.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @author nanfeng
 * @data 2019年8月29日
 */
public class FileUtil {

	/**
	 * @param type     使用模板类型
	 * @param data     填充数据
	 * @param filePath 输出文件
	 * @throws IOException
	 * @throws TemplateException
	 */
	public static void generateToJava(int type, Object data, String filePath) throws IOException, TemplateException {
		File file = new File(filePath);
		if (file.exists()) {
			System.err.println("ERROR: " + file.getPath().substring(file.getPath().lastIndexOf("\\") + 1, file.getPath().length()) + " 已存在，请手动修改");
			return;
		}
		File path = new File(file.getParent());
		if (!path.exists()) {
			path.mkdirs();
		}
		Template tpl = getTemplate(type); // 获取模板文件
		// 填充数据
		StringWriter writer = new StringWriter();
		tpl.process(data, writer);
		writer.flush();
		// 写入文件
		FileOutputStream fos = new FileOutputStream(filePath);
		OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
		BufferedWriter bw = new BufferedWriter(osw, 1024);
		tpl.process(data, bw);
		fos.close();
	}

	/**
	 * 获取模板
	 *
	 * @param type 模板类型
	 * @return
	 * @throws IOException
	 */
	private static Template getTemplate(int type) throws IOException {
		switch (type) {
		case FreemarketConfigUtils.TYPE_ENTITY:
			return FreemarketConfigUtils.getInstance().getTemplate("entity.ftl");
		case FreemarketConfigUtils.TYPE_DAO:
			return FreemarketConfigUtils.getInstance().getTemplate("mapper.ftl");
		case FreemarketConfigUtils.TYPE_SERVICE:
			return FreemarketConfigUtils.getInstance().getTemplate("service.ftl");
		case FreemarketConfigUtils.TYPE_CONTROLLER:
			return FreemarketConfigUtils.getInstance().getTemplate("controller.ftl");
		case FreemarketConfigUtils.TYPE_MAPPER:
			return FreemarketConfigUtils.getInstance().getTemplate("mapperXml.ftl");
		case FreemarketConfigUtils.TYPE_INTERFACE:
			return FreemarketConfigUtils.getInstance().getTemplate("interface.ftl");
		default:
			return null;
		}
	}

}
