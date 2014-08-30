ps -ef | grep fitnesse | grep -v grep | awk '{print $2}' | xargs kill
