#!/usr/bin/env bash

wget https://goo.gl/pZZPXS -O gwt.zip
unzip -q gwt.zip
mv gwt-2* gwt
echo '{"gwt": "./gwt"}' > config.json
npm install
