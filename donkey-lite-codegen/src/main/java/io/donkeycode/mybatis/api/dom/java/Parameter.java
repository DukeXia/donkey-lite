/**
 * Copyright 2006-2017 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.donkeycode.mybatis.api.dom.java;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Parameter {
	private String name;
	private FullyQualifiedJavaType type;
	private boolean isVarargs;

	private List<String> annotations;

	public Parameter(FullyQualifiedJavaType type, String name, boolean isVarargs) {
		super();
		this.name = name;
		this.type = type;
		this.isVarargs = isVarargs;
		annotations = new ArrayList<String>();
	}

	public Parameter(FullyQualifiedJavaType type, String name) {
		this(type, name, false);
	}

	public Parameter(FullyQualifiedJavaType type, String name, String annotation) {
		this(type, name, false);
		addAnnotation(annotation);
	}

	public Parameter(FullyQualifiedJavaType type, String name, String annotation, boolean isVarargs) {
		this(type, name, isVarargs);
		addAnnotation(annotation);
	}

	public void addAnnotation(String annotation) {
		annotations.add(annotation);
	}

	public String getFormattedContent(CompilationUnit compilationUnit) {
		StringBuilder sb = new StringBuilder();

		for (String annotation : annotations) {
			sb.append(annotation);
			sb.append(' ');
		}
		sb.append(JavaDomUtils.calculateTypeName(compilationUnit, type));
		sb.append(' ');
		if (isVarargs) {
			sb.append("... "); //$NON-NLS-1$
		}
		sb.append(name);
		return sb.toString();
	}

}
