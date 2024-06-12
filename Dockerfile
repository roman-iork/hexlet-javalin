FROM gradle:8.7.0-jdk-21-and-22

WORKDIR /HexletJavalin

COPY . .

RUN gradle installShadowDist

CMD ./build/install/HexletJavalin-shadow/bin/HexletJavalin