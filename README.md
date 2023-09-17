# Batch Samples using Spring Batch

Batch Samples using Spring Batch

<img src="https://github.com/susimsek/spring-batch-samples/blob/main/images/introduction.png" alt="Spring Boot Batch Samples" width="100%" height="100%"/> 

# Spring Batch

Spring Batch follows the traditional batch architecture where a job repository does the work of scheduling and interacting with the job.

A job can have more than one step. And every step typically follows the sequence of reading data, processing it and writing it.

## Prerequisites

* Java 17
* GraalVM 22.3+
* Kotlin
* Maven 3.x
* Kafka


## Build

You can install the dependencies and build by typing the following command

```sh
mvn clean install
```

To compile as native, run the following goal:

```
$ mvn native:compile -Pnative
```

Then, you can run the batch as follows:

```
$ target/spring-batch-samples
```

## Testing

You can run batchlication's tests by typing the following command

```
mvn verify
```


## Code Quality

You can test code quality locally via sonarqube by typing the following command

```sh
mvn -Psonar compile initialize sonar:sonar
```

## Detekt

Detekt a static code analysis tool for the Kotlin programming language

You can run detekt by typing the following command

```sh
mvn antrun:run@detekt
```

## Dependency Vulnerabilities Check

You can check for security vulnerabilities by typing the following command

```sh
$ mvn -Powasp dependency-check:check
```
You can see the generated report at `target/dependency-check-report.html`

## Docker

You can also fully dockerize  the sample applications. To achieve this, first build a docker image of your app.
The docker image of sample app can be built as follows:

```sh
mvn -Pjib verify jib:dockerBuild
```

to build native image
```sh
mvn -Pnative spring-boot:build-image
```

## Deployment with Docker Compose

### Prerequisites for Docker Compose Deployment

* Docker
* Docker Compose

You can deploy batch by running the following bash command


```sh
 sudo chmod +x deploy.sh
```

```sh
 ./deploy.sh -d
```

You can uninstall batch the following bash command

```sh
 ./deploy.sh -d -r
```

## Deployment Kubernetes with Helm

### Prerequisites for Kubernetes Deployment

* Kubernetes
* Helm

You can deploy batch by running the following bash command

```sh
 sudo chmod +x deploy.sh
```

```sh
 ./deploy.sh -k
```

You can uninstall batch the following bash command

```sh
 ./deploy.sh -k -r
```

You can upgrade the batch (if you have made any changes to the generated manifests) by running the
following bash command

```sh
 ./deploy.sh -u
```

# Used Technologies
* Java 17
* Kotlin 
* GraalVM
* Upx
* Docker
* Docker Compose
* Kubernetes
* Helm
* Sonarqube
* Snyk
* CircleCI
* Detekt
* H2
* Kafka
* Spring Boot 3.x
* Spring Boot Batch
* Spring Batch Integration
* Spring Boot Jpa
* Spring Kafka
* Mapstruct