const tf = require('@tensorflow/tfjs-node');
const InputError = require('../exceptions/InputError');

async function detectClassification(model, image) {
    try {
        const tensor = tf.node
            .decodeImage(image._data, 3)
            .resizeNearestNeighbor([150, 150])
            .expandDims()
            .toFloat()
            .div(tf.scalar(255));

        const classes = ['Leaf Spot', 'Powdery Mildew', 'Rust Leaf'];

        const prediction = model.predict(tensor);
        const score = await prediction.data();

        const classResult = score[0] > 0.5 ? 0 : 1;
        const percentage = Math.max(...score) * 100;
        const diseasesName = classes[classResult];
        let treatment;
        let description;
        let causes;

        if (diseasesName === 'Leaf Spot') {
            description = "Leaf spot is a common fungal or bacterial infection that plagues houseplants. It causes dark spots on the leaves and is encouraged by high humidity and overwatering.";
            causes = "Leaf spot can be caused by various fungi or bacteria. It thrives in warm, moist environments and can spread through contaminated soil or water.";
            treatment = "Treatment involves pruning affected leaves, improving soil drainage, avoiding overwatering, and applying fungicidal sprays if necessary.";
        }
        if (diseasesName === 'Powdery Mildew') {
            description = "Powdery mildew is a fungal disease that affects a wide range of plants. It appears as white powdery spots on the leaves and stems.";
            causes = "Powdery mildew thrives in warm, dry environments with high humidity. Overcrowding of plants and poor air circulation can also contribute to its development.";
            treatment = "Treatment includes removing affected leaves, improving air circulation, and applying fungicidal sprays if necessary. Pruning may also be necessary to reduce overcrowding.";
        }
        if (diseasesName === 'Rust Leaf') {
            description = "Rust leaf is a fungal disease that causes rust-colored spots on the undersides of leaves. It is often found in humid conditions.";
            causes = "Rust leaf is often caused by high humidity and poor air circulation. It can also spread through contaminated soil, water, or plant debris.";
            treatment = "Treatment involves removing affected leaves, ensuring proper spacing and air circulation, and applying fungicidal sprays if necessary.";
        }

        return { diseasesName, description, percentage, causes, treatment };
    } catch (error) {
        throw new InputError(`Terjadi kesalahan input: ${error.message}`);
    }
}

module.exports = detectClassification;
