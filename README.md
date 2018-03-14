# Mercedes Tiago Vicente

## How to build and run your application

```
$mvn spring-boot:run initFile.json
```

Application run has an optional argument, a filename which should be included in the resources folder of the project, which initalizes the state of the api.

## How to run your tests

```
$mvn install
```

Tests are automatically run with maven install. I didn't have time to complete all tests, as such bookings don't have tests.

## API documentation

### Booking (Mapping /booking)

**Mapping**|**Input**|**Output** |
:-----:|:-----:|:-----:|
/add (put)|BookingDTO|void|
/get/{idString (String)} (get)|void|BookingDTO|
/cancel/{idString (String)} (put)|reason (String)|void|

### Dealer (Mapping /dealer)

**Mapping**|**Input**|**Output** |
:-----:|:-----:|:-----:|
/add (put)|DealerDTO|void|
/get/{idString (String)} (get)|void|DealerDTO|
/closest/(put)|ClosestDealerDto|DealerDTO|
/closestlist/(put)|ClosestDealerDto|List\<DealerDTO>|
/dealersinsidepolygon/(put)|GetDealersInsidePolygonDto|List\<DealerDTO>|



### Vehicle (Mapping /vehicle)

**Mapping**|**Input**|**Output** |
:-----:|:-----:|:-----:|
/model/{model} (get)|void|List\<VehicleDTO>|
/fuel/{fuel} (get)|void|List\<VehicleDTO>|
/transmission/{transmission} (get)|void|List\<VehicleDTO>|
/dealer/{dealer} (get)|void|List\<VehicleDTO>|

## Explanation about your choices, their limitations and possible improvements

I used Spring Boot to create this project it is the technology I have more experience with.
I also have some experience with JPA and has such I choose to use H2, a database in memory inside the application in order to load/store information. This was a good decision, in my opinion, because I saw it would be necessary to list elements according to certain parameteres, something a relation database excels at.

As for limitations:
* My implementations of the closest/closestList/insidepolygon might be sped up if using custom functions to the database. Since I wasn't sure how to this I ran through the list of elements that had the request attributes with a normal foreach
* As for the function insidepolygon I assume that I can just create a polygon2D without transfering coordinates to (x,y) mapping. I'm not interely sure this is true but I think it is for "contains" method
