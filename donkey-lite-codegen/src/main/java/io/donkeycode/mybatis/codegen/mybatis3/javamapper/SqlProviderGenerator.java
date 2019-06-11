package io.donkeycode.mybatis.codegen.mybatis3.javamapper;

import static io.donkeycode.mybatis.internal.util.messages.Messages.getString;

import java.util.ArrayList;
import java.util.List;

import io.donkeycode.mybatis.api.CommentGenerator;
import io.donkeycode.mybatis.api.dom.java.CompilationUnit;
import io.donkeycode.mybatis.api.dom.java.FullyQualifiedJavaType;
import io.donkeycode.mybatis.api.dom.java.JavaVisibility;
import io.donkeycode.mybatis.api.dom.java.TopLevelClass;
import io.donkeycode.mybatis.codegen.AbstractJavaGenerator;

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
