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
| Endpoint   | Method | Payload                   | Description |
|------------|--------|---------------------------|-------------|
| /register  | POST   | `application/json`        | This endpoint is used for registering a new user. The payload should be a JSON object with `username` and `password` fields. Upon successful registration, the server responds with a 201 Created status. If the username already exists, it responds with a 409 Conflict status. |
| /login     | POST   | `application/json`        | This endpoint is used for user login. The payload should be a JSON object with `username` and `password` fields. You can use either the username or email for the `username` field. Upon successful login, the server responds with a 201 Created status and includes a token in the response. If the username or password is incorrect, it responds with a 400 Bad Request status. |


### Detect
``` detect
/detect
```
| Endpoint                  | Method | Payload                   | Description |
|---------------------------|--------|---------------------------|-------------|
| /detect                   | POST   | `multipart/form-data`     | This endpoint is used to detect diseases in an uploaded image. It requires a JWT token for authentication. The payload should include an image file (`File`). |
| /detect/histories         | GET    | -                         | This endpoint retrieves all detection histories for the authenticated user. It requires a JWT token for authentication. |
| /detect/histories/{id}    | GET    | -                         | This endpoint retrieves a specific detection history by ID (`id`). It requires a JWT token for authentication. |
| /detect/histories/{id}    | DELETE | -                         | This endpoint deletes a specific detection history by ID (`id`). It requires a JWT token for authentication. |
| /detect/histories         | DELETE | -                         | This endpoint deletes all detection histories for the authenticated user. It requires a JWT token for authentication. |


### Forum
```forum
/forum
```


