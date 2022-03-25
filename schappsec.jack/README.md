![](src/main/logo/jack.svg)
# Jack's Advanced Container Kaleidoscope

*Plug into your Linux processes*

Processes are essential in Linux. They are the unit for resource allocation and isolation. Lots of useful information is exposed to userland through `procfs` which is mounted on `/proc/`. Many of the standard command line tools, such as `ps` and `netstat`, works by traversing `/proc/<pid>/`. JACK does the same and provides a Java SDK and a GUI to present the information. 

**THIS IS EXPERIMENTAL WORK**
```shell script
$ ../gradlew run
```
