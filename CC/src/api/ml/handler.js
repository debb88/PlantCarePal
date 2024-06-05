const detectClassification = require("../../ml/detect");
const crypto = require("crypto");
const db = require("../../config/db");

async function postDetect(request, h) {
  const { image } = request.payload;
  const { model } = request.server.app;

  const { diseasesName, description, percentage, causes, treatment } =
    await detectClassification(model, image);
  const id = crypto.randomUUID();
  const createdAt = new Date().toISOString();

  await db.collection("detections").doc(id).set({
    diseasesName,
    percentage,
    description,
    causes,
    treatment,
    createdAt,
  });

  const response = h.response({
    status: "success",
    message: "Model is predicted successfully.",
    data,
  });
  response.code(201);
  return response;
}

async function getHistories(request, h) {
  const historyCollection = db.collection("detections");
  const snapshot = await historyCollection.get();

  if (snapshot.empty) {
    return h
      .response({
        status: "success",
        message: "History Not Found.",
      })
      .code(200);
  }

  const histories = snapshot.docs.map((doc) => ({
    id: doc.id,
    history: {
      ...doc.data(),
      id: doc.id,
    },
  }));

  return h
    .response({
      status: "success",
      data: histories,
    })
    .code(200);
}

async function getHistoryById(request, h) {
  try {
    const { id } = request.params;

    const detectRef = db.collection("detections").doc(id);
    const detectDoc = await detectRef.get();

    if (detectDoc.empty) {
      return h
        .response({
          status: "fail",
          message: "History not found.",
        })
        .code(404);
    }

    const detectData = detectDoc.data();
    return {
      status: "success",
      data: detectData,
    };
  } catch (error) {
    return h
      .response({
        status: "error",
        message: "Internal Server Error",
      })
      .code(500);
  }
};

module.exports = { postDetect, getHistories, getHistoryById };
