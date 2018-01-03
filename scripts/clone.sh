#!/bin/bash

#
# Author: Sandor
# Since: 2017-12-18
#
# [How to use]
# 1) Copy a script (if not willing to alias it) into a directory
# 2) Run ./clone.sh [your_dir_name]
#
# Have our remote git repository (https://github.com/korotkevics/ships/)
# cloned into a [your_dir_name].
#

set -e
git clone https://github.com/korotkevics/ships $1 --quiet
cd $1
git checkout master --quiet
echo "Done cloning"
