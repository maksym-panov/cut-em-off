# Cut 'em off!

This application receives your looooong URLs and returns short ones that you can use instead.

# How to scale?

In order to achieve better performance and make sure that system won't die if we should handle up to 100.00 
resolving requests per second, we should firstly think about separating URL-Shortcut and Shortcut-URL databases 
(currently these databases are part of one MongoDB container in Docker). Same thing should be done with Redis cache.

Next we should make process of saving shortcuts and URLs asynchronous - we want to generate new shortcut in `shortener` service,
send it back to user and in the end we are going to send async messages to `url-registry` and `shortcut-registry` services, that
new shorcut and its full URL should be saved in the database. We could also make this process more fault-tolerant via
choosing Redis chache TTL that can give time for message broker and `...-registry` services but also won't lead to data stagnation.

Last but not least, we should choose good load balancer, that will automatically create, manage and dispose additional
instances of microservices depending on current traffic.

## Bootstrap

#### Clone this repository on your local machine
```shell
git clone https://github.com/maksym-panov/cut-em-off.git
```

#### Go inside the project 
```shell
cd cut-em-off
```

#### Start MongoDB and Redis instances with Docker
```shell
docker-compose up -d
```

#### Compile microservices into executable .jar files with Maven (make sure your Maven uses Java version 17)
```shell
mvn clean install
```

#### Run discovery server (Java version 17)

On Unix
```shell
java -jar discovery-server/target/discovery-server-0.0.0-SNAPSHOT.jar &
```

On Windows
```bash
start /b java -jar discovery-server\target\discovery-server-0.0.0-SNAPSHOT.jar
```

#### Run other microservices

On Unix
```shell
java -jar shortcut-registry\target\shortcut-registry-0.0.0-SNAPSHOT.jar &\
java -jar url-registry\target\url-registry-0.0.0-SNAPSHOT.jar &\
java -jar shortener\target\shortener-0.0.0-SNAPSHOT.jar &\
java -jar shortcut-resolver\target\shortcut-resolver-0.0.0-SNAPSHOT.jar
```

On Windows
```bash
start /b java -jar shortcut-registry\target\shortcut-registry-0.0.0-SNAPSHOT.jar
```

```bash
start /b java -jar url-registry\target\url-registry-0.0.0-SNAPSHOT.jar
```

```bash
start /b java -jar shortener\target\shortener-0.0.0-SNAPSHOT.jar
```

```bash
start /b java -jar shortcut-resolver\target\shortcut-resolver-0.0.0-SNAPSHOT.jar
```

## Use the application

To start using the service, you should just make a `POST` request to the URL `http://localhost:8082/api/v1/shortener`
with `application/json` request body that follows the following structure:

```json
{
  "fullLink": "https://www.google.com"
}
```

Or:

```json
{
  "fullLink": "www.google.com"
}
```

Or even:

```json
{
  "fullLink": "https://www.extremely.large.link.that.makes.me/disappointed?in=my&life=dotcom"
}
```

As a response you will receive `application/json` that is similar to this:

```json
{
  "shortcut": "localhost:9999/Facj23txpo"
}
```

And now you can use this generated URL to access your site instead of `https://www.extremely.large.link.that.makes.me/disappointed?in=my&life=dotcom`.
