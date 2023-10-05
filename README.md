# EOL Bot: Telegram bot notifies about *End Of Life* dates

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://en.wikipedia.org/wiki/MIT_License)
[![Java version](https://img.shields.io/badge/Java-11-blue)](https://www.oracle.com/java/technologies/downloads/#java11)
[![PMD](https://github.com/desckapg-weastur-dev/eol_bot/actions/workflows/pmd.yml/badge.svg)](https://pmd.github.io/)
[![Checkstyle](https://github.com/desckapg-weastur-dev/eol_bot/actions/workflows/checkstyle.yml/badge.svg)](https://checkstyle.org/sun_style)

## Description
This is a small bot that allows you to track the *EOL* dates of various technologies and software. For tracking is used 
[endoflife.date](https://endoflife.date/)

## Features
- Select techonologies to tracking
- Select version of technoologies
- Notifies frequency settings

## Project status
Now the project is in pre-development - setting up workspace. 
So, roadmap and other information about you can see in [WIKI](https://github.com/desckapg-weastur-dev/eol_bot/wiki)

## Installation / Requirements

To run the project you must have installed *Java 11, GIT*.
```shell
sudo apt install openjdk-11-jdk git
```
Also, you can use *Docker* to launch MongoDB needed for the bot
```docker
docker pull mongo
docker create volume mongodbdata
docker run --name mongodb -d -p 27017:27017 -v mongodbdata:/data/db mongodb/mongodb-community-server:6.0-ubi8
```

To build the project use *Gradle* wrapper
```shell
./gradlew build
```
For storing *secrets* use *".env"* file in the root of project and [dotenv lib](https://github.com/cdimascio/dotenv-java). </br>
Exmaple:
```
eol_bot_token="0000000000:***********************************"
```
