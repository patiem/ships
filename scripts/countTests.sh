#!/bin/bash

mvn test | grep -A 2 Results | grep Tests | sed 's/[^0-9 ]//g' > tempFile.txt
echo -n "Tests run: "; awk '{ SUM += $1} END { print SUM }' tempFile.txt
echo -n "Failures: "; awk '{ SUM += $2} END { print SUM }' tempFile.txt
echo -n "Errors: "; awk '{ SUM += $3} END { print SUM }' tempFile.txt
echo -n "Skipped: "; awk '{ SUM += $4} END { print SUM }' tempFile.txt
rm tempFile.txt

