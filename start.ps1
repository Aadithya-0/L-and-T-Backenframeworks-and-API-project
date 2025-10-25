# Employee Management System - Quick Start Script
# Run this script to set up and start the application

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  Employee Management System - Setup" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Step 1: Check MySQL
Write-Host "[Step 1/4] Checking MySQL connection..." -ForegroundColor Yellow
Write-Host "⚠️  Make sure MySQL is running on localhost:3306" -ForegroundColor Yellow
Write-Host "⚠️  Update password in application.properties or DBConnection.java" -ForegroundColor Yellow
Write-Host ""
Read-Host "Press Enter when MySQL is ready"

# Step 2: Create Database
Write-Host ""
Write-Host "[Step 2/4] Creating EmployeeDB database..." -ForegroundColor Yellow
Write-Host "Run this in MySQL:" -ForegroundColor White
Write-Host "  CREATE DATABASE IF NOT EXISTS EmployeeDB;" -ForegroundColor Green
Write-Host ""
$createDb = Read-Host "Have you created the EmployeeDB database? (y/n)"
if ($createDb -ne "y") {
    Write-Host "❌ Please create the database first and run this script again." -ForegroundColor Red
    exit
}

# Step 3: Build Project
Write-Host ""
Write-Host "[Step 3/4] Building the project..." -ForegroundColor Yellow
& .\mvnw.cmd clean install -DskipTests
if ($LASTEXITCODE -ne 0) {
    Write-Host "❌ Build failed! Check the errors above." -ForegroundColor Red
    exit
}
Write-Host "✅ Build successful!" -ForegroundColor Green

# Step 4: Run Application
Write-Host ""
Write-Host "[Step 4/4] Starting the application..." -ForegroundColor Yellow
Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  Application is starting..." -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

& .\mvnw.cmd spring-boot:run
