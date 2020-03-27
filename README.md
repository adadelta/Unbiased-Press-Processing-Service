# Unbiased Press processing service

This microservice handles processing needed between other services in the system.

## PREREQUISITES

* Java JDK version >= 8

* Clojure >= 1.10.1

* Leiningen >= 2.9.1

## BUILDING

    lein with-profile prod-profile uberjar

## INSTALLING

### Secrets

In your home directory ($HOME) create a file called __secrets.edn__ and add this string to it:

    {:mongo-password-prod MONGO_USER_PASSWORD}

Replace MONGO_USER_PASSWORD with the password for the mongodb user.

### Logging

The service produces two logs:
* *processing_service.log*: Holds all messages from the application with levels up to and including __INFO__.

* *processing_service_error.log*: Holds all messages from the application with level __ERROR__.

If you do nothing, these two logs will be added to ${HOME}/tmp.

There are two more things you can do to have more control of your logging.

__Set log output folder__: By setting a *LOG_DIRECTORY* environment variable with an absolute path to a directory on your file system, you can choose were your logs go.

__Use an external logback.xml file__: Place the logback.xml file found in the __prod__ resource folder somewhere on your file system. You can edit this file as much as you like to change how the logging is handled by the application. Remember to either set the *LOG_DIRECTORY* environment variable or edit the *LOG_HOME* property within the .xml file to point to where you want the logs to go. Next, run the jar file with the logback configuration option shown later.

## RUNNING

    java -jar {NAME_OF_SERVICE_JAR}.jar

or if you are using an external logback file:

    java -jar -Dlogback.configurationFile={PATH_TO_LOGBACK_FILE}/logback.xml {NAME_OF_SERVICE_JAR}.jar

## DEVELOPING & TESTING

### REPL driven development
1. Using VSCode + Calva, open a .clj file and run this command:

    ctrl + alt + c  ctrl + alt + j

2. Choose *Leiningen* and then *devprofile*.

3. To load the current .cls file into the REPL and run this command:

    ctrl + alt + c enter

4. Now start evaluating your heart out.

### Running the server (dev)

To run the server in development mode, run this command:

    lein with-profile dev-profile run

### Running the tests

To run the tests, run this command:

    lein with-profile test-profile test

## License

Copyright © 2020 G. L. Finnbogason
