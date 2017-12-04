
# Micro Don

Run project with
```
sbt run
```
Run tests with
```
sbt test
```


## Routes

This API provides 3 routes : 
1. List banks (to test the API) : 
```
curl -X GET \
  http://localhost:9000/microdon/banks \
```
1. List a user's transactions : 

The three differents users are stored in-memory in the API. 
Their ids are 1, 2 and 3
```
curl -X GET \
  http://localhost:9000/microdon/users/:id/transactions \
  
Example
curl -X GET \
  http://localhost:9000/microdon/users/1/transactions \
```
1. Aggregate users transactions : 

Dates in body are optional
```
curl -X POST \
  http://localhost:9000/microdon/users/transactions \
  -d '{
	"startingDate": "2015-03-01",
	"endDate": "2015-04-01"
}'
```
