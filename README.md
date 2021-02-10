# DayToDayTest

In this application, two types of roles are defined, one is user and other is admin. An API HTTP request is being given the by the user, 
then the sytem will authenticate the user using basic authentication, and based on role authorize the user, wether it can access it or not.
After this, control will be sent to the application controller, based on request, it will process the request using business logic 
and through the bean repositories it will connect to the database. Data will saved be saved and retrieved with individual bean repositories.
Admin can add products and users, admins functionality is not implemented as of now, whereas, the normal user’s following 3 APIs are implemented.
1. User can add a product rating: POST API to add the rating of specific product used by customer. Request body contains a product name and a rating.
2. User can update a product rating: PUT API to update the rating of specific product used by customer. Request body contains a product name and a rating.
3. Get particular product all ratings and average rating. GET API to get the specific product ratings. Path variable input as product name is given to fetch the details.

Tables Schema 

3 tables are used: USER, PRODUCT, PRODUCT_RATING 

USER:
ID, PASSWORD, TYPE, USERNAME

PRODUCT:
ID, NAME

PRODUCT_RATING:
ID, RATING, USER_ID, PRODUCT_ID
(User_id and product_id are foreign keys)
