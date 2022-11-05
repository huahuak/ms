# syntax=docker/dockerfile:1
FROM node:14.21.0-alpine3.16
COPY . /usr/src/ms-front-user
WORKDIR /usr/src/ms-front-user
RUN npm install -g pnpm \
  && pnpm install;
ENTRYPOINT [ "pnpm", "run", "serve" ]