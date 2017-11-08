#!/bin/bash
cd /home/pi/projects/Video-Uploader-Bot/
mvn clean install
java -jar /home/pi/projects/Video-Uploader-Bot/target/videobot-1.0-SNAPSHOT.jar