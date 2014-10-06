cd acceptance
javac -cp .;..\build\classes\main Feature1Keywords.java
java -cp .;..\build\classes\main;C:\RobotFramework\robotframework-2.8.5.jar;C:\RobotFramework\swinglibrary-1.5.1.jar org.robotframework.RobotFramework Feature1Tests.txt
cd ..