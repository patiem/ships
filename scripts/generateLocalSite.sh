#!/bin/bash

#
# Author: Sandor, Magda
# Since: 2018-01-02
#

function helpEcho {
echo
echo "[Important]"
echo -e " \t * It requires write access from within root dir"
echo -e " \t * It takes a few minutes to execute"
echo -e " \t * The assumption is that the repo is up-to-date and there aren't uncommitted changes on current branch"
echo
echo "[Usage]"
echo -e "\t ./generateLocalSite.sh\n"
}

if [[ $1 = "--help" ]];
	then helpEcho
	exit 0;
fi

if ! cd .. ;
then
	helpEcho
	exit 1;
fi
echo "Checking out to master branch.."
if ! git checkout master -q;
then
	helpEcho
	exit 1;
fi
echo "Generating JaCoCo datafiles.."
if ! mvn clean install -q;
then
	helpEcho
	exit 1;
fi
echo "Creating site.."
if ! mvn site -q;
then
	helpEcho
	exit 1;
fi
echo "Staging site.."
if ! mvn site:stage -q;
then
	helpEcho
	exit 1;
fi
echo "Done generating site. Please browse a local site with your favourite browser at [root]/target/staging/index.html"
