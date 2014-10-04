#!/bin/sh
ps -ef | grep fitnesse-standalone.jar | grep -v grep | awk '{print $2}' | xargs kill
