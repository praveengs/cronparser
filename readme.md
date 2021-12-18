#Overview

A simple program to report a single line cron expression as below

`*/15 0 1,15 * 1-5 /usr/bin/find`

into it component parts like

```
minute        0 15 30 45
hour          0         
day of month  1 15
month         1 2 3 4 5 6 7 8 9 10 11 12
day of week   1 2 3 4 5
command       /usr/bin/find
```

## How to run the application
The application is build using gradle. The minimum jdk requirement is JDK11.

### Build the application
#### Pre-requisites
Ensure that the variable JAVA_HOME is to set to the JDK_HOME directory. 

For e.g. in a unix box (replace with your installation path)
```shell
export JAVA_HOME=/opt/jdk/jdk-11
export PATH=$JAVA_HOME/bin:$PATH
```
or for windows (replace with your installation path)
```
set path="C:\Program Files\Java\jdk-11.0.12\bin";%path%
set JAVA_HOME="C:\Program Files\Java\jdk-11.0.12"
```
#### Build and test
The following should be run from inside the source directory.

Run the following command to do a clean and build along with tests. 
This will ensure that all the tests are run from a clean slate.

For unix.
```shell
./gradlew clean build test
```
or for windows
```
gradlew.bat clean build test
```

#### Running the application
Once the build command runs, it will generate a jar file which we will use to run
the application from command line.

For e.g. from shell.
```shell
java -classpath ./build/libs/cronparser-1.0-SNAPSHOT.jar org.home.cron.CronParser "*/15 0 1,15 * 1-5 /usr/bin/find"
```
for windows
```
java -classpath .\build\libs\cronparser-1.0-SNAPSHOT.jar org.home.cron.CronParser "*/15 0 1,15 * 1-5 /usr/bin/find"
```