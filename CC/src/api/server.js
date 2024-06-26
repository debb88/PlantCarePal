require('dotenv').config();

const Hapi = require('@hapi/hapi');
const authApi = require('./auth');
const guidesApi = require('./guides');
const forumApi = require('./forum');
const mlApi = require('./ml');
const loadModel = require('../ml/loadModel');

const init = async () => {
    const server = Hapi.server({
        port: process.env.PORT || 3000,
        host: 'localhost',
        routes: {
            cors: {
                origin: ['*'],
            },
        },
    });

    await server.register(require('@hapi/inert'));
    await server.register(require('hapi-auth-jwt2'));

    server.auth.strategy('jwt', 'jwt', {
        key: 'my_secret_key',
        validate: async (decoded, request, h) => {
            return { isValid: true };
        },
        verifyOptions: { algorithms: ['HS256'] }
    });

    server.auth.default('jwt');

    const model = await loadModel();
    server.app.model = model;

    authApi(server);
    guidesApi(server);
    forumApi(server);
    mlApi(server);

    await server.start();
    console.log(`Server running on ${server.info.uri}`);
};

init();
