<div align="center">
  
# PlantCarePal

</div>

PlantCarePal is a Mobile App that is for users who are interested in decorative plans. This app will help users to find information for their decorative plants easily, and with the help of our Plant Health Scanner, they can easily tackle any problems with their plants if it ever infected or attacked by pests.


## PlantCarePal Teams - C241-PS497
<div align="center">
  
| Nama | Path | Bangkit ID | Social Media |
|---------|---------|---------|---------|
| Rio Rezky Seleng | Machine Learning  | M004D4KY2871  | Data 3  |
| Mohd Alfitra Syauqi | Machine Learning  | M006D4KY2753  | Data 6  |
| Virandy Bagaskara Syahwanto | Machine Learning  | M006D4KY1786  | Data 9  |
| Debora Meiliana | Cloud Computing | C242D4KX1012 | Data 12 |
| Muhammad Naufalaudi Syaliandra | Cloud Computing | C006D4KY0154 | [LinkedIn](https://www.linkedin.com/in/naufalsyaliandra/) & [GitHub](https://github.com/naufalliandra) |
| Andreas Matthew | Mobile Development | A242D4KY3968 | Data 18 |
| Willyam | Mobile Development | A242D4KY4312 | Data 21 |

</div>

## Screenshots

<div align="center">

| Splash Screen | Opening Page | Register Page | Login Page | Home Page |
|---------|---------|---------|---------|---------|
|  |   |   |   |   |

| Detect Page | Analyze Result Page | History Page | Guides Page | Guide Detail Page |
|---------|---------|---------|---------|---------|
|  |   |   |   |   |

</div>

## UI/UX
[PlantCarePal UI Design](https://www.figma.com/design/qSRxvICZxb3N3lFodDNiRN/Mockup-Capstone?node-id=0-1&t=N2cDs5MrmuClcNSu-1)

<br>

# Installing PlantCarePale API Server
These are the steps in installing PlantCarePal API

## Install Requirements
[Node.js and NPM](https://docs.npmjs.com/downloading-and-installing-node-js-and-npm)

## Run API Locally

<br>

# How to Deploy to Google Cloud (Cloud Run)
## Requirements

<br>

# PlantCarePal API Reference
### Authentication
| Endpoint | Method | Parameter | Type | Description |
|---------|---------|---------|---------|---------|
| /register | POST | ``` application/json ```  | ``` String ```  |  This endpoint is used for registering a new user. The payload contains fields for username and password. When you fill out the payload and send the request to the server, the server will respond with a 201 Created status if the registration is successful, or a 409 Conflict status if the username already exists.  |
| /login |  POST | ``` application/json ```  |  ``` String ``` | This endpoint is used for user login. The payload contains fields for username and password. You can fill the username field with either the username or email. After filling the payload and sending the request to the server, the server will respond with a 201 Created status if the login is successful, and the response will contain a token. If the username or password is incorrect, the server will respond with a 400 Bad Request status.  |

### Detect
``` detect
/detect
```
| Endpoint | Method | Parameter | Type | Description |
|---------|---------|---------|---------|---------|
| /detect | POST | ``` multipart/form-data ```  | ``` File ```  |  Detects diseases in an uploaded image. Requires JWT token for authentication.  |
| /detect/histories |  GET | -  |  - | Retrieves all detection histories for the authenticated user. Requires JWT token for authentication.  |
| `/detect/histories/{id}`  | GET    | `?id=<id>`                         | `String`           | Retrieves a specific detection history by ID. Requires JWT token for authentication. |
| `/detect/histories/{id}`  | DELETE | `?id=<id>`                         | `String`           | Deletes a specific detection history by ID. Requires JWT token for authentication. |
| `/detect/histories`       | DELETE | -                            | -                  | Deletes all detection histories for the authenticated user. Requires JWT token for authentication. |
