# Awards Application - Run Instructions

1. Checkout the code and build it using maven commands "clean install"
2. In main/resources/data.sql replace the value of variable @initialDataFilePath with your local path to the csv file which contains the movie list. The csv file should use semicolons as separators
3. Run the application on your IDE. No arguments or variables are needed. The application should start successfully
4. Using any HTTP request tool like Postman, send a HTTP GET request to http://localhost:8080/awards or just paste this url on your browser and hit enter. You should get the results back in JSON format according to the test specification
