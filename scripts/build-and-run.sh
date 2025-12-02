#!/usr/bin/env bash

set -e

if ! command -v docker &>/dev/null; then
  echo "Docker is not installed" >&2
  exit 1
fi

./mvnw clean package -DskipTests -V

SPI_JAR=$(find target -maxdepth 1 -type f -name "*.jar" -not -name "original-*.jar" | head -1)
if [[ -z "${SPI_JAR}" ]]; then
  echo "SPI jar not found in the target directory" >&2
  exit 1
fi

SPI_JAR_VERSION=$(basename "${SPI_JAR}" | sed -E 's/.*-([0-9]+\.[0-9]+\.[0-9]+(-[a-zA-Z0-9]+)?)\.jar/\1/')

echo "Built Keycloak provider [version=${SPI_JAR_VERSION}] for Kafka"
JAR_VERSION="${SPI_JAR_VERSION}" docker compose up --build -d