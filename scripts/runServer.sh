#!/bin/bash

#
# Author: Piotr
# Since: 2018-01-19
#

function helpEcho {
echo "[Description]"
echo
echo "It executes client application located in this directory"
echo
echo "[Important]"
echo -e " \t 1) The assumption is that the script and jar files are placed within this some directory."
echo -e " \t 2) JAR file name can not be changed."
echo
echo "[Usage]"
echo -e "\t ./runServer.sh\n"
}

if [[ $1 = "--help" ]];
	then helpEcho
	exit 0;
fi

if ! java -jar server*.jar;
then
	helpEcho
	exit 1;
fi