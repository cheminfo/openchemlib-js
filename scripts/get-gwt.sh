#/bin/bash

wget -q https://storage.googleapis.com/gwt-releases/gwt-2.9.0.zip -O gwt.zip
unzip -q gwt.zip
mv gwt-2* gwt
echo '{"gwt": "./gwt"}' > config.json
