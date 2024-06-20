<div align="center">
  
# PlantCarePal

</div>

PlantCarePal is a Mobile App that is for users who are interested in decorative plans. This app will help users to find information for their decorative plants easily, and with the help of our Plant Health Scanner, they can easily tackle any problems with their plants if it ever infected or attacked by pests.


## PlantCarePal Teams - C241-PS497
<div align="center">
  
| Nama | Path | Bangkit ID | Social Media |
|---------|---------|---------|---------|
| Rio Rezky Seleng | Machine Learning  | M004D4KY2871  | [LinkedIn]() & [GitHub]()  |
| Mohd Alfitra Syauqi | Machine Learning  | M006D4KY2753  | [LinkedIn]() & [GitHub]()  |
| Virandy Bagaskara Syahwanto | Machine Learning  | M006D4KY1786  | [LinkedIn]() & [GitHub]()  |
| Debora Meiliana | Cloud Computing | C242D4KX1012 | [LinkedIn]() & [GitHub]() |
| Muhammad Naufalaudi Syaliandra | Cloud Computing | C006D4KY0154 | [LinkedIn](https://www.linkedin.com/in/naufalsyaliandra/) & [GitHub](https://github.com/naufalliandra) |
| Andreas Matthew | Mobile Development | A242D4KY3968 | [LinkedIn](https://www.linkedin.com/in/andreas-matthew-698b8025b/) & [GitHub](https://github.com/Nox-Matt) |
| Willyam | Mobile Development | A242D4KY4312 | [LinkedIn]() & [GitHub]() |

</div>

<br>

## Screenshots

<div align="center">

| Splash Screen | Opening Page | Register Page | Login Page | Home Page | Post Detect Page | Analyze Page |
|---------|---------|---------|---------|---------|---------|---------|
| ![SplashScreen](https://github.com/debb88/PlantCarePal/assets/110184326/ddf33da2-9ca5-439c-8589-6cad7d860d5f) | ![Opening Page](https://github.com/debb88/PlantCarePal/assets/110184326/acbbf9fe-4f7f-49a4-b8af-de5a59ab858e) | ![register](https://github.com/debb88/PlantCarePal/assets/120155046/9d59dfd1-cde2-40cd-9070-d6384fd56dba)  | ![login](https://github.com/debb88/PlantCarePal/assets/120155046/493f58db-af05-4e2c-93a1-732d1fc679b6)  | ![homepage](https://github.com/debb88/PlantCarePal/assets/120155046/8be5e38c-bc27-4cfa-b841-db61f6095812)  | ![post-photo](https://github.com/debb88/PlantCarePal/assets/120155046/b3c65374-1a8b-48a6-93de-f23bfdc70abe)  | ![analyze-page](https://github.com/debb88/PlantCarePal/assets/120155046/e5a086d8-37a0-4c51-b58e-a2daa359ca8f)  |

| Analyze Result Page | History Page | Discussion Page | Discussion Details Page | Post Question Page | Guides Page | Guide Details Page |
|---------|---------|---------|---------|---------|---------|---------|
| ![analyze-result](https://github.com/debb88/PlantCarePal/assets/120155046/7f109343-aebc-4be0-9eef-d4d967830d5d) | ![history](https://github.com/debb88/PlantCarePal/assets/120155046/2dc7078d-ca96-4daa-8252-807998b97412) | ![discussion-forum](https://github.com/debb88/PlantCarePal/assets/120155046/4cb5df0f-d9cd-47d8-a210-935ab4ba7088) | ![discuss-detail](https://github.com/debb88/PlantCarePal/assets/120155046/ffa13d01-2d29-4544-8e6c-60346462ab1e) | ![post-question](https://github.com/debb88/PlantCarePal/assets/120155046/f11602da-3890-443e-bfbb-1a3f48b4fa95) | ![guides](https://github.com/debb88/PlantCarePal/assets/120155046/c6d92873-3439-4ddf-b9a5-58132c4016c8) | ![guide-details](https://github.com/debb88/PlantCarePal/assets/120155046/2b13541c-a50c-48fc-b1bd-dc3567f8835d) |

</div>

<br>

## UI/UX
[PlantCarePal UI Design](https://www.figma.com/design/qSRxvICZxb3N3lFodDNiRN/Mockup-Capstone?node-id=0-1&t=N2cDs5MrmuClcNSu-1)

<br>

## Installing PlantCarePale API Server
These are the steps in installing PlantCarePal API

### Install Requirements
[Node.js and NPM](https://docs.npmjs.com/downloading-and-installing-node-js-and-npm)

### Run API Locally

<br>

## How to Deploy to Google Cloud (Cloud Run)
### Prerequisites
1. **Google Cloud Account**: [Daftar](https://cloud.google.com/).
2. **Google Cloud SDK**: [Install](https://cloud.google.com/sdk/docs/install).
3. **Docker**: [Install Docker](https://docs.docker.com/get-docker/).
4. **Google Cloud Storage and Firestore**:
   - **Enable Cloud Storage API**: 
     ```sh
     gcloud services enable storage.googleapis.com
     ```
   - **Enable Firestore API**:
     ```sh
     gcloud services enable firestore.googleapis.com
     ```
### Deploy to Cloud Run with SDK
1. **Authenticate with Google Cloud**:
   ```sh
   gcloud auth login
   ```
   
2. **Set Google Cloud project**
   ```sh
   gcloud config set project [YOUR_PROJECT_ID]
   ```
   Replace [YOUR_PROJECT_ID] with your Google Cloud project ID.
   
3. **Enable necessary APIs**
   ```sh
   gcloud services enable artifactregistry.googleapis.com cloudbuild.googleapis.com run.googleapis.com
   ```
   
4. **Clone the repository (if you haven't already)**
   ```sh
   git clone https://github.com/[YOUR_GITHUB_USERNAME]/[YOUR_REPOSITORY].git
   ```
   ```sh
   cd [YOUR_REPOSITORY]
   ```
   Make sure your repository already has a "Dockerfile" file, like this:
   ```sh
   FROM node:20
   WORKDIR /app
   ENV PORT [PORT]
   COPY . .
   RUN npm install
   EXPOSE [PORT]
   CMD [ "npm", "run", "start"]
   ```
   Replace [PORT] with the port you want
   
5. **Create an Artifact Registry**
   ```sh
   gcloud artifacts repositories create [REPOSITORY_NAME] --repository-format=docker --location=asia-southeast2 --async
   ```
   For "location" you can change it according to your needs. In this project, We use the location asia-southeast2

6. **Build and push the Docker image**
   ```sh
   gcloud builds submit --tag asia-southeast2-docker.pkg.dev/${GOOGLE_CLOUD_PROJECT}/[REPOSITORY_NAME]/[IMAGE_NAME]:[TAG]
   ```
   Replace ${GOOGLE_CLOUD_PROJECT} with your project ID, [REPOSITORY_NAME] with the repository name you want, [IMAGE_NAME] with the image name, and [TAG] with the appropriate tag.

7. **Deploy to Cloun Run**
   ```sh
   gcloud run deploy [YOUR_SERVICE_NAME] \
    --image asia-southeast2-docker.pkg.dev/${GOOGLE_CLOUD_PROJECT}/[REPOSITORY_NAME]/[IMAGE_NAME]:[TAG] \
    --platform managed \
    --region [YOUR_REGION] \
    --allow-unauthenticated
   ```
   Replace [YOUR_SERVICE_NAME] with the name of the service you want, ${GOOGLE_CLOUD_PROJECT} with your project ID, [REPOSITORY_NAME] with the name of the repository, [IMAGE_NAME] with the name of the image, [TAG] with the tag, and [YOUR_REGION] with the region where you want to deposit application (for example, us-central1).

   
<br>

## PlantCarePal API Reference
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
| Endpoint                  | Method | Payload                   | Description |
|---------------------------|--------|---------------------------|-------------|
| /forum                  | GET    | -                         | This endpoint retrieves all questions posted in the forum. It requires a JWT token for authentication. |
| /forum/{id}             | GET    | -                         | This endpoint retrieves a specific question by ID from the forum. It requires a JWT token for authentication. |
| /forum                  | POST   | `application/json`        | This endpoint posts a new question in the forum. It requires a JWT token for authentication. The payload should be a JSON object containing `title` and `question`. |
| /forum/{id}/answers     | POST   | `application/json`        | This endpoint posts an answer to a specific question in the forum. It requires a JWT token for authentication. The payload should be a JSON object containing `answer`. |


### Guides
```guide
/guides
```
| Endpoint          | Method | Payload            | Description |
|-------------------|--------|--------------------|-------------|
| /guides         | GET    | -                  | This endpoint retrieves all available guides. Authentication with a JWT token is required. |
| /guides/{id}    | GET    | -                  | This endpoint retrieves a specific guide by its ID. Authentication with a JWT token is required. |




