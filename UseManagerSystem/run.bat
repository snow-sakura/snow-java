chcp 65001 >nul
@echo off
echo 正在启动用户管理系统...
echo.

cd /d "%~dp0"

REM 检查Java是否已安装
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo 错误: 未找到Java。请确保已安装Java 21或更高版本。
    pause
    exit /b 1
)

REM 检查Maven是否已安装
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo 错误: 未找到Maven。请确保已安装Maven。
    pause
    exit /b 1
)

echo 构建项目...(Building project...)
mvn clean install -DskipTests

echo 准备启动应用程序，数据库将在首次启动时自动创建...
set BUILD_EXIT_CODE=%errorlevel%

if %BUILD_EXIT_CODE% equ 0 (
    echo.
    echo 启动应用程序...
    mvn spring-boot:run
) else (
    echo 构建失败，请检查错误信息。
    pause
)

pause