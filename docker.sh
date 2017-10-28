#!/usr/bin/env bash

PREFIX=drako
NAME=eventer-alexa-skill

case "$1" in
  build) docker build --rm -t ${PREFIX}/${NAME}:latest .;;
  start) docker run --rm -p 5001:8080 -d --name ${NAME} -t ${PREFIX}/${NAME};;
  stop) docker stop ${NAME};;
esac
