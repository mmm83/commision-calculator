# commission-calculator
Kudos to tutorials authors:
1. https://kotlinlang.org/docs/jvm-spring-boot-restful.html
2. https://medium.com/techwasti/spring-boot-mongodb-rest-api-using-kotlin-47e49729bb21

## Running tests
Unit tests written using embedded mongodb. To run tests execute gradle test task.
```shell
gradle test
```

## Database
Created MongoDB database on using free account on https://cloud.mongodb.com

To connect to database update [application.properties](src/main/resources/application.properties) with connection properties, e.g.:
```properties
spring.data.mongodb.uri=mongodb+srv://test-user:<password>@cluster0.6ovbl.mongodb.net/commision-calculator
```

## Import csv data
Data imported using following commands in git bash console:
```bash
./mongoimport --uri mongodb+srv://test-user:<password>@cluster0.6ovbl.mongodb.net/commision-calculator --collection fee_wages --type CSV --file ./fee_wages.csv --headerline
./mongoimport --uri mongodb+srv://test-user:<password>@cluster0.6ovbl.mongodb.net/commision-calculator --collection transactions --type CSV --file ./transactions.csv --headerline
```

## Problems
```
javax.net.ssl.SSLHandshakeException: extension (5) should not be presented in certificate_request

//solved using VM option
-Djdk.tls.client.protocols=TLSv1.2
```
https://mongodb.github.io/mongo-java-driver/4.1/driver/tutorials/ssl/#ocsp-stapling

## Development tips

To prevent uploading database credentials to repo following command can be used:
```shell script
git update-index --assume-unchanged src/main/resources/application.properties
```
https://stackoverflow.com/a/7658676/10950272

## TODOs
1. Simple HTTP authentication for endpoint, spring security should do the job
2. Consider load of app, first ideas would be:
   - build query to get proper result directly from database
   - cache results
3. Log request to database - add log repository and log Transaction entity to it in each request. Or simplified Transaction entity with just customerId, total fee and request date.

Other things to consider would be to add failing scenarios test cases and handle them properly.