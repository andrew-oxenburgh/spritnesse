#!/bin/sh
java -jar lib/fitnesse-standalone.jar -p 9001 -l /tmp/fitnesse.log -o &
open localhost:9001
