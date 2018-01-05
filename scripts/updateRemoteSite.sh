#!/bin/bash

#
# Author: Sandor
#
# Since: 2017-12-31
#
# [How to use]
# Execute it with no params ./updateRemoteSite.sh
#
# Have our remote site updated: https://korotkevics.github.io/ships/
#
# [Important]
#
# * It is dependent on clone.sh script
# * It requires write access within a dir one level above the project root dir
# * It takes a few minutes to execute
# * It requires a user to provide his credentials at some stage (when performing git push)
#

function helpEcho {
echo 
echo "[Important]"
echo -e " \t * It is dependent on clone.sh script"
echo -e " \t * It requires write access within a dir one level above the project root dir"
echo -e " \t * It takes a few minutes to execute"
echo -e " \t * It requires a user to provide his credentials at some stage (when performing git push)"
echo 
echo "[Usage]"
echo -e "\t ./updateRemoteSite.sh\n"
}

if [[ $1 =1 "--help" ]];
	then helpEcho
	exit 0;
fi

if ! mkdir ../../tempUpdateRemoteSite;
then
	helpEcho
	exit 1;
fi
echo "Clonning first repo [from].."
if ! ./clone.sh ../../tempUpdateRemoteSite/from; 
then
	helpEcho
	exit 1;
fi
echo "Clonning repo [to].."
if ! ./clone.sh ../../tempUpdateRemoteSite/to;
then
	helpEcho
	exit 1;
fi
echo "Checking out to master branch on [from].."
if ! cd ../../tempUpdateRemoteSite/from;
then
	helpEcho
	exit 1;
fi
if ! git checkout master -q;
then
	helpEcho
	exit 1;
fi
echo "Checking out to gh-pages branch on [to].."
if ! cd ../to;
then
	helpEcho
	exit 1;
fi
if ! git checkout gh-pages -q;
then
	helpEcho
	exit 1;
fi
echo "Generating JaCoCo datafiles on [from].."
if ! cd ../from;
then
	helpEcho
	exit 1;
fi
if ! mvn clean install -q;
then
	helpEcho
	exit 1;
fi
echo "Generating site on [from].."
if ! mvn site -q;
then
	helpEcho
	exit 1;
fi
echo "Staging site on [from].."
if ! mvn site:stage -q;
then
	helpEcho
	exit 1;
fi
echo "Removing all files on [to].."
if ! cd ../to;
then
	helpEcho
	exit 1;
fi
if ! rm -rf * ;
then
	helpEcho
	exit 1;
fi
echo "Copying staged site from [from] to [to].."
if ! cd .. ;
then
	helpEcho
	exit 1;
fi
if ! cp from/target/staging/. to/. -r ;
then
	helpEcho
	exit 1;
fi
echo "Adding.. Committing.. Pushing (your credentials will be required).."
if ! cd to ;
then
	helpEcho
	exit 1;
fi
if ! git add . ;
then
	helpEcho
	exit 1;
fi
if ! git commit -m 'Trigger site update' -q ;
then
	helpEcho
	exit 1;
fi
if ! git push origin gh-pages ;
then
	helpEcho
	exit 1;
fi
echo "Done updating remote site. Cleaning up.."
if ! cd ../.. ;
then
	helpEcho
	exit 1;
fi
if ! rm -rf tempUpdateRemoteSite ;
then
	helpEcho
	exit 1;
fi
echo "Done cleaning."
