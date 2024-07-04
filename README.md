![](Project_Logo.png)

# Team A - Event Management System

## What is it?

This application aims to solve event management problems:

- **Tracking events** \
Hectic way of tracking the events manually.

- **Managing records** \
Updating, editing and managing records of the data is bothersome.

- **Analysing predictions** \
Updating, editing and managing records of the data is bothersome.

## Features

- **Closing the gap** \
Our product makes the client lives easier which offers some extra cool features. Simple design that gives the client the targeted information they need.

- **Easy user management** \
The School of Healthcare and Sciences can manage the applications very easily with account based authentication.

- **Ease of use** \
Security and admin team can edit, add and delete records with a click

- **Analyse and display predictions** \
Can predict future data based on data analysis

## Dependencies

|Dependency| Version |
|-----------|------|
|Java| 17   |
|Springboot| 3.3.1 |
|spring.jpa.database-platform|org.hibernate.dialect.H2Dialect      |

## How to connect to our hosted database
Host:  \
Port:  \
User: \
Password: 

Other dependencies are already mentioned in the **build.gradle** and **application.properties** files.

## How to build
Unzip the **<name>>.zip**, open in IDE and hit run **EventmanagementApplication** file.

---

## Credits
- Haoxuan Zhang - Developer
- Snigdha Banani - Developer
- Hongyang Chen - Developer
- Nithin Narayanan Kutty - Developer
- Shuoyan Zhao - Developer

## Special thanks to
- Bootstrap, for making stylesheets easier for us
- Fontawesome, for providing high quality scalable icons

## Test cases
In order to run the following tests successfully:
- <name_of_file>.java
- <name_of_file>.java
- <name_of_file>.java

We have to change the value of `id` according to the database values in the following code:

This is an example of an ID in these files:
```java
    @Test
    public void TestGetAccountById(){
        Accounts response =accountsService.getAccountById(6);
        Assertions.assertThat(response.getId()).isEqualTo(6);
    }
```

You only need to change the value of the ID corresponding to the database connected, in this example it is `6`