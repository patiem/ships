#!/bin/bash

#
# Author: Sandor
# Since: 2017-12-19
#
# [Important]
#
#   1) The assumption is that the script is placed within ./scripts/
#      directory.
#   2) JAR file name has to be maintained manually.
#
# [Description]
#
# It installs server along with two clients with mvn clean install and executes
# generated JAR files.
#

set -e
cd ..
mvn clean -q
mvn install -q
echo "Installed server, now attempting to deploy.."
java -jar ./server/target/server*.jar &
echo "Deployed server, now attempting to deploy the 1st client.."
java -jar ./client/target/client*.jar &
echo "Deployed server, now attempting to deploy the 2nd client.."
java -jar ./client/target/client*.jar &
