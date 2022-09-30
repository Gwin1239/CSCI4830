#!/bin/bash
rm -fr eclipse-src/
git clone https://github.com/boguo-uno/eclipse.jdt.core.git eclipse-src
./batch-process1.sh ./eclipse-src/org.eclipse.jdt.apt.core/src/org/eclipse/jdt/apt/core/util


