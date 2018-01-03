#!/bin/bash

#
# Author: Piotr
# Since: 2018-01-03
#
# Pre-commit hook running mvn clean install
# save the file as <git_directory>/.git/hooks/pre-commit

echo "Running Maven clean install"
# retrieving current working directory
START_DIR=`pwd`

# retrieving directory of this script
MAIN_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
echo  $MAIN_DIR

# go to main project dir
cd $MAIN_DIR/../../

# running maven clean install
mvn clean install
if [ $? -ne 0 ]; then
  "Error while maven clean install the code"
  # go back to start dir
  cd $START_DIR
  exit 1
fi
# go back to start dir
cd $START_DIR