1. install jdk >= 1.8
2. install maven
3. create a data folder named is: data and make sure the program has the permission to read/write a file from this path
4. create files CSV with the format the same as the sample.csv file and put these csv file into the data folder in step 3
5. Update the "data.folder" property in application.properties file to make sure it is poinsting to your data folder in step 3
6. Build project: go to the project folder and run in cmd: mvn clean install
7. Start the application using this cmd: mvn spring-boot:run
8. Open the browser and try with this URL: http://localhost:9999/orders to see all orders
9. To get the order by vendor,  try with this URL:  http://localhost:9999/orders/vendor/{vendor}/ -  {vendor} maybe replaced by one in three vendors: HP, DELL, IBM
10. At step 9, in the result return to you, it includes the full path of the CSV file where you can download and review the orders of specified vendor

