# Sample Rate Limited API (Spring Boot)
This is a sample Spring Boot application that implements a Rate Limit functionality.

## Architecture
The main components of the application are:
* __Controllers__
  * [__HotelController__](src/main/java/com/example/sampleratelimitedapi/controllers/HotelController.java): 
  The main entry point of the application, where the routes are defined.
  The methods here simply call the corresponding HotelService methods, after checking that the request is allowed: 
  this functionality provided by the 
  [__RateLimiter__](src/main/java/com/example/sampleratelimitedapi/utils/RateLimiter.java) class.
* __Models__
  * [__Hotel__](src/main/java/com/example/sampleratelimitedapi/models/Hotel.java): A simple POJO describing an Hotel entity. 
  This class is used to persist and retrieve Hotel information from the Database using JPA.  
* __Repositories__
  * [__HotelRepository__](src/main/java/com/example/sampleratelimitedapi/repositories/HotelRepository.java): 
  This interface defines the method that our Repository should implement. 
  Rather than creating the class directly, we created an interface first so we can inject it 
  where needed using Dependency Injection.
  * [__HotelRepositoryImpl__](src/main/java/com/example/sampleratelimitedapi/repositories/HotelRepositoryImpl.java): 
  Our implementation of the HotelRepository interface.
  The methods here simply call the corresponding HotelJpaRepository methods, and also apply the sorting logic.
  * [__HotelJpaRepository__](src/main/java/com/example/sampleratelimitedapi/repositories/HotelJpaRepository.java): 
  This interface provides access to the Hotel entities stored in the Database.
  By extending the Spring Repository class, we get "for free" the methods "findByCityIgnoreCase" and "findByRoomIgnoreCase"
  that return a List of Hotel filtered by the corresponding city / room.
* __Services__
  * [__HotelService__](src/main/java/com/example/sampleratelimitedapi/services/HotelService.java): 
  This interface defines the method that our Service should implement. 
  Rather than creating the class directly, we created an interface first so we can inject it 
  where needed using Dependency Injection.
  * [__HotelServiceImpl__](src/main/java/com/example/sampleratelimitedapi/services/HotelServiceImpl.java): 
  Our implementation of the HotelService interface.
  The methods here simply call the corresponding HotelRepository methods.
* __Utils__
  * [__RateLimiter__](src/main/java/com/example/sampleratelimitedapi/utils/RateLimiter.java): 
  A simple implementation of a Rate Limiter (Max N requests in M milliseconds).
  This class keeps reference of the timing of the last N requests using a Queue: 
  when we reach the Nth request, if the time difference between the 0th and the Nth requests is less than M, 
  then the request gets blocked. Also, that endpoint becomes unavailable for __RateLimiter.API_BLOCKED_TIME_MS__.
  
Also:
* This Spring Boot application uses an __H2 in-memory database__ that is accessed using __JPA__. 
The Database gets build automatically by the Spring Framework, and seeded using __/src/main/resources/data.sql__. 

## Testing
Every component of the application is covered by Unit Tests: all the components are tested in isolation 
by injecting mocked instances (created with Mockito) to the constructor.

We also have several Integration Tests 
(in the [ApplicationTests](src/test/java/com/example/sampleratelimitedapi/ApplicationTests.java) class)
that verify the correct behaviour of the application by simulating HTTP calls to the API.

## Endpoints
The application expose two endpoints:
* __GET /city/{city}__ 
  * _optional_: "?sortByPrice=ASC" (or DESC)
  * This endpoint returns all the Hotel in a specific city
* __GET /room/{room}__
  * _optional_: "?sortByPrice=ASC" (or DESC)
  * This endpoint returns all the Hotel with a specific room
  
Here is some examples of HTTP calls to the API:
* Retrieve all the hotels in Bangkok, order by price.
  * (__GET__) localhost:8080/city/Bangkok?sortByPrice=ASC
* Retrieve all the hotels with "Deluxe" rooms, order by price (reversed).
  * (__GET__) localhost:8080/room/Deluxe?sortByPrice=DESC

## Run the application
To run the application with Gradle:

```sh
$ gradlew bootRun
```

The application will be accessible at http://localhost:8080 from your browser. 

The port can be easily modified by changing the __server.port__ value 
in the [application.properties](src/main/resources/application.properties) file.  

## Test the application
To test the application with Gradle:

```sh
$ gradlew clean test
```

The result of the tests will be printed once all the test are executed.
