#!/bin/bash

apt update -y && apt upgrade -y

apt install -y openjdk-11-jdk unzip

#Installing Jenkins
wget -q -O - https://pkg.jenkins.io/debian-stable/jenkins.io.key | apt-key add -
sh -c 'echo deb https://pkg.jenkins.io/debian-stable binary/ > /etc/apt/sources.list.d/jenkins.list'
apt update -y
apt install -y jenkins

jenkins --version

#Installing SonarQube
cd /opt
wget https://binaries.sonarsource.com/Distribution/sonarqube/sonarqube-10.4.1.88267.zip
unzip sonarqube-10.4.1.88267.zip
mv sonarqube-* sonarqube

adduser --system --no-create-home --group --disabled-login sonar
chown -R sonar:sonar /opt/sonarqube

