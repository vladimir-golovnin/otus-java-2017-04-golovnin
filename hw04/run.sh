#!/usr/bin/env bash

MEMORY="-Xms512m -Xmx512m -XX:MaxMetaspaceSize=128m"
GC_SERIAL="-XX:+UseSerialGC"
GC_PAR="-XX:+UseParallelGC"
GC_CONC="-XX:+UseConcMarkSweepGC"
GC_G1="-XX:+UseG1GC"
TARGET="-jar target/hw04.jar"

echo Run with Serial GC
java $GC_SERIAL $MEMORY $TARGET
echo Run with Parallel GC
java $GC_PAR $MEMORY $TARGET
echo Run with Concurrent GC
java $GC_CONC $MEMORY $TARGET
echo Run with G1 GC
java $GC_G1 $MEMORY $TARGET
