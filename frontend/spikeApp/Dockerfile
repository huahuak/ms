# syntax=docker/dockerfile:1
FROM node:14.21.0-alpine3.16 
COPY package.json pnpm-lock.yaml /usr/src/ms-front-user/
WORKDIR /usr/src/ms-front-user
RUN npm install -g pnpm \
  && pnpm install;

COPY . /usr/src/ms-front-user/
ENTRYPOINT [ "pnpm", "run", "serve" ]