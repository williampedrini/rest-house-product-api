# Rest House API

This project contains all the exposed APIs, related to all the existing products in a rest house.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

What things you need to install the software and how to install them:

```
Java 12
Gradle 5.5
Lombok Plugin 1.18.6 
```

#### INSTALLING

Install the tool for managing parallel versions of multiple Software Development Kits [SDK MAN](https://sdkman.io/install).

##### JAVA

Execute the following command:

```
sdk install java 12.0.1-sapmchn
```

##### GRADLE

Execute the following command:

```
sdk install gradle 5.5
```

##### DOCKER

1. Download [Docker](https://www.docker.com/products/docker-desktop)

2. Follow the instructions provided by the wizard.

#### Lombok Plugin

Please follow the steps defined on the following links, depending on your IDEA.

* [Lombok Eclipse](https://projectlombok.org/setup/eclipse) - Installing lombok on Eclipse.
* [Lombok Intellij](https://projectlombok.org/setup/intellij) - Installing lombok on Intellij.

### Running in Development Mode

To run the API execute the following command:

```
 gradle -Penvironment=development bootRun
```