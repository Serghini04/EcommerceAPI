#!/bin/bash

# This script sets up the environment for the EcommerceAPI project.
# It installs Docker, Java 17, and starts a MySQL 8 container.

sudo apt update
sudo apt install openjdk-17-jdk -y
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH
source ~/.bashrc

# Install Docker
if ! command -v docker &> /dev/null; then
  sudo apt install docker.io -y
  sudo systemctl start docker
  sudo systemctl enable docker
else
  echo "Docker is already installed."
fi

docker run -d \
  --name mysql-store \
  -e MYSQL_ROOT_PASSWORD=MyPassword! \
  -e MYSQL_DATABASE=store_api \
  -v mysql_store_data:/var/lib/mysql \
  -p 3306:3306 \
  mysql:8

echo "Docker and Java 17 installed successfully."

# Build and run the EcommerceAPI application
mvn clean install
mvn spring-boot:run