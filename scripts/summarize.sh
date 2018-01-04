#!/bin/bash

#
# Author: Sandor, Piotr
# Since: 2017-12-18
#
# [Important]
#
#   1) Make sure the branch you're on has no uncommitted changes.
#      Auto-commit wasn't introduced for safety reasons.
#   2) The assumption is that the script is placed within ./scripts/
#      directory.
#
# [Description]
#
# It gives a quick summary of what a project has.
# Some of output is automated, the remainder of values
# has to be pre-defined manually.
#
#
#Go to the root directory and switch to master branch quietly.
#Won't work properly if there are uncommitted changes (see above).
#

function helpEcho {
echo 
echo "[Description]" 
echo
echo "It gives a quick summary of what a project has. Some of output is automated, the remainder of values has to be pre-defined manually. "
echo "Won't work properly if there are uncommitted changes (see above)."
echo
echo "[Important]"
echo -e " \t 1) Make sure the branch you're on has no uncommitted changes. Auto-commit wasn't introduced for safety reasons."
echo -e " 2) The assumption is that the script is placed within ./scripts/ directory."
echo
echo "[Usage]"
echo -e "\t ./summarize.sh\n"
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
if ! git checkout master --quiet ;
then
	helpEcho
	exit 1;
fi
#[] Auto output

echo "[ Q ] How many tests do we have.."
if ! ./scripts/countTests.sh;
then
	helpEcho
	exit 1;
fi
echo "[ Q ] How many commits do we have.."
if ! git rev-list HEAD ^master --count ;
then
	helpEcho
	exit 1;
fi
echo "[ Q ] How many packages do we have.."
if ! grep -r "package" --include=*.java . --no-filename | sort -u | wc -l ; 
then
	helpEcho
	exit 1;
fi
echo "[ Q ] How many interfaces do we have.."
grep -r " interface " --include=*.java . --no-filename | sort -u | grep -i "{" | wc -l ;
then
	helpEcho
	exit 1;
fi
echo "[ Q ] How many lines of code do we have.."
if ! ( find ./ -name '*.java' -print0 | xargs -0 cat ) | wc -l ;
then
	helpEcho
	exit 1;
fi

echo "[ Q ] How many public APIs do we have..(=public method count)"
if ! grep -r "public .*) {*" --include=*.java . --no-filename | sort -u |wc -l;
then
	helpEcho
	exit 1;
fi

#[] Manual output

echo "[ Q ] How many design patterns do we have.."
echo "3 (Builder, Factory Method in Shared module, and MVC in Client module - since we use JavaFX)"

echo "[ Q ] How many package-private APIs do we have.."
echo "2: event triggers, message handlers in Client module"
