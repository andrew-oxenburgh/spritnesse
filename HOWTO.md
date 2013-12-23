Run the Demo
===

pull the source - clone from somewhere - if you're reading this, you probably already have.

git clone /Volumes

move to that dir
===

cd spritnesse

-- build the project.
mvn package


-- run fitnesse
java -jar fitnesse.jar -p 9002


-- go to the demo page
open http://localhost:9002/DemoPage