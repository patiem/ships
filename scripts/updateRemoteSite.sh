#!/bin/bash

#
# Author: Sandor
#
# Since: 2017-12-31
#
# TODO: add extra info

echo "Clonning first repo [from].."
./clone.sh from
echo "Clonning repo [to].."
./clone.sh to
echo "Checking out to master branch on [from].."
cd from
git checkout master --quiet
echo "Checking out to gh_pages branch on [to].."
cd ../to
git checkout gh-pages --quiet
echo "Generating JaCoCo datafiles on [from].."
cd ../from
mvn clean install -q
echo "Generating site on [from].."
mvn site -q
echo "Staging site on [from].."
mvn site:stage -q
echo "Removing all files on [to].."
cd ../to
rm -rf *
echo "Copying staged site from [from] to [to].."
echo "Adding.. Committing.. Pushing (your credentials will be required).."
git add . -q; git commit -m 'Trigger site update' -q #; git push origin gh-pages
echo "Done updating remote site. Cleaning up.."
cd ..
echo "Removing [from].."
rm -rf from
echo "Removing [to].."
rm -rf to
echo "Done cleaning."
