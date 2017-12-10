#!/bin/bash

cd ..
echo "Executing <mvn clean>.."
mvn clean
echo "Executing <mvn package>.."
mvn package
echo "Executing <mvn clean>.."
mvn clean
echo "Executing <mvn enforcer:enforce>.."
mvn enforcer:enforce
echo "Executing <mvn site>.."
mvn site
