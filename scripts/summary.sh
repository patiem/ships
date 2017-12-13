#!/bin/bash

echo ""
echo "<<< AUTO SUMMARY >>>"
echo ""

jshell packages.jsh --feedback concise

# Go to the root directory and switch to master branch quietly
cd ..
git checkout master --quiet

echo "[ Q ] How many tests do we have.."
mvn test | grep -o "Tests run: [0-9]" | sed 's/[^0-9]*//g' | awk '{ SUM += $1} END { print SUM }'

echo "[ Q ] How many commits do we have.."
git rev-list --all --count

echo "[ Q ] How many lines of code do we have.."
( find ./ -name '*.java' -print0 | xargs -0 cat ) | wc -l

echo ""
echo "<<< MANUAL SUMMARY >>>"
echo ""
echo "[ Q ] How many design patterns do we have.."
echo "1"