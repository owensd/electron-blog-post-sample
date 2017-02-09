#!/bin/bash

CURRENT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
PROJECT_ROOT=$CURRENT_DIR/..
PRODUCT_DIR=$PROJECT_ROOT/.out/prod/app

if [ ! -d "$PRODUCT_DIR" ]; then
  echo "ERROR: The product directory does not exist! $PRODUCT_DIR"
else
  $PROJECT_ROOT/node_modules/electron-packager/cli.js $PROJECT_ROOT/.out/prod/app --out=$PROJECT_ROOT/.dist
fi
