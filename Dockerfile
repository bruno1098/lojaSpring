# Fase de construção
FROM ubuntu:latest AS build

# Atualiza o gerenciador de pacotes e instala o JDK
RUN apt-get update && apt-get install -y openjdk-17-jdk maven

# Cria e define o diretório de trabalho
WORKDIR /app

# Copia o pom.xml e os arquivos de código-fonte para o contêiner
COPY pom.xml /app/
COPY src /app/src/

# Executa o comando de construção do Maven
RUN mvn clean install

# Fase de execução
FROM openjdk:17-jdk-slim

# Expõe a porta 8080
EXPOSE 8080

# Define o diretório de trabalho no contêiner final
WORKDIR /app

# Copia o jar construído da etapa anterior para o contêiner final
COPY --from=build /app/target/loja-0.0.1-SNAPSHOT.jar app.jar

# Define o comando de entrada para executar o aplicativo
ENTRYPOINT ["java", "-jar", "app.jar"]
