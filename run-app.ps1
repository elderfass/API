# Set Java 26 as the runtime for this session
$env:JAVA_HOME = "c:\Users\Eder\Documents\Univercidad\moviles\Corte 2\Api_mongo\src\main\resources\oracleJdk-26"
Write-Host "Using JAVA_HOME: $env:JAVA_HOME" -ForegroundColor Green
& "$env:JAVA_HOME\bin\java" -version

Write-Host "`nStarting Spring Boot Application...`n" -ForegroundColor Cyan

# Run Spring Boot
.\mvnw.cmd spring-boot:run
