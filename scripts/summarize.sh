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

cd ..
git checkout master --quiet
#[] Auto output

echo "[ Q ] How many tests do we have.."
./scripts/countTests.sh
echo "[ Q ] How many commits do we have.."
git rev-list HEAD ^master --count

echo "[ Q ] How many packages do we have.."
grep -r "package" --include=*.java . --no-filename | sort -u | wc -l

echo "[ Q ] How many interfaces do we have.."
grep -r " interface " --include=*.java . --no-filename | sort -u | grep -i "{" | wc -l

echo "[ Q ] How many lines of code do we have.."
( find ./ -name '*.java' -print0 | xargs -0 cat ) | wc -l

#[] Manual output

echo "[ Q ] How many design patterns do we have.."
echo "3 (Builder, Factory Method in Shared module, and MVC in Client module - since we use JavaFX)"

echo "[ Q ] How many public APIs do we have.."
echo "3: communication, conversion and fleet in Shared module"
echo "public methods: "
grep -r "public .*) {*" --include=*.java . --no-filename | sort -u |wc -l

echo "[ Q ] How many package-private APIs do we have.."
echo "2: event triggers, message handlers in Client module"
