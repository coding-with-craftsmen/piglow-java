piglow-java
===========

Simple example project for using the piglow from java

## Building

The command `mvn clean package` will result in a fat-jar in the target directory named `piglow-java-1.0-SNAPSHOT.one-jar.jar`

## Running

After copying the jar to your raspberry pi, you can run the application with `java -jar piglow-java-1.0-SNAPSHOT.one-jar.jar`

## Problems

### Blocked i2c port
If you use Raspbian distro the i2c port is disabled by default.
To enable this protocol edit /etc/modprobe.d/raspi-blacklist.conf and remove or comment out (with #) `blacklist i2c-bcm2708`.
Then edit the file /etc/modules and add the lines `i2c-dev` and `i2c-bcm2708`

### User has no rights on i2c port
By default the pi user does not have rights on the i2c port. By installing the i2c-tools `sudo apt-get install i2c-tools`
a i2c group is created. This group can be added to the pi user by `sudo usermod -aG i2c pi`. After rebooting the pi user
should have access to the i2c port