#!/bin/bash

CURRENT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
PROJECT_ROOT=$CURRENT_DIR/..

PACKAGE_FILE=./package.json
PRODUCT_NAME=$1
PRODUCT_DESCRIPTION=$2
PRODUCT_VERSION=$3
PRODUCT_URL=$4
ELECTRON_VERSION=$5
ELECTRON_PACKAGER_VERSION=$6

read -d '' FILE_CONTENTS << EOF
{
  "name": "$PRODUCT_NAME",
  "version": "$PRODUCT_VERSION",
  "description": "$PRODUCT_DESCRIPTION",
  "devDependencies": {
    "grunt": "^1.0.0",
    "grunt-contrib-symlink": "^1.0.0",
    "electron": "$ELECTRON_VERSION",
    "electron-packager": "$ELECTRON_PACKAGER_VERSION"
  },
  "license": "MIT",
  "repository": "$PRODUCT_URL"
}
EOF

echo $FILE_CONTENTS > $PACKAGE_FILE
