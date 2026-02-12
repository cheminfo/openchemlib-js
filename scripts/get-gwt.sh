#!/bin/bash

# clear previous gwt installation
rm -rf gwt

wget -q https://github.com/gwtproject/gwt/releases/download/2.13.0/gwt-2.13.0.zip -O gwt.zip
unzip -q gwt.zip
mv gwt-2* gwt
echo '{"gwt": "./gwt"}' > config.json
