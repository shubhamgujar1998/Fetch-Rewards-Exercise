# Fetch rewards coding exercise created using Spring boot 2.4.3

To download the application on your local machine:

git clone https://github.com/shubhamgujar1998/Fetch-Rewards-Exercise

**Software Requirements:**

-   Java 8

-   POSTMAN

**Running by Command line:**

Open root folder in terminal where you will see pom.xml and src folder
and execute the following commands:
```sh
mvn compile

mvn clean install

mvn exec:java -Dexec.mainClass=com.shubham.FetchRewardsExerciseApplication
```
**Running by Eclipse or IntelliJ IDE:**

Import the project in Eclipse or IntelliJ as Maven project to run the
Spring Boot Application

**Using POSTMAN to check our endpoints:**

1.  /saveUser (http://localhost:8080/saveUser)

This is a POST route to add payer transaction with the following
attributes:

id: int, payer: String, points: int, timestamp: String

Attribute id is auto generated so no need to input in the body of
POSTMAN, and Timestamp is using String datatype which is then
converted to Date using SimpleDateFormat in Java8

Input the following JSON format in POSTMAN one by one:

> { \"payer\": \"DANNON\", \"points\": 1000, \"timestamp\": \"2020-11-02T14:00:00Z\" }
> 
> { \"payer\": \"UNILEVER\", \"points\": 200, \"timestamp\": \"2020-10-31T11:00:00Z\" }
> 
> { \"payer\": \"DANNON\", \"points\": -200, \"timestamp\": \"2020-10-31T15:00:00Z\" }
> 
> { \"payer\": \"MILLER COORS\", \"points\": 10000,\"timestamp\": \"2020-11-01T14:00:00Z\"}
> 
> { \"payer\": \"DANNON\", \"points\": 300, \"timestamp\": \"2020-10-31T10:00:00Z\" }



2.  /balance (http://localhost:8080/balance)

This is a GET route to show all the balances of payer and their points

> {
> 
> \"UNILEVER\": 200,
> 
> \"MILLER COORS\": 10000,
> 
> \"DANNON\": 1100
> 
> }



3.  /deduct (http://localhost:8080/deduct)

This is a POST route to deduct the oldest points first. Send a POST
request body:
```sh
{
   	 	"points": 5000
}
![image](https://user-images.githubusercontent.com/43499410/109847260-97a07e80-7c14-11eb-94b8-b9dc7966f912.png)

```
The response received below shows how the points are deducted with
respect to transaction date:

> {
> 
> \"DANNON\": -100,
> 
> \"UNILEVER\": -200,
> 
> \"MILLER COORS\": -4700
> 
> }



4.  /users (http://localhost:8080/users)

This is a GET route which retrieves the user transaction history after
adding new user or deducting points from the user so as to keep track on
the transaction records

The application runs locally, but also stores transaction history on H2
database which is SQL and in-memory database. Accessing the database is
optional but also provides a list of all the transaction history per
Payer.


**To access the database:**

-   Go to this url: <http://localhost:8080/h2/>

-   Click connect

-   Select the USER database and run the query

![image](https://user-images.githubusercontent.com/43499410/109846125-7ee39900-7c13-11eb-962e-ed46bda43c6f.png)

