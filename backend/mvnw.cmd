@echo off
setlocal EnableExtensions

set "MAVEN_VERSION=3.9.9"
set "MAVEN_BASE=%USERPROFILE%\.maven"
set "MAVEN_HOME=%MAVEN_BASE%\apache-maven-%MAVEN_VERSION%"
set "MAVEN_ZIP=%TEMP%\apache-maven-%MAVEN_VERSION%-bin.zip"
set "MAVEN_URL=https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/%MAVEN_VERSION%/apache-maven-%MAVEN_VERSION%-bin.zip"

if exist "%MAVEN_HOME%\bin\mvn.cmd" goto RUN_MAVEN

if not exist "%MAVEN_BASE%" mkdir "%MAVEN_BASE%"

echo Maven %MAVEN_VERSION% was not found. Downloading it now...

powershell.exe -NoProfile -ExecutionPolicy Bypass -Command "$ErrorActionPreference='Stop'; Invoke-WebRequest -UseBasicParsing -Uri '%MAVEN_URL%' -OutFile '%MAVEN_ZIP%'"
if errorlevel 1 (
  echo Failed to download Maven. Check your internet connection or corporate proxy settings.
  exit /b 1
)

powershell.exe -NoProfile -ExecutionPolicy Bypass -Command "$ErrorActionPreference='Stop'; Expand-Archive -LiteralPath '%MAVEN_ZIP%' -DestinationPath '%MAVEN_BASE%' -Force"
if errorlevel 1 (
  echo Failed to extract Maven.
  exit /b 1
)

del /q "%MAVEN_ZIP%" >nul 2>&1

:RUN_MAVEN
call "%MAVEN_HOME%\bin\mvn.cmd" %*
exit /b %ERRORLEVEL%
