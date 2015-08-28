# DynamicJavaCompiler
### compile/load classes on the fly

Example use for code generators to compile and load on the fly:
```java
// generate implementation for a known interface
String className = "package.GenedDummyImpl"
String code = ...
```
```java
// Compile and load class using a JavaLoader
JavaLoader loader = new JavaLoader();
loader.compile(className, code);
// Instatiate and use
KnownDummyInterface dummy = (KnownDummyInterface) loader.instantiate(className);
dummy.doStuff();
```
