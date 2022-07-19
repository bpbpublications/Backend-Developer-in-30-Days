FROM node:16
# create app directory
WORKDIR /usr/src/app
# copy all node related files
COPY package*.json ./
# install application dependencies
RUN npm install
# copy application into container
COPY views/* views/
COPY public/images/*.jpeg public/images/
COPY server.js server.js
# expose HTTP port
EXPOSE 3000

CMD [ "node", "server.js" ]
