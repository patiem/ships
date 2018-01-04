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

function helpEcho {
echo "[Description]"
echo
echo "It installs server along with two clients with mvn clean install and executes generated JAR files."
echo
echo "[Important]"
echo -e " \t 1) The assumption is that the script is placed within ./scripts/ directory."
echo -e " \t 2) JAR file name has to be maintained manually."
echo
echo "[Usage]"
echo -e "\t ./deployserverandclients.sh\n"
}

if [[ $1 = "--help" ]];
	then helpEcho
	exit 1;
fi

if ! cd .. ;
then
	helpEcho
	exit 1;
fi
if ! mvn clean -q;
then
	helpEcho
	exit 1;
fi
if ! mvn install -q;
then
	helpEcho
	exit 1;
fi
echo "Installed server, now attempting to deploy.."
if ! (java -jar ./server/target/server*.jar &) ;
then
	helpEcho
	exit 1;
fi
echo "Deployed server, now attempting to deploy the 1st client.."
if ! (java -jar ./client/target/client*.jar &) ;
then
	helpEcho
	exit 1;
fi
echo "Deployed server, now attempting to deploy the 2nd client.."
if ! (java -jar ./client/target/client*.jar &) ;
then
	helpEcho
	exit 1;
fi
