#!/bin/bash

# Check if two arguments are provided
if [ "$#" -ne 2 ]; then
    echo "Usage: ./runCategorizer.sh <source_directory> <destination_directory>"
    echo "Example: ./runCategorizer.sh /path/to/source /path/to/destination"
    exit 1
fi

# Assigning arguments to variables
DIRECTORY_PATH="$1"
DESTINATION_DIRECTORY="$2"

# Set the paths to your JAR files and main class
JAR_PATH="target/photo-video-categorizer-1.0.0.jar"
LIB_PATH="lib/*"
MAIN_CLASS="net.tokmak.photo_video_categorizer.Categorizer"

# Run the Java application
java -cp "$JAR_PATH:$LIB_PATH" $MAIN_CLASS "$DIRECTORY_PATH" "$DESTINATION_DIRECTORY"
