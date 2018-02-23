# Project status
![status: inactive](https://img.shields.io/badge/status-inactive-red.svg)

This project is no longer actively maintained, and remains here as an archive of this work.

## Quick Start sample for Instant Buy API

Requires [Apache Maven](http://maven.apache.org) 3.0 or greater, and JDK 6+ in order to run.

### Setup
To Setup the sample, edit src/main/java/com/instantbuy/quickstart/Config.java 

* Given the Sandbox environment, enter values for SANDBOX_MERCHANT_ID and SANDBOX_MERCHANT_SECRET
* Enter the OAUTH_CLIENT_ID and save the file.

Next, enter the OAUTH_CLIENT_ID in ./src/main/webapp/index.html
var OauthClientId = "....";


### Build
To build, run

    mvn package

Building will run the tests, but to explicitly run tests you can use the test target

    mvn test

To start the app, use the [App Engine Maven Plugin](http://code.google.com/p/appengine-maven-plugin/) that is already included in this demo.  Just run the command.

    mvn appengine:devserver

For further information, consult the [Java App Engine](https://developers.google.com/appengine/docs/java/overview) documentation.

To see all the available goals for the App Engine plugin, run

    mvn help:describe -Dplugin=appengine

