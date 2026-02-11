FROM tomcat:10.1.13-jdk21

# Borrar la aplicación default 
RUN rm -rf /usr/local/tomcat/webapps/*

# Copiar el WAR generado por Maven 
COPY target/taller-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# Exponer el puerto
EXPOSE 8080

# Comando para ejecutar Tomcat
CMD ["catalina.sh", "run"]
