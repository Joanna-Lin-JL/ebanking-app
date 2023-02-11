# ***Ebanking App***
## ***Author: Joanna Lin***

### Implementations:
1. Database access: Spring Data JPA
2. Database creation: MySQL
3. Security: Spring Security with JWT
4. Logging: Log4j2
6. Testing: JUnit + Mockito
<br></br>

### Code structure diagram: 
<img src="./images/code_structure.png" width="280">
<br></br>

### Classes overview: 
1. `controller` contains classes that define the endpoint methods as specified in the section on "Endpoint specification" below. 
2. `generator` contains classes that define the helper functions used across the application
    - `IbanGenerator` defines the method used to randomly generate an iban for a new account to use as unique identifier
    - `ResponseHandler` defines what information should be serialized into JSON to return to the client
3. `model` directory contains classes that define the information stored in each database object
4. `Repository` contains interfaces for getting information from the database
5. `security/config` contains classes that configure the authentication processes
    - `JwtAuthEntryPoint` handles the exceptions during authentication
    - `JwtTokenFilter`parses and validates the authentication tokens to load data
    - `JwtTokenUtils` generates, parses, and validates the JWT
    - `WebSecurityConfig` manages the authentication process
7. `security/payload` contains classes that serve as java objects during the authentication
8. `security/service` contains `UserDetailsService` that retrieves user details during authentication
9. `test` contains classes that define the unit testings for each endpoint and some JPA functions. It uses mock users to test for the authenticated endpoints. 
<br></br>

### Database relationship diagram: 
![Database Relationship Diagram](./images/relationship_diagram.png)
<br></br>

### Endpoint specifications
For authentications *(does not require authentications)*: 
![Authentication Specs](./images/auth_specs.png)
<br></br>

For client data access *(requires authentications)*: 
![Client Specs](./images/client_specs.png)
<br></br>

For account data access *(requires authentications)*: 
![Account Specs](./images/account_specs.png)
<br></br>

For transaction data access *(requires authentications)*: 
![Transaction Spec](./images/transaction_specs.png)
<br></br>

### Authentication Processes
Registration: 
![Registration Process](./images/registration_diagram.png)
<br></br>

Login: 
![Login Process](./images/login_diagram.png)
<br></br>

Data accessing: 
![Data Accessing Process](./images/data_accessing_diagram.png)
