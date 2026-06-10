@echo off
REM Set Java 26 as the runtime
set JAVA_HOME=c:\Users\Eder\Documents\Univercidad\moviles\Corte 2\Api_mongo\src\main\resources\oracleJdk-26
echo Using JAVA_HOME: %JAVA_HOME%
%JAVA_HOME%\bin\java -version

REM Run Spring Boot
.\mvnw.cmd spring-boot:run
