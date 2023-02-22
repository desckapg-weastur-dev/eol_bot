#!/bin/sh -l

cd $GITHUB_WORKSPACE

java -jar /checkstyle.jar -c $2 $1
