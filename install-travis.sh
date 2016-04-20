#!/usr/bin/env bash

wget http://goo.gl/62SeR5 -O gwt.zip
unzip gwt.zip
mv gwt-2* gwt
echo '{"gwt": "./gwt"}' > config.json
