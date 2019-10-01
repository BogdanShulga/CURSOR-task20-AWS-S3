# _*To test this app do next:*_

***
>to save a user:  
POST: http://localhost:8080/aws/s3  
BODY:  
```json
{
    "name": "Bill",
    "surname": "Reamer",
    "lastLoginDate": [
        2019,
        3,
        3
    ],
    "email": "Bill.Reamer.1984@gmail.com",
    "homeworkToIsDone": {
        "homework-1": false,
        "homework-2": false,
        "homework-3": true,
        "homework-4": false,
        "homework-5": false
    }
} 
```
***
>to get all files names from the S3 repository:  
GET: http://localhost:8080/aws/s3/all  
***
>to get a specific file from the S3 repository:  
GET: http://localhost:8080/aws/s3/?fileName=userList.json  
***
>to delete a specific file from the S3 repository:  
DELETE: http://localhost:8080/aws/s3?fileName=Bill-Reamer.json  
***
>to save a migrate file to the S3 repository:  
GET: http://localhost:8080/aws/s3/migrate  
***