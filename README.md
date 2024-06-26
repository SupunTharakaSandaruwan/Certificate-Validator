Steps
- Clone the Repo
```
git clone https://github.com/SupunTharakaSandaruwan/Certificate-Validator.git
```
- Build the project
```
mvn clean install -Dmaven.test.skip=true
```
- Create config.properties file with the following values
```
cert.location=pathToCertLocation.cer
keystore.location=pathToTrustStore
keystore.password=passwordOfTheTrustStore
```
- Keep the config.properties file under the same location of the jar file and Execute the jar
```
java -jar cert-validator-1.0-SNAPSHOT.jar
```
  
