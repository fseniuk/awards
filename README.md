# Awards Application - Run Instructions

1. Checkout the code and build it using maven commands "clean install"
2. The csv file should be placed inside the project's root directory and use semicolons as separators
3. Run the application on your IDE. No arguments or variables are needed. The application should start successfully
4. Using any HTTP request tool like Postman, send a HTTP GET request to http://localhost:8080/awards or just paste this url on your browser and hit enter. You should get the results back in JSON format according to the test specification
5. To run the integration test just make sure the csv file is in the project's root directory. There is a single integration test which tests the whole functionality of the application
