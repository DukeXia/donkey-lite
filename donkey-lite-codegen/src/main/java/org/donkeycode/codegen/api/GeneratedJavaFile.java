package org.donkeycode.codegen.api;

import org.donkeycode.codegen.api.dom.java.CompilationUnit;

/**
 * The Class GeneratedJavaFile.
 *
 * @author Jeff Butler
 */
public class GeneratedJavaFile extends GeneratedFile {

	/** The compilation unit. */
	private CompilationUnit compilationUnit;

	/** The file encoding. */
	private String fileEncoding;

	/** The java formatter. */
	private JavaFormatter javaFormatter;

	/**
	 * Default constructor.
	 *
	 * @param compilationUnit
	 *            the compilation unit
	 * @param targetProject
	 *            the target project
	 * @param fileEncoding
	 *            the file encoding
	 * @param javaFormatter
	 *            the java formatter
	 */
	public GeneratedJavaFile(CompilationUnit compilationUnit, String targetProject, String fileEncoding, JavaFormatter javaFormatter) {
		super(targetProject);
		this.compilationUnit = compilationUnit;
		this.fileEncoding = fileEncoding;
		this.javaFormatter = javaFormatter;
	}

	/**
	 * Instantiates a new generated java file.
	 *
	 * @param compilationUnit
	 *            the compilation unit
	 * @param targetProject
	 *            the target project
	 * @param javaFormatter
	 *            the java formatter
	 */
	public GeneratedJavaFile(CompilationUnit compilationUnit, String targetProject, JavaFormatter javaFormatter) {
		this(compilationUnit, targetProject, null, javaFormatter);
	}

	/* (non-Javadoc)
	 * @see org.mybatis.generator.api.GeneratedFile#getFormattedContent()
	 */
	@Override
	public String getFormattedContent() {
		return javaFormatter.getFormattedContent(compilationUnit);
	}

	/* (non-Javadoc)
	 * @see org.mybatis.generator.api.GeneratedFile#getFileName()
	 */
	@Override
	public String getFileName() {
		return compilationUnit.getType().getShortNameWithoutTypeArguments() + ".java";
	}

	/* (non-Javadoc)
	 * @see org.mybatis.generator.api.GeneratedFile#getTargetPackage()
	 */
	@Override
	public String getTargetPackage() {
		return compilationUnit.getType().getPackageName();
	}

	/**
	 * This method is required by the Eclipse Java merger. If you are not
	 * running in Eclipse, or some other system that implements the Java merge
	 * function, you may return null from this method.
	 *
	 * @return the CompilationUnit associated with this file, or null if the
	 *         file is not mergeable.
	 */
	public CompilationUnit getCompilationUnit() {
		return compilationUnit;
	}

	/**
	 * Gets the file encoding.
	 *
	 * @return the file encoding
	 */
	public String getFileEncoding() {
		return fileEncoding;
	}
}
