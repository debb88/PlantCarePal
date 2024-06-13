const { Storage } = require('@google-cloud/storage');
const path = require('path');
const credentials = require('../../key-storage.json');
const storage = new Storage({
  projectId: credentials.project_id,
  credentials
});
const bucketName = process.env.GCS_BUCKET_NAME;

async function storeImage(image, id) {
  const bucket = storage.bucket(bucketName);
  const blob = bucket.file(`images/${id}${path.extname(image.hapi.filename)}`);
  const stream = blob.createWriteStream({
    resumable: false,
    contentType: image.hapi.headers['content-type'],
  });

  return new Promise((resolve, reject) => {
    stream.on('finish', async () => {
      await blob.makePublic();
      const publicUrl = `https://storage.googleapis.com/${bucketName}/${blob.name}`;
      resolve(publicUrl);
    });

    stream.on('error', (err) => {
      reject(err);
    });

    stream.end(image._data);
  });
}

async function deleteImage(imageUrl) {
  try {
    const bucket = storage.bucket(bucketName);
    const fileName = imageUrl.split(`https://storage.googleapis.com/${bucketName}/`)[1];
    const file = bucket.file(fileName);

    await file.delete();

    return true;
  } catch (error) {
    throw new Error(`Failed to delete image from storage: ${error.message}`);
  }
}

module.exports = { storeImage, deleteImage };
