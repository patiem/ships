#!/bin/bash

#
# Author: Piotr
#
# Since: 2017-01-03
#
# [How to use]
# 1) Copy a script (if not willing to alias it) into a directory
# 2) Run ./cloneAndAddHook.sh [your_dir_name]
#
# [Important]
#
# * It is dependent on clone.sh script
# * It is dependent on pre-commitHookMaven.sh script
#

./clone.sh $1
cd $1
echo "copying pre-commit hook"
chmod u+x scripts/pre-commitHookMaven.sh
cp scripts/pre-commitHookMaven.sh .git/hooks/pre-commit
echo "hook added"