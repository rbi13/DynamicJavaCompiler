package com.rbi.example;

import java.nio.file.Files;
import java.nio.file.Paths;

import com.rbi.dynamic.compiler.JavaLoader;

public class Example {

	public static void main(String[] args) {

		// TODO: get className from source
		String className = "com.rbi.example.GenedDummyImpl";
		String codePath = String
				.format(System.getProperty("user.dir")+"/src/%s", className)
				.replace(".","/")
				+".txt";
		try {
			// String to file (simulating codeGen)
			String code = new String( Files.readAllBytes( Paths.get(codePath) ) );
			System.out.println("DUMMY's SOURCE:");
			System.out.println( code );
			// Compile and load class using a JavaLoader
			JavaLoader loader = new JavaLoader();
			loader.compile(className, code);
			//Instantiate and test
			KnownDummyInterface dummy = (KnownDummyInterface) loader.instantiate(className);
			System.out.println("CALLING DUMMY - dummy.getName():");
			System.out.println(dummy.getName());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
