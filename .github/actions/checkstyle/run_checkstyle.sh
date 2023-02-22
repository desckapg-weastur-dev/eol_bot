#!/bin/sh -l

java -jar /checkstyle.jar -c $GITHUB_WORKSPACE/$2 $GITHUB_WORKSPACE/$1
