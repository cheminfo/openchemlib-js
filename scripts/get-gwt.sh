#!/bin/bash

wget -q https://github.com/gwtproject/gwt/releases/download/2.12.1/gwt-2.12.1.zip -O gwt.zip
unzip -q gwt.zip
mv gwt-2* gwt
echo '{"gwt": "./gwt"}' > config.json
