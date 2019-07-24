FROM openjdk:jre-alpine


ENV DOWNLOAD http://www.h2database.com/h2-2019-03-13.zip
ENV DATA_DIR /opt/h2/data

RUN apk add --no-cache curl

RUN mkdir -p ${DATA_DIR} \
    && curl ${DOWNLOAD} -o h2.zip \
    && unzip h2.zip -d /opt/ \
    && rm h2.zip

EXPOSE 9092 5435 8082

WORKDIR ${DATA_DIR}

CMD java -cp /opt/h2/bin/h2*.jar org.h2.tools.Server \
 	-web -webAllowOthers \
 	-tcp -tcpAllowOthers \
 	-baseDir ${DATA_DIR}