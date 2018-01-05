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

function helpEcho {
echo "[How to use]"
echo " 1) Copy a script (if not willing to alias it) into a directory"
echo
echo "[Usage]"
echo -e "\t ./clone.sh [your_dir_name]"
echo -e "\t\t [your_dir_name] - dir to which you want to clone"
}

if [[ $1 = "--help" ]];
	then helpEcho
	exit 0;
fi

if [[ ${#1} = 0 ]]; 
	then echo -e "no args provided! \n\n" 
	helpEcho
	exit 1;
fi

if ! git clone https://github.com/korotkevics/ships $1 --quiet;
then
	helpEcho
	exit 1;
fi

cd $1
if ! git checkout master --quiet;
then
	helpEcho
	exit 1;
fi
echo "Done cloning"
