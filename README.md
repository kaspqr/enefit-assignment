open a terminal and run 'psql -U postgres' (replace postgres with your PostgreSQL username if it's different)
run 'CREATE DATABASE enefit_backend;'
Alternatively, create the database in pgAdmin.

in application.properties, the name of the database is 'enefit_backend',
the owner is 'postgres' and password is 'parool123'

If you have different values, change them accordingly in application.properties on those lines:
spring.datasource.url=jdbc:postgresql://localhost:5432/enefit_backend
spring.datasource.username=postgres
spring.datasource.password=parool123

cd into electricity-consumption
run './gradlew build'

cd back to enefit-assignment-main
run 'psql -U your_username -d your_database -f db/init.sql'
this will fill the database with enough data
just make sure your_username and your_database have values appropriate to you

cd into electricity-consumption
run './gradlew bootRun' to run backend on port 8080

in another terminal, cd into enefit-client
run 'npm install'
then run 'npm run dev' to run frontend on port 5173

log in with username 'johndoe' and password 'password123'

Switch "Stay Logged In" button on if you wish to persist login.

Select year 2024 - all the inserted consumptions are within that year, only on johndoe's account.



________________________________________________________________________


Vite + React with JS in Frontend:
Redux for state management
Using RTK query
Using PersistLogin in state (true or false)
If Stay Logged in is switched on before logging in, they wont be logged out until
the refreshToken expires (30 days)
Otherwise, the user is logged out as soon as the page is refeshed/left
Optionally, you can log out any time by pressing on the logout button in the top right corner
Each metering point has it's own "Card" with Consumption and Cost in separate graphs.
All graphs show monthly info of the selected year.
If a user has no metering points, no Cards are shown.

Testing:
Only testing util functions.
Tried testing components, but had problems rendering Provider with store within testing,
which took me too long to debug, so had to move on.


Spring Boot 3 backend:
First time using Spring Boot/second time using Java.
Tried to follow principles from my Node.js projects.
AuthController: generating accessToken (15min) and refreshToken (30 days) upon login
if an accessToken is expired, a valid refreshToken will send a new accessToken to frontend to retry the request
accessTokens are required with all authenticated requests.
When fetching consumption data, the user is taken from the accessToken,
only data relevant to said user is sent back.

Note: I understand that in reality my algorithm needs to be improved (with caching?),
since it could be going over the same time periods multiple times.

Testing:
3 tests for ConsumptionController, one of which didn't work as I was unable to mock
customersRepository response (always had an empty array), so after a long time of
debugging I commented it out and added a small test for AuthController instead.

.env and application.properties are pushed to git just for the sake of simplicity,
same goes for not encrypting passwords in case someone wishes to add more users,
since registration page has not been made.
