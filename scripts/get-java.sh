#!/bin/bash

# clear previous jdk installation
rm -rf jdk

wget -q https://github.com/adoptium/temurin21-binaries/releases/download/jdk-21.0.6%2B7/OpenJDK21U-jdk_x64_linux_hotspot_21.0.6_7.tar.gz -O jdk.tar.gz
tar xf jdk.tar.gz
mv jdk-21* jdk
echo '{"gwt": "./gwt", "jdk": "./jdk"}' > config.json
