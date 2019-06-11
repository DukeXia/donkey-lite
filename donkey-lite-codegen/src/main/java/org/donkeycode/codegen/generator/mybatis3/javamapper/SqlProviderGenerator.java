package org.donkeycode.codegen.generator.mybatis3.javamapper;

import static org.donkeycode.codegen.internal.util.messages.Messages.getString;

import java.util.ArrayList;
import java.util.List;

import org.donkeycode.codegen.api.CommentGenerator;
import org.donkeycode.codegen.api.dom.java.CompilationUnit;
import org.donkeycode.codegen.api.dom.java.FullyQualifiedJavaType;
import org.donkeycode.codegen.api.dom.java.JavaVisibility;
import org.donkeycode.codegen.api.dom.java.TopLevelClass;
import org.donkeycode.codegen.generator.AbstractJavaGenerator;

/**
 *
 * @author Jeff Butler
 *
 */
public class SqlProviderGenerator extends AbstractJavaGenerator {

	public SqlProviderGenerator(boolean useLegacyBuilder) {
		super();
	}

	@Override
	public List<CompilationUnit> getCompilationUnits() {
		progressCallback.startTask(getString("Progress.18", introspectedTable.getFullyQualifiedTable().toString()));
		CommentGenerator commentGenerator = context.getCommentGenerator();

		FullyQualifiedJavaType type = new FullyQualifiedJavaType(introspectedTable.getMyBatis3SqlProviderType());
		TopLevelClass topLevelClass = new TopLevelClass(type);
		topLevelClass.setVisibility(JavaVisibility.PUBLIC);
		commentGenerator.addJavaFileComment(topLevelClass);

		List<CompilationUnit> answer = new ArrayList<CompilationUnit>();

		if (topLevelClass.getMethods().size() > 0 && context.getPlugins().providerGenerated(topLevelClass, introspectedTable)) {
			answer.add(topLevelClass);
		}

		return answer;
	}

}
