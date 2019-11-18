cd ..
call mvn clean compile assembly:single
echo clean compile complete
call mvn test
echo tests complete
cd target/
call java -jar sampleapp-1.0.0-jar-with-dependencies.jar
pause