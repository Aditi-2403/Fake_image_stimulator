#!/bin/bash

# Configuration
BACKEND_SRC="backend/src"
BACKEND_BIN="backend/bin"
FRONTEND_HTML="frontend/index.html"
MAIN_CLASS="com.analyzer.Main"

echo "------------------------------------------------"
echo "🚀 Starting Fake Image Stimulator Forensic Suite"
echo "------------------------------------------------"

# 1. Clean and Create Bin Directory
echo "[1/3] Preparing workspace..."
mkdir -p "$BACKEND_BIN"

# 2. Compile Java Backend
echo "[2/3] Compiling Backend..."
javac -d "$BACKEND_BIN" $(find "$BACKEND_SRC" -name "*.java")

if [ $? -ne 0 ]; then
    echo "❌ Compilation failed. Please check your Java installation."
    exit 1
fi

# 3. Start Backend in Background
echo "[3/3] Launching Forensic Server..."
java -cp "$BACKEND_BIN" "$MAIN_CLASS" &
BACKEND_PID=$!

# Give the server a moment to start
sleep 2

# 4. Open Frontend
echo "🌐 Opening Forensic Dashboard..."
if [[ "$OSTYPE" == "darwin"* ]]; then
    open "$FRONTEND_HTML"
elif [[ "$OSTYPE" == "linux-gnu"* ]]; then
    xdg-open "$FRONTEND_HTML"
else
    echo "Please open $FRONTEND_HTML manually in your browser."
fi

echo "------------------------------------------------"
echo "✅ System is live! Dashboard should be open."
echo "Press Ctrl+C to stop the system."
echo "------------------------------------------------"

# Wait for backend to finish (or for user to interrupt)
trap "kill $BACKEND_PID; exit" INT
wait $BACKEND_PID
