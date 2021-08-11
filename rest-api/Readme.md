##     

REST stands for representational state transfer very important
link : https://ninenines.eu/docs/en/cowboy/2.9/guide/rest_principles/  
https://serverfault.com/questions/994319/what-is-the-difference-between-a-proxy-server-and-a-gateway-server

## VERBS / METHODS

1. GET
2. POST
3. DELETE
4. PUT

## Features of the REST

1. Resource based
2. Stateless
3. Context is provided in the each api request
4. Session like features are simulated using the session id

## REST response

1. 1xx
2. 2xx success
3. 3xx redirects
4. 4xx client error
5. 5xx Server error

##

1. URI should use noun instead of the verbs
2. While using the long name for resources, use underscore or hyphen. Avoid using spaces between words. For example, to
   define authorized users resource, the name can be “authorized_users” or “authorized-users”.
3. The URI is case-insensitive, but as part of best practice, it is recommended to use lower case only.
4. Use appropriate HTTP methods like GET, PUT, DELETE, PATCH, etc. It is not needed or recommended to use these method
   names in the URI. Example: To get user details of a particular ID, use /users/{id} instead of /getUser
5. Use the technique of forward slashing to indicate the hierarchy between the resources and the collections. Example:
   To get the address of the user of a particular id, we can use: /users/{id}/address

## Difference between SOAP and REST

1. REST is architectural style
2. REST support both json and xml format
3. Resources 
