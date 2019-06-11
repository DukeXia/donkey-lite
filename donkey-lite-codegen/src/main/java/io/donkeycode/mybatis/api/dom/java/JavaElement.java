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

import io.donkeycode.mybatis.api.dom.OutputUtilities;
import lombok.Getter;
import lombok.Setter;

/**
 * The Class JavaElement.
 *
 * @author Jeff Butler
 */
@Setter
@Getter
public abstract class JavaElement {

	/** The java doc lines. */
	private List<String> javaDocLines;

	/** The visibility. */
	private JavaVisibility visibility = JavaVisibility.DEFAULT;

	/** The is static. */
	private boolean isStatic;

	/** The is final. */
	private boolean isFinal;

	/** The annotations. */
	private List<String> annotations;

	/**
	 * Instantiates a new java element.
	 */
	public JavaElement() {
		super();
		javaDocLines = new ArrayList<String>();
		annotations = new ArrayList<String>();
	}

	/**
	 * Copy Constructor.
	 *
	 * @param original
	 *            the original
	 */
	public JavaElement(JavaElement original) {
		this();
		this.annotations.addAll(original.annotations);
		this.isFinal = original.isFinal;
		this.isStatic = original.isStatic;
		this.javaDocLines.addAll(original.javaDocLines);
		this.visibility = original.visibility;
	}

	/**
	 * Adds the java doc line.
	 *
	 * @param javaDocLine
	 *            the java doc line
	 */
	public void addJavaDocLine(String javaDocLine) {
		javaDocLines.add(javaDocLine);
	}

	/**
	 * Adds the annotation.
	 *
	 * @param annotation
	 *            the annotation
	 */
	public void addAnnotation(String annotation) {
		annotations.add(annotation);
	}

	/**
	 * Adds the suppress type warnings annotation.
	 */
	public void addSuppressTypeWarningsAnnotation() {
		addAnnotation("@SuppressWarnings(\"unchecked\")");
	}

	/**
	 * Adds the formatted javadoc.
	 *
	 * @param sb
	 *            the sb
	 * @param indentLevel
	 *            the indent level
	 */
	public void addFormattedJavadoc(StringBuilder sb, int indentLevel) {
		for (String javaDocLine : javaDocLines) {
			OutputUtilities.javaIndent(sb, indentLevel);
			sb.append(javaDocLine);
			OutputUtilities.newLine(sb);
		}
	}

	/**
	 * Adds the formatted annotations.
	 *
	 * @param sb
	 *            the sb
	 * @param indentLevel
	 *            the indent level
	 */
	public void addFormattedAnnotations(StringBuilder sb, int indentLevel) {
		for (String annotation : annotations) {
			OutputUtilities.javaIndent(sb, indentLevel);
			sb.append(annotation);
			OutputUtilities.newLine(sb);
		}
	}
}
