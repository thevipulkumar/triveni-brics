#!/bin/bash
# =====================================================
#  Start the Triveni Bricks website
#  Double-click this file, or run:  ./run.sh
# =====================================================
cd "$(dirname "$0")" || exit 1

# Find a Java installation
if [ -z "$JAVA_HOME" ]; then
    if [ -d "/opt/homebrew/opt/openjdk@21" ]; then
        export JAVA_HOME="/opt/homebrew/opt/openjdk@21"
    elif [ -d "/opt/homebrew/opt/openjdk" ]; then
        export JAVA_HOME="/opt/homebrew/opt/openjdk"
    elif command -v /usr/libexec/java_home >/dev/null 2>&1; then
        export JAVA_HOME="$(/usr/libexec/java_home)"
    fi
fi

if [ ! -x "$JAVA_HOME/bin/java" ]; then
    echo "Java was not found. Install it with:  brew install openjdk@21"
    exit 1
fi

# Refuse to start a second copy: two copies cannot share the database file,
# and the error H2 prints in that case is not helpful.
if lsof -nP -iTCP:8080 -sTCP:LISTEN >/dev/null 2>&1; then
    echo "---------------------------------------------"
    echo " The website is ALREADY RUNNING."
    echo ""
    echo " Just open this address in your browser:"
    echo "     http://localhost:8080"
    echo ""
    echo " To restart it, go to the window where it is"
    echo " running and press Control+C first."
    echo "---------------------------------------------"
    exit 0
fi

echo "---------------------------------------------"
echo " Starting Triveni Bricks website..."
echo " Java: $("$JAVA_HOME/bin/java" -version 2>&1 | head -1)"
echo ""
echo " When you see 'Started TriveniBricksApplication',"
echo " open this address in your browser:"
echo ""
echo "     http://localhost:8080"
echo ""
echo " Press Control+C to stop the website."
echo "---------------------------------------------"
echo ""

exec mvn spring-boot:run
