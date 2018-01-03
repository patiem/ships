#!/bin/bash

#
# Author: Sandor
#
# Since: 2018-01-02
#
# [How to use]
# Execute it with no params ./updateRemoteSite.sh
#
# Have a local site generated with entry point at: [root]/target/staging/index.html
#
# [Important]
#
# * It requires write access from within root dir
# * It takes a few minutes to execute
# * The assumption is that the repo is up-to-date and there aren't
#   uncommitted changes on current branch
#
set -e

cd ..
echo "Checking out to master branch.."
git checkout master -q
echo "Generating JaCoCo datafiles.."
mvn clean install -q
echo "Creating site.."
mvn site -q
echo "Staging site.."
mvn site:stage -q
echo "Done generating site.\nPlease browse a local site with your favourite browser at [root]/target/staging/index.html"
