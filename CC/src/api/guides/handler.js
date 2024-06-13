const db = require('../../config/db');

const formatDate = (timestamp) => {
    const date = timestamp.toDate();
    const day = String(date.getDate()).padStart(2, '0');
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const year = date.getFullYear();
    return `${day}-${month}-${year}`;
};

const getAllGuides = async (request, h) => {
    try {
        const guidesSnapshot = await db.collection('guides').get();
        const guides = guidesSnapshot.docs.map((doc) => {
            const { title, imageUrl, publishedAt } = doc.data();
            const formattedPublishedAt = formatDate(publishedAt);
            return { id: doc.id, imageUrl, title, publishedAt: formattedPublishedAt };
        });

        return { 
            status: 'success', 
            data: guides 
        };
    } catch (error) {
        return h.response({ 
            status: 'error',
            message: 'Internal Server Error' 
        }).code(500);
    }
};

const getGuideById = async (request, h) => {
    try {
        const { id } = request.params;
        const guideRef = db.collection('guides').doc(id);
        
        const guideDoc = await guideRef.get();
        if (!guideDoc.exists) {
            return h.response({
                status: 'fail',
                message: 'Guide not found'
            }).code(404);
        }

        const { title, imageUrl, publishedAt } = guideDoc.data();
        const formattedPublishedAt = formatDate(publishedAt);
        
        const contentSnapshot = await guideRef.collection('content').get();
        const content = contentSnapshot.docs.map((doc) => {
            const { 'step1-title': step1Title, 'step1-body': step1Body, 'step2-title': step2Title, 'step2-body': step2Body, 'step3-title': step3Title, 'step3-body': step3Body, 'step4-title': step4Title, 'step4-body': step4Body, 'step5-title': step5Title, 'step5-body': step5Body } = doc.data();
            return { id: doc.id, step1Title, step1Body, step2Title, step2Body, step3Title, step3Body, step4Title, step4Body, step5Title, step5Body };
        });

        const guideDetail = {
            id: guideDoc.id,
            title,
            imageUrl,
            publishedAt: formattedPublishedAt,
            content,
        };

        return { 
            status: 'success', 
            data: guideDetail,
        };
    } catch (error) {
        return h.response({ 
            status: 'error', 
            message: 'Internal Server Error' 
        }).code(500);
    }
};

module.exports = {
    getAllGuides,
    getGuideById
};
