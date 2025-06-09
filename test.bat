@echo off
echo Compiling...

for /r calculus %%f in (*.java) do (
    javac -encoding UTF-8 -d out %%f
)

if %ERRORLEVEL% neq 0 (
    echo Compilation failed.
    exit /b %ERRORLEVEL%
)

echo Running.
echo.
java -cp out calculus.Test
