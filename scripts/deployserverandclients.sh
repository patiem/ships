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
# It installs server with mvn clean install and executes
# a generated .jar file.
#

set -e
cd ../server
mvn clean -q
mvn install -q
echo "Installed server, now attempting to deploy.."
java -jar ../server/target/server-1.0-SNAPSHOT.jar &
echo "Deployed server, now attempting to deploy the 1st client.."
java -jar ../client/target/client-1.0-SNAPSHOT.jar &
echo "Deployed server, now attempting to deploy the 2nd client.."
java -jar ../client/target/client-1.0-SNAPSHOT.jar
