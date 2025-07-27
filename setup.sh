#!/bin/bash

# This script sets up the environment for the EcommerceAPI project.
# It installs Docker, Java 17, and starts a MySQL 8 container.

# sudo apt update
# sudo apt install openjdk-17-jdk -y

export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH

if ! command -v docker &> /dev/null; then
  sudo apt install docker.io -y
  sudo systemctl start docker
  sudo systemctl enable docker
else
  echo "Docker is already installed."
fi

# Start MySQL container if not already running
if ! docker ps --format '{{.Names}}' | grep -q '^mysql-store$'; then
  docker run -d \
    --name mysql-store \
    -e MYSQL_ROOT_PASSWORD=MyPassword! \
    -e MYSQL_DATABASE=store_api \
    -v mysql_store_data:/var/lib/mysql \
    -p 3306:3306 \
    mysql:8
else
  echo "MySQL container 'mysql-store' is already running."
fi

echo "Docker and Java 17 installed successfully."

mvn clean install
mvn spring-boot:run

echo "Setup complete. If you want to use Java 17 in your current shell, run:"
echo "  export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64"
echo "  export PATH=\$JAVA_HOME/bin:\$PATH"