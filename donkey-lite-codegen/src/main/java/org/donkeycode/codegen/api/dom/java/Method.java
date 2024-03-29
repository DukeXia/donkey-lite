/**
 * Copyright 2006-2018 the original author or authors.
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
package org.donkeycode.codegen.api.dom.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

import org.donkeycode.codegen.api.dom.OutputUtilities;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class Method.
 *
 * @author Jeff Butler
 */
@Getter
@Setter
public class Method extends JavaElement {

	/** The body lines. */
	private List<String> bodyLines;

	/** The constructor. */
	private boolean constructor;

	/** The return type. */
	private FullyQualifiedJavaType returnType;

	/** The name. */
	private String name;

	/** The type parameters. */
	private List<TypeParameter> typeParameters;

	/** The parameters. */
	private List<Parameter> parameters;

	/** The exceptions. */
	private List<FullyQualifiedJavaType> exceptions;

	/** The is synchronized. */
	private boolean isSynchronized;

	/** The is native. */
	private boolean isNative;

	private boolean isDefault;

	/**
	 * Instantiates a new method.
	 */
	public Method() {
		// use a default name to avoid malformed code
		this("bar");
	}

	/**
	 * Instantiates a new method.
	 *
	 * @param name
	 *            the name
	 */
	public Method(String name) {
		super();
		bodyLines = new ArrayList<String>();
		typeParameters = new ArrayList<TypeParameter>();
		parameters = new ArrayList<Parameter>();
		exceptions = new ArrayList<FullyQualifiedJavaType>();
		this.name = name;
	}

	/**
	 * Copy constructor. Not a truly deep copy, but close enough for most purposes.
	 *
	 * @param original
	 *            the original
	 */
	public Method(Method original) {
		super(original);
		bodyLines = new ArrayList<String>();
		typeParameters = new ArrayList<TypeParameter>();
		parameters = new ArrayList<Parameter>();
		exceptions = new ArrayList<FullyQualifiedJavaType>();
		this.bodyLines.addAll(original.bodyLines);
		this.constructor = original.constructor;
		this.exceptions.addAll(original.exceptions);
		this.name = original.name;
		this.typeParameters.addAll(original.typeParameters);
		this.parameters.addAll(original.parameters);
		this.returnType = original.returnType;
		this.isNative = original.isNative;
		this.isSynchronized = original.isSynchronized;
		this.isDefault = original.isDefault;
	}

	/**
	 * Adds the body line.
	 *
	 * @param line
	 *            the line
	 */
	public void addBodyLine(String line) {
		bodyLines.add(line);
	}

	/**
	 * Adds the body line.
	 *
	 * @param index
	 *            the index
	 * @param line
	 *            the line
	 */
	public void addBodyLine(int index, String line) {
		bodyLines.add(index, line);
	}

	/**
	 * Adds the body lines.
	 *
	 * @param lines
	 *            the lines
	 */
	public void addBodyLines(Collection<String> lines) {
		bodyLines.addAll(lines);
	}

	/**
	 * Adds the body lines.
	 *
	 * @param index
	 *            the index
	 * @param lines
	 *            the lines
	 */
	public void addBodyLines(int index, Collection<String> lines) {
		bodyLines.addAll(index, lines);
	}

	/**
	 * Gets the formatted content.
	 *
	 * @param indentLevel
	 *            the indent level
	 * @param interfaceMethod
	 *            the interface method
	 * @param compilationUnit the compilation unit
	 * @return the formatted content
	 */
	public String getFormattedContent(int indentLevel, boolean interfaceMethod, CompilationUnit compilationUnit) {
		StringBuilder sb = new StringBuilder();

		addFormattedJavadoc(sb, indentLevel);
		addFormattedAnnotations(sb, indentLevel);

		OutputUtilities.javaIndent(sb, indentLevel);

		if (interfaceMethod) {
			if (isStatic()) {
				sb.append("static ");
			} else if (isDefault()) {
				sb.append("default ");
			}
		} else {
			sb.append(getVisibility().getValue());

			if (isStatic()) {
				sb.append("static ");
			}

			if (isFinal()) {
				sb.append("final ");
			}

			if (isSynchronized()) {
				sb.append("synchronized ");
			}

			if (isNative()) {
				sb.append("native ");
			} else if (bodyLines.size() == 0) {
				sb.append("abstract ");
			}
		}

		if (!getTypeParameters().isEmpty()) {
			sb.append("<");
			boolean comma = false;
			for (TypeParameter typeParameter : getTypeParameters()) {
				if (comma) {
					sb.append(", ");
				} else {
					comma = true;
				}

				sb.append(typeParameter.getFormattedContent(compilationUnit));
			}
			sb.append("> ");
		}

		if (!constructor) {
			if (getReturnType() == null) {
				sb.append("void");
			} else {
				sb.append(JavaDomUtils.calculateTypeName(compilationUnit, getReturnType()));
			}
			sb.append(' ');
		}

		sb.append(getName());
		sb.append('(');

		boolean comma = false;
		for (Parameter parameter : getParameters()) {
			if (comma) {
				sb.append(", ");
			} else {
				comma = true;
			}

			sb.append(parameter.getFormattedContent(compilationUnit));
		}

		sb.append(')');

		if (getExceptions().size() > 0) {
			sb.append(" throws ");
			comma = false;
			for (FullyQualifiedJavaType fqjt : getExceptions()) {
				if (comma) {
					sb.append(", ");
				} else {
					comma = true;
				}

				sb.append(JavaDomUtils.calculateTypeName(compilationUnit, fqjt));
			}
		}

		// if no body lines, then this is an abstract method
		if (bodyLines.size() == 0 || isNative()) {
			sb.append(';');
		} else {
			sb.append(" {");
			indentLevel++;

			ListIterator<String> listIter = bodyLines.listIterator();
			while (listIter.hasNext()) {
				String line = listIter.next();
				if (line.startsWith("}")) {
					indentLevel--;
				}

				OutputUtilities.newLine(sb);
				OutputUtilities.javaIndent(sb, indentLevel);
				sb.append(line);

				if ((line.endsWith("{") && !line.startsWith("switch"))
						|| line.endsWith(":")) {
					indentLevel++;
				}

				if (line.startsWith("break")) {
					// if the next line is '}', then don't outdent
					if (listIter.hasNext()) {
						String nextLine = listIter.next();
						if (nextLine.startsWith("}")) {
							indentLevel++;
						}

						// set back to the previous element
						listIter.previous();
					}
					indentLevel--;
				}
			}

			indentLevel--;
			OutputUtilities.newLine(sb);
			OutputUtilities.javaIndent(sb, indentLevel);
			sb.append('}');
		}

		return sb.toString();
	}

	/**
	 * Adds the type parameter.
	 *
	 * @param typeParameter
	 *            the type parameter
	 */
	public void addTypeParameter(TypeParameter typeParameter) {
		typeParameters.add(typeParameter);
	}

	/**
	 * Adds the parameter.
	 *
	 * @param index
	 *            the index
	 * @param typeParameter
	 *            the type parameter
	 */
	public void addTypeParameter(int index, TypeParameter typeParameter) {
		typeParameters.add(index, typeParameter);
	}

	/**
	 * Adds the parameter.
	 *
	 * @param parameter
	 *            the parameter
	 */
	public void addParameter(Parameter parameter) {
		parameters.add(parameter);
	}

	/**
	 * Adds the parameter.
	 *
	 * @param index
	 *            the index
	 * @param parameter
	 *            the parameter
	 */
	public void addParameter(int index, Parameter parameter) {
		parameters.add(index, parameter);
	}

	/**
	 * Adds the exception.
	 *
	 * @param exception
	 *            the exception
	 */
	public void addException(FullyQualifiedJavaType exception) {
		exceptions.add(exception);
	}

}
