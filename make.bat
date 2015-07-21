@echo off

echo %~dp0

md WebContent\WEB-INF\classes

javac -sourcepath src -d WebContent\WEB-INF\classes -Djava.ext.dirs=E:\apache-tomcat-7.0.61\lib src\base\*.java
javac -sourcepath src -d WebContent\WEB-INF\classes -Djava.ext.dirs=E:\apache-tomcat-7.0.61\lib src\common\*.java
javac -sourcepath src -d WebContent\WEB-INF\classes -Djava.ext.dirs=E:\apache-tomcat-7.0.61\lib src\DB\*.java
javac -sourcepath src -d WebContent\WEB-INF\classes -Djava.ext.dirs=E:\apache-tomcat-7.0.61\lib src\event\*.java
javac -sourcepath src -d WebContent\WEB-INF\classes -Djava.ext.dirs=E:\apache-tomcat-7.0.61\lib src\filter\*.java
javac -sourcepath src -d WebContent\WEB-INF\classes -Djava.ext.dirs=E:\apache-tomcat-7.0.61\lib src\kernel\*.java
javac -sourcepath src -d WebContent\WEB-INF\classes -Djava.ext.dirs=E:\apache-tomcat-7.0.61\lib src\oj\*.java
javac -sourcepath src -d WebContent\WEB-INF\classes -Djava.ext.dirs=E:\apache-tomcat-7.0.61\lib src\servlet\*.java


pause