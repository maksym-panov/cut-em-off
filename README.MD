# Cut 'em off!

This application receives your looooong URLs and returns short ones that you can use instead.

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

#### Compile microservices into executable .jar files with Maven (make sure your Maven uses Java 17)
```shell
mvn clean install
```

#### Run discovery server (Java 17)

On Unix
```shell
java -jar discovery-server/target/discovery-server-0.0.0-SNAPSHOT.jar
```

On Windows
```bash
start /b java -jar discovery-server\target\discovery-server-0.0.0-SNAPSHOT.jar
```

#### Run other microservices

On Unix
```shell
start /b java -jar shortcut-registry\target\shortcut-registry-0.0.0-SNAPSHOT.jar &\
start /b java -jar url-registry\target\url-registry-0.0.0-SNAPSHOT.jar &\
start /b java -jar shortener\target\shortener-0.0.0-SNAPSHOT.jar &\
start /b java -jar shortcut-resolver\target\shortcut-resolver-0.0.0-SNAPSHOT.jar
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
