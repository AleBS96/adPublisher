@echo off
REM Abre Chrome con de selenium y puerto de depuración para Selenium

set USUARIOWINDOWS=%USERNAME%
set PERFILGOOGLE=%~1
set CHROMEDEBUGGINGPORT=%~2


"C:\Program Files\Google\Chrome\Application\chrome.exe" ^
--remote-debugging-port=%CHROMEDEBUGGINGPORT% ^
--user-data-dir="C:\Users\%USUARIOWINDOWS%\RevolicoChromeProfiles\%PERFILGOOGLE%" ^
--start-maximized

pause



