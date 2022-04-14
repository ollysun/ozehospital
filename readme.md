OZE Hospital Project

clone and run the project

``mvn spring-boot:run`` to initialize the H2 database

use this link to view the patient database

```http://localhost:4040/h2```
<p>details</p> 

``jdbcurl = jdbc:h2:mem:hospitaldb;``
``username: sa``
``password: sa``

The following are the list of Endpoints available on the postman collection.
``` 
Get All Staff: http://localhost:4040/api/staff 
```
```
Create Staff:  http://localhost:4040/api/staff
```
```
{
"name":"mikestand",
"registerDate":"2011-09-07"
}
```
```
Update Staff:  http://localhost:4040/api/staff
```
```
{
    "staffUUid":"16013414-a157-401a-b159-e1f7f94ea47d",
    "name":"moses"
}
```
`` 
Get all patient up to 2 years:  
``
```
http://localhost:4040/api/patient?staffUuid={value}
value is UUID generated when creating staff
```

``Download specific patient Profile``
```
http://localhost:4040/api/patient/download/?staffUuid={value1}&patientId={value2}
value1: staff UUID,  value2: patient id to dwonload
```
```
Note : generate the csv , it is good to run the endpoint on browser. 
Postman cannot create csv file 
```

`` Delete multiple Profile between a date range ``
```
http://localhost:4040/api/patient?staffUuid={value1}&from={value2}&to={value3}
```
```
value1: staff uuid, value2: from date, value3: to date
```

The project has two unit test from Staff Controller and Patient Controller

In case you need some clarification: you can send mail to ``ollysun@gmail.com``