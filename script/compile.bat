@echo off

Rem set GRAALVM_HOME=C:\Users\IEUser\Downloads\graalvm-ce-java8-19.3.1
Rem set PATH=%PATH%;C:\Users\IEUser\bin

if "%GRAALVM_HOME%"=="" (
    echo Please set GRAALVM_HOME
    exit /b
)

set JAVA_HOME=%GRAALVM_HOME%
set PATH=%GRAALVM_HOME%\bin;%PATH%

set /P PUGET_CLI_VERSION=< resources\PUGET_CLI_VERSION
echo Building Babashka %PUGET_CLI_VERSION%

if "%GRAALVM_HOME%"=="" (
echo Please set GRAALVM_HOME
exit /b
)

set PATH=%USERPROFILE%\deps.clj;%PATH%

if not exist "classes" mkdir classes
call deps -e "(compile 'puget-cli.main)"
deps -Spath > .classpath
set /P PUGET_CLASSPATH=<.classpath

call %GRAALVM_HOME%\bin\gu.cmd install native-image

call %GRAALVM_HOME%\bin\native-image.cmd ^
  "-cp" "%PUGET_CLASSPATH%;classes" ^
  "-H:Name=puget" ^
  "-H:+ReportExceptionStackTraces" ^
  "--initialize-at-build-time" ^
  "-J-Dclojure.spec.skip-macros=true" ^
  "-J-Dclojure.compiler.direct-linking=true" ^
  "--report-unsupported-elements-at-runtime" ^
  "--verbose" ^
  "--no-fallback" ^
  "--no-server" ^
  "-J-Xmx3g" ^
  "puget_cli.main"

del .classpath

if %errorlevel% neq 0 exit /b %errorlevel%

echo Creating zip archive
jar -cMf puget-cli-%PUGET_CLI_VERSION%-windows-amd64.zip puget.exe
