# Test order service application
# _Description_

## Models:
### Model Order
Contains next fields:
- creationTime (final, can't be changed, initializes automatically by LocalDateTime.now() value at the moment of the constructor call);
- id;
- item;
- quantity;
- totalPrice (product of item's price and of quantity);

### Model Item
Contains next fields:
- id;
- category (limited list, ENUM);
- price;
- quantity;
- description;

### ENUM Category
List of available categories;

## Controllers:

##1) _Order controller_

**POST** request(`/orders`):
- Allows to create a new order with the existing quantity of items in a special category;
- If requested quantity of items is greater than available in DB, throws exception with a message about available quantity of requested item;
- If order is created, automatically reduces quantity of available items in DB;
- Before you create an order, automatically removes from DB all invalid orders with items in the same category and returns items from deleted orders back to DB;
- Order is invalid, if it was created more than ten minutes ago;

Example of request body:
`{"quantity":"2",
"itemId":1}`
(quantity must be greater than 0)

**DELETE** request(`/orders/{id}`)
- If order with requested id exists, deletes it and returns all items from this order to DB;

##2) _Item controller_

**POST** request (`/items`)
- Allows to create a new item in DB;
- Before creation deletes all invalid orders with items in the same category and returns items from deleted orders back to DB;
  Example of request body:
  `{"model": "s8",
  "price": 200,
  "description": "Samsung Galaxy S8",
  "category": "PHONES",
  "quantity": 2}`
  (category should be chosen between existing ENUMS and should be written in upper case)

**GET** request (`/items/with-lowest-price?category="category_name"&quantity="quantity_name"`)
- Returns requested quantity of the cheapest item in a separate category of items;
- If requested quantity is greater than available in DB, returns available quantity;
- If quantity of the cheapest item = 0, returns next cheapest item in category, which quantity is bigger than 0;
- If there are 2 or more items with the same price, returns items in alphabetical order by model;
- Category in a request can be written in any case("PHONES", "phones", "Phones" etc.).

##3) _Data inject controller_
Injects prepared items into H2 DB;

# _How to start_:

    1) Clone this repository;
    2) Run `src/main/java/com/example/orderservice/OrderServiceApplication.java`;
    3) You can inject prepared items by sending GET request into `/inject` URL or by using script from `src/main/resources/insert-items.sql` file in H2 DB;
    4) Tables `orders` and `items` are creating automatically after running the program;
    5) To enter into H2 console use login and password from `src/main/resources/application.properties` file (by default - "user" and "password").

_P.S.: App is based on H2 in memory DB and covered by JUnit v5 tests._