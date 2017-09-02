#!/usr/bin/env bash

cd Cache
mvn install
cd ../DbService
mvn install
cd ../MessageSystem
mvn install
cd ../Frontend
mvn install
cd ../DbServer
mvn package
