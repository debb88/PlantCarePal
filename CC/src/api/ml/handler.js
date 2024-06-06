const detectClassification = require("../../ml/detect");
const crypto = require("crypto");
const db = require("../../config/db");
const { storeImage } = require("../../config/storage");
const jwt = require('jsonwebtoken');

async function postDetect(request, h) {
  try {
    const token = request.headers.authorization.split(' ')[1];
    const decoded = jwt.verify(token, 'my_secret_key');
    const userId = decoded.user.id;

    const userSnapshot = await db.collection('users').doc(userId).get();
    if (!userSnapshot.exists) {
      return h.response({ 
        status: 'error', 
        message: 'User not found',
      }).code(404);
    }

    const { image } = request.payload;
    const { model } = request.server.app;

    const id = crypto.randomUUID();
    
    const imageUrl = await storeImage(image, id).catch(err => {
      return h.response({ 
        status: 'error', 
        message: 'Error storing image'
      }).code(500);
    });

    const { diseasesName, description, percentage, causes, treatment } = await detectClassification(model, image).catch(err => {
      return h.response({ 
        status: 'error', 
        message: 'Error classifying image'
      }).code(500);
    });
    
    const createdAt = new Date().toISOString();

    await db.collection("detections").doc(id).set({
      userId,
      diseasesName,
      percentage,
      description,
      causes,
      treatment,
      createdAt,
      imageUrl,
    });

    const response = h.response({
      status: "success",
      message: "Model is predicted successfully.",
      data: {
        id,
        imageUrl,
        diseasesName,
        percentage,
        description,
        causes,
        treatment,
        createdAt,
      },
    });
    response.code(201);
    return response;

  } catch (error) {
    return h.response({ 
      status: 'error', 
      message: 'Internal server error'
    }).code(500);
  }
}

async function getHistories(request, h) {
  try {
    const token = request.headers.authorization.split(' ')[1];
    const decoded = jwt.verify(token, 'my_secret_key');
    const userId = decoded.user.id;

    const historyCollection = db.collection("detections");
    const snapshot = await historyCollection.where('userId', '==', userId).get();

    if (snapshot.empty) {
      return h.response({
        status: "success",
        message: "History Not Found.",
      }).code(200);
    }

    const histories = snapshot.docs.map((doc) => {
      const data = doc.data();
      const { imageUrl, diseasesName, percentage, createdAt } = data;
      return { id: doc.id, imageUrl, diseasesName, percentage, createdAt };
    });

    return h.response({
      status: "success",
      data: histories,
    }).code(200);

  } catch (error) {
    return h.response({
      status: 'error',
      message: 'Internal server error'
    }).code(500);
  }
}

async function getHistoryById(request, h) {
  try {
    const { id } = request.params;

    const detectRef = db.collection("detections").doc(id);
    const detectDoc = await detectRef.get();

    if (!detectDoc.exists) {
      return h.response({
        status: "fail",
        message: "History not found.",
      }).code(404);
    }

    const detectData = detectDoc.data();
    const { imageUrl, diseasesName, percentage, description, causes, treatment } = detectData;

    return {
      status: "success",
      data: { imageUrl, diseasesName, percentage, description, causes, treatment },
    };

  } catch (error) {
    return h.response({
      status: 'error',
      message: 'Internal server error'
    }).code(500);
  }
}

module.exports = { postDetect, getHistories, getHistoryById };
