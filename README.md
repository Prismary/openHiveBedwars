# openHiveBedwars
An open source re-implementation of the Bedwars plugin used on the now closed HiveMC Java Edition server

## Contributing (on Windows)
If you would like to contribute to this project, you need to obtain all project dependencies after cloning this 
repository to your machine and setting up an IDE of your choice. This project uses Maven to handle dependencies, 
so as long as you have unrestricted access to the internet, you should be fine for the most part. However, due to 
licensing issues with compiled artifacts of Spigot (or Bukkit) server JAR files, you will need to compile those 
manually and add them to your local Maven repository.

Fortunately, there is a tool which makes this easy to do: [BuildTools](https://www.spigotmc.org/wiki/buildtools/) 
enables you to automatically download and build any required server artifact (this project requires 
Spigot 1.8.8-R0.1).
After completing the build process, the only thing left to do is adding the compiled JAR file to your local Maven 
repository, such that it can be accessed in order to build this plugin from source. This can be achieved by running 
the following command in the Windows Commandline:
```
mvn install:install-file \
-Dfile=<path-to-server-jar-file> \
-DgroupId=org.spigotmc \
-DartifactId=spigot \
-Dversion=1.8.8-R0.1-SNAPSHOT \
-Dpackaging=jar \
-DgeneratePom=true
```
Refer to the [Maven Documentation](https://maven.apache.org/plugins/maven-install-plugin/usage.html#The_install:install-file_goal)
for additional information, if required.

After completing these steps, you should be all set to start compiling the plugin from source and contributing yourself.
