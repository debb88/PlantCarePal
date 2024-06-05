const db = require('../../config/db');

const getAllGuides = async (request, h) => {
    try {
        const guidesSnapshot = await db.collection('guides').get();
        const guides = guidesSnapshot.docs.map((doc) => {
            const { title, imageUrl } = doc.data();
            return { id: doc.id, imageUrl, title };
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
        const guideData = guideDoc.data();

        const contentSnapshot = await guideRef.collection('content').get();
        const content = contentSnapshot.docs.map((doc) => {
            const { opening, 'step1-title': step1Title, 'step1-body': step1Body, 'step2-title': step2Title, 'step2-body': step2Body, 'step3-title': step3Title, 'step3-body': step3Body, 'step4-title': step4Title, 'step4-body': step4Body, 'step5-title': step5Title, 'step5-body': step5Body } = doc.data();
            return { id: doc.id, opening, step1Title, step1Body, step2Title, step2Body, step3Title, step3Body, step4Title, step4Body, step5Title, step5Body };
        });

        const guideDetail = {
            id: guideDoc.id,
            ...guideData,
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
