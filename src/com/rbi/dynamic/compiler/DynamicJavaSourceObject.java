package com.rbi.dynamic.compiler;

import java.io.IOException;
import java.net.URI;

import javax.tools.SimpleJavaFileObject;

class DynamicJavaSourceObject extends SimpleJavaFileObject {
	private String className;
	private String sourceCode;

	/**
	 * Converts the name to an URI, as that is the format expected by
	 * JavaFileObject
	 *
	 *
	 * @param String
	 *            name given to the class file
	 * @param String
	 *            source the source code string
	 */
	protected DynamicJavaSourceObject(String name, String source) {
		super(URI.create("string:///" + name.replaceAll("\\.", "/") + Kind.SOURCE.extension), Kind.SOURCE);
		this.className = name;
		this.sourceCode = source;
	}

	@Override
	public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
		return sourceCode;
	}

	public String getClassName(){
		return className;
	}
	
	public String getSourceCode() {
		return sourceCode;
	}
}
