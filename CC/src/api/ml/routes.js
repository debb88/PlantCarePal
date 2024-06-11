const detectHandler = require('./handler');

const routes = [
    {
        path: '/detect',
        method: 'POST',
        handler: detectHandler.postDetect,
        options: {
            auth: 'jwt',
            payload: {
                maxBytes: 1000000,
                parse: true,
                output: 'stream',
                allow: 'multipart/form-data',
                multipart: true,
            }
        }
    },
    {
        path: '/detect/histories',
        method: 'GET',
        handler: detectHandler.getHistories,
        options: { auth: 'jwt' }
    },
    {
        path: '/detect/histories/{id}',
        method: 'GET',
        handler: detectHandler.getHistoryById,
        options: { auth: 'jwt' }
    },
    {
        path: '/detect/histories/{id}',
        method: 'DELETE',
        handler: detectHandler.deleteHistory,
        options: { auth: 'jwt' }
    },
];

module.exports = routes;
