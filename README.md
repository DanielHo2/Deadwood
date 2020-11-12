# Building

To compile, run the following from the project root:

`javac -d ./bin src/*`

Then, to create a jar file, change to the bin/ directory...

`cd bin`

...and run:

`jar cfe Deadwood.jar Deadwood *.class`

This jar file should then be moved to the parent directory.

`mv Deadwod.jar ../`

Then, after changing to this directory...

`cd ..`

...the program can be run with:

`java -jar Deadwood.jar`
