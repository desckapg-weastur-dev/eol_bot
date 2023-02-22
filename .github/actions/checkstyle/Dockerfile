FROM openjdk:17-alpine

RUN apk add --update --no-cache wget unzip curl bash jq

RUN wget -O - -q https://github.com/checkstyle/checkstyle/releases/download/checkstyle-10.7.0/checkstyle-10.7.0-all.jar > /checkstyle.jar

COPY run_checkstyle.sh /run_checkstyle.sh

ENTRYPOINT ["/run_checkstyle.sh"]