1. install jdk >= 1.8
2. install maven
3. create a data folder named is: data and make sure the program has the permission to read/write a file from this path
4. create files CSV with the format the same as the sample.csv file in the resources folder
5. Build project: go to the project folder and run in cmd: mvn clean install
6. Start the application using this cmd: mvn spring-boot:run
7. Open the browser and try with this URL: http://localhost:9999/orders to see all orders
8. To get the order by vendor,  try with this URL:  http://localhost:9999/orders/vendor/{vendor}/ -  {vendor} maybe replaced by one in three vendors: HP, DELL, IBM
9. At step 8, in the result return to you, its include the full path of the CSV file where you can download and review all order of specified vendor

