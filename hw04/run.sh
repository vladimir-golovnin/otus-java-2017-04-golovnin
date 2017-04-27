#!/usr/bin/env bash

MEMORY="-Xms512m -Xmx512m -XX:MaxMetaspaceSize=128m"
GC="-XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 -XX:+ScavengeBeforeFullGC -XX:+CMSScavengeBeforeRemark -XX:+UseParNewGC"
GC_SERIAL="-XX:+UseSerialGC"
GC_PAR="-XX:+UseParallelGC"
GC_CONC="-XX:+UseConcMarkSweepGC"
GC_G1="-XX:+UseG1GC"
GC_LOG=" -verbose:gc -Xloggc:./gc.log -XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps -XX:+PrintGCDetails"
JMX="-Dcom.sun.management.jmxremote.port=15025 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false"

echo Run with Serial GC
java $GC_SERIAL $MEMORY -jar target/hw04.jar
echo Run with Parallel GC
java $GC_PAR $MEMORY -jar target/hw04.jar
echo Run with Concurrent GC
java $GC_CONC $MEMORY -jar target/hw04.jar
echo Run with G1 GC
java $GC_G1 $MEMORY -jar target/hw04.jar
