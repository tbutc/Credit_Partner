// src/main/frontend/src/setProxy.js

const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app) {
<<<<<<< HEAD
    app.use(
        '/api',
        createProxyMiddleware({
            target: 'http://localhost:8080',
    changeOrigin: true,
})
);
};
=======
  app.use(
    '/api',
    createProxyMiddleware({
      target: 'http://localhost:8080',	# 서버 URL or localhost:설정한포트번호
      changeOrigin: true,
    })
  );
};
>>>>>>> 4ba319f14ea5383ae281f1f13567f616661956b3
