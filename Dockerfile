# Use a imagem oficial do OpenJDK para Java 11 como base
FROM openjdk:11-jre-slim

# Diretório de trabalho dentro do contêiner
WORKDIR /app

# Copiar o jar da sua aplicação para o contêiner
COPY target/pw-manager-api.jar /app/pw-manager-api.jar

# Comando para esperar o MySQL iniciar antes de iniciar a aplicação
CMD ["sh", "-c", "while ! nc -z pw-manager-api-mysql 3306; do sleep 1; done; java -jar pw-manager-api.jar"]

# Expor a porta da sua aplicação
EXPOSE 8080