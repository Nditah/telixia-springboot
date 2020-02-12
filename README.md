# telixia-springboot [![Build Status](https://travis-ci.com/Nditah/telixia-springboot.svg?branch=master)](https://travis-ci.com/Nditah/telixia-springboot)

RESTful API for CRUD operations, developed with Spring Boot in Java.

## Function

Six RESTful APIs for the Game Store web application.

API                       | Description         | URL
--------------------------|---------------------|--------------
GET /api/products         | Get all products    | GET [http://localhost:8080/api/products](http://localhost:8080/api/products)
GET /api/products/{id}    | Get a product by ID | GET [http://localhost:8080/api/products/1](http://localhost:8080/api/products/1)
POST /api/products        | Add a new product   | POST [http://localhost:8080/api/products](http://localhost:8080/api/products)
PUT /api/products/{id}    | Update a product    | PUT [http://localhost:8080/api/products/1](http://localhost:8080/api/products/1)
DELETE /api/products/{id} | Delete a product    | DELETE [http://localhost:8080/api/products/1](http://localhost:8080/api/products/1)
POST /api/upload          | Upload an image     | POST [http://localhost:8080/api/upload](http://localhost:8080/api/upload)

## Setup Locally

```bash
git clone https://github.com/Nditah/telixia-springboot.git
mvn spring-boot:run
```
Access http://localhost:8080/api/products in web browser or PostMan, you should get the following JSON as response.
```json
[  
   {  
      "id":3,
      "productName":"Wireless Controller",
      "price":19.99,
      "image":"http://localhost:5000/images/controller.jpg"
   },
   {  
      "id":2,
      "productName":"Wii",
      "price":269.0,
      "image":"http://localhost:5000/images/wii.jpg"
   },
   {  
      "id":1,
      "productName":"Xbox 360",
      "price":299.0,
      "image":"http://localhost:5000/images/xbox360.jpg"
   }
]
```


The available RESTful API on Heroku is
* baseURL: https://telixia-springboot.herokuapp.com/

For example, request https://telixia-springboot.herokuapp.com/api/products to get all products.

