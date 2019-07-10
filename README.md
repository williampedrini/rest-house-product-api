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

## Running Tests

#### Prerequisites

What things you need to run the tests:

```
Postman
```

#### Postman

1) Download [Postman](https://www.getpostman.com/downloads/) and install it.
2) Import the postman [local](postman/local.environment.json) environment.
3) Import the postman [authorization](postman/authorization.collection.json) collection.
4) Import the postman [product](postman/product.collection.json) collection.

#### Example

Perform a call to generate the authorization token via Authorization -> Generate Token. The result must follow the pattern below:

```
{
    "access_token": "c53edc32-51a3-4666-b8d6-8fb4d521f586",
    "token_type": "bearer",
    "refresh_token": "bf4b311e-16ca-4eb0-b4fc-f6b6e496739f",
    "expires_in": 10799,
    "scope": "read write"
}
```

Perform a call to search for all existing products via Products -> Search all. The result must follow the pattern below:

```
[
    {
        "id": "5d2619c9b899fd1340426e0d",
        "name": "1010",
        "quantity": 100
    }
]
```