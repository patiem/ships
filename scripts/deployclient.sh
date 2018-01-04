#!/bin/bash

#
# Author: Sandor
# Since: 2017-12-18
#
# [Important]
#
#   1) The assumption is that the script is placed within ./scripts/
#      directory.
#   2) JAR file name has to be maintained manually.
#
# [Description]
#
# It installs client with mvn clean install and executes
# a generated .jar file.
#


function helpEcho {
echo "[Description]"
echo
echo "It installs client with mvn clean install and executes a generated .jar file."
echo
echo "[Important]"
echo -e " \t 1) The assumption is that the script is placed within ./scripts/ directory."
echo -e " \t 2) JAR file name has to be maintained manually."
echo
echo "[Usage]"
echo -e "\t ./deployclient.sh\n"
}

if [[ $1 = "--help" ]];
	then helpEcho
	exit 1;
fi

cd ../client

if ! mvn clean install -q;
then
	helpEcho
	exit 1;
fi

echo "Installed, now attempting to deploy.."

if ! java -jar ./target/client*.jar;
then
	helpEcho
	exit 1;
fi
