# BOOK-STORE
## Book Store Application

### DB Tables
![DB architecture](https://github.com/kbabayevv/book-store/assets/125863408/26815859-f473-4ec2-996f-9401f2d5d2b1)

## Tech Stack
- Gradle
- Java 11
- Spring BOOT (Web, Security, Data JPA)
- PostgreSQL
- Liquibase
- ModelMapper
- Docker


## Installation and Running
##### After Cloning project, Open your favorite Terminal in root directory and run these commands.
 First step:
 
    ./gradlew build
 
 Second step:
    
    docker build -t book-store-app:0.0.1 .
 Third step:

    docker-compose up
    

##### default base url: 
      
      localhost:8002/api/book_store
     
## REST API

### Authentication
##### Get access_token to send other requests
`POST {{base_url}}/authentication`

``` 
{
    "email":"email@gmail.com",
    "password":"password12345"
}
```      

### User APIs

| Request | About |
| ------ | ----------- |
| `POST /users`  | Create user. |
| `GET /users/id` | Find user by ID. |

### Book APIs

| Request | About |
| ------ | ----------- |
| `POST /books`  | Create book. |
| `GET /books` | Get all books. |
| `POST /books/reader` | Add new reader as a student. |
| `GET /books/currently-reading` | Find books that student read currently. |
| `DELETE /books/{bookId}` | Author delete own book by book id. |
    


    
