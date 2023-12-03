@echo off
SETLOCAL

REM Set the paths to your JAR files and main class
SET JAR_PATH=target\photo-video-categorizer-1.0.0.jar
SET LIB_PATH=lib\*
SET MAIN_CLASS=net.tokmak.photo_video_categorizer.Categorizer

REM Check if two arguments are provided
IF "%~1"=="" GOTO NoArgs
IF "%~2"=="" GOTO NoArgs

REM Run the Java application
java -cp "%JAR_PATH%;%LIB_PATH%" %MAIN_CLASS% %1 %2
GOTO End

:NoArgs
echo Usage: runCategorizer ^<source_directory^> ^<destination_directory^>
echo Example: runCategorizer "G:\Archive\Photos_Videos\WorkDir" "G:\Archive\Photos_Videos\OutDir"
GOTO End

:End
ENDLOCAL
