#!/bin/bash

# Name of the screen session
SCREEN_NAME="timelink_backend"

# Path to your backend application
APP_DIR="./"

# Java JAR file
JAR_FILE="timelink-backend-0.0.1-SNAPSHOT.jar"

# Java command to start your application
START_CMD="java -jar $JAR_FILE"

# Check if the screen session already exists
if screen -list | grep -q "$SCREEN_NAME"; then
    echo "A screen named '$SCREEN_NAME' is already running."
    echo "You can reattach with 'screen -r $SCREEN_NAME'."
    exit 1
fi

# Navigate to the app directory
cd "$APP_DIR" || { echo "Directory not found: $APP_DIR"; exit 1; }

# Start the screen session and run the Java command
screen -dmS "$SCREEN_NAME" bash -c "$START_CMD"

echo "Java backend application started in screen session '$SCREEN_NAME'."
echo "You can reattach with 'screen -r $SCREEN_NAME'."
