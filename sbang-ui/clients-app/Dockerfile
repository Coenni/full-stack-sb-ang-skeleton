#FROM node:alpine AS build-stage
#WORKDIR /app
#COPY package.json ./
#RUN npm install --legacy-peer-deps
#COPY . .
#RUN npm run build
#
#### STAGE 2: Run ###
#FROM nginx as production-stage
#COPY default.conf /etc/nginx/conf.d/default.conf
#COPY --from=build-stage /app/dist/clients-app /usr/share/nginx/html
#EXPOSE 80
#
#ENTRYPOINT ["nginx","-g","daemon off;"]
# Build stage
FROM node:14 as build-stage
COPY ./ /usr/src/
WORKDIR /usr/src
RUN npm install --legacy-peer-deps
RUN npm run ng build --prod

# Compiled app based on nginx
FROM nginx:1.19
COPY --from=build-stage /usr/src/dist/clients-app/ /usr/share/nginx/html
COPY /nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
