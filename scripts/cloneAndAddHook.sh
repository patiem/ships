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

function helpEcho {
echo "[How to use]"
echo " 1) Copy a script (if not willing to alias it) into a directory"
echo
echo "[Important]"
echo "* It is dependent on clone.sh script"
echo "* It is dependent on pre-commitHookMaven.sh script"
echo
echo "[Usage]"
echo -e "\t ./cloneAndHook.sh [your_dir_name]"
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
if ! ./clone.sh $1;
	then helpEcho
	exit 1;
fi
cd $1
echo "copying pre-commit hook"
if ! cp scripts/pre-commitHookMaven.sh .git/hooks/pre-commit;
	then helpEcho
	exit 1;
fi
echo "hook added"

