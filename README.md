# lulo-code-challenge

> A simple Java project to procces accounts and transactions 
> Developed by Gabriel Caceres Leguizamon

## Prerequisites

You will need the following things properly installed on your computer.

* [Git](http://git-scm.com/)
* [Gradle](https://gradle.org//)
* [Docker](https://docs.docker.com/)

## Installation

This Java project use [Gradle](https://gradle.org//), 
is an open-source build automation tool that is designed to be flexible enough to build almost any type of software.

* Execute: `git clone https://github.com/GabrielCaceresL/lulo-code-challenge.git`.
* Change into the new directory `cd lulo-code-challenge`

## For Run api use
```bash
gradle clean build
java -jar build/libs/lulo-code-challenge-1.0.jar
```

## For Run api with docker use
```bash
gradle clean build
docker build -t lulo-code .
docker run --name "lulo-code-challenge" -it lulo-code
```
