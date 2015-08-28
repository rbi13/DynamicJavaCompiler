package com.rbi.dynamic.compiler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;

public class JavaLoader {

	private ClassFileManager fileManager;
	private Map<String,DynamicJavaSourceObject> compiledUnits;

	public JavaLoader() {
		fileManager = new ClassFileManager(
				ToolProvider.getSystemJavaCompiler().getStandardFileManager(null, null, null));
		compiledUnits = new HashMap<>();
	}
	
	public boolean compile(String className, String code) throws ClassNotFoundException{
		return compile(Arrays.asList(new DynamicJavaSourceObject[]{ new DynamicJavaSourceObject(className,code) }));
	}
	
	public boolean compile(Map<String,String> sources) throws ClassNotFoundException{
		// or loop for backward-compatibility
		List<? extends DynamicJavaSourceObject> compilationUnits = sources.entrySet().stream()
				.map( source -> new DynamicJavaSourceObject(source.getKey(), source.getValue()) )
				.collect(Collectors.toList());
		return compile(compilationUnits);
	}
	
	public boolean compile(Collection<? extends DynamicJavaSourceObject> compilationUnits) 
			throws ClassNotFoundException{
		
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		
		List<String> compilerOptions = new ArrayList<>();
		CompilationTask compilerTask = compiler.getTask(
				null, 
				fileManager, 
				new DiagnosticCollector<JavaFileObject>(), 
				compilerOptions, 
				null,
				compilationUnits);

		boolean result = compilerTask.call();
		if(result){
			load(compilationUnits);
		}
		return result;
	}
	
	private void load(Collection<? extends DynamicJavaSourceObject> compilationUnits) 
			throws ClassNotFoundException{
		for (DynamicJavaSourceObject unit : compilationUnits) {
			fileManager.getClassLoader(null).loadClass(unit.getClassName());
			compiledUnits.put(unit.getClassName(),unit);
		}
	}

	public Object instantiate(String className) 
			throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		return Class.forName(className, false, fileManager.getClassLoader(null)).newInstance();
	}
}
