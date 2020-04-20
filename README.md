## SSL Echo server client

- PEM RSA private key

openssl genrsa -des3 -passout pass:x -out server.pass.key 2048

openssl rsa -passin pass:x -in server.pass.key -out server.key

rm server.pass.key

- Create the Certificate Signing Request (CSR) utilizing the RSA private key

openssl req -new -key server.key -out server.csr

Example filling:
-----
```Country Name (2 letter code) []:VN
State or Province Name (full name) []:Hanoi
authorityKeyIdentifier=keyid,issuer
Locality Name (eg, city) []:Hanoi
Organization Name (eg, company) []:Example
Organizational Unit Name (eg, section) []:ORG
Common Name (eg, fully qualified host name) []:example.com
Email Address []:test@example.com```

- create v3.ext file

authorityKeyIdentifier=keyid,issuer
basicConstraints=CA:FALSE
keyUsage = digitalSignature, nonRepudiation, keyEncipherment, dataEncipherment
subjectAltName = @alt_names

[alt_names]
DNS.1 = example.com

 
Setting the DNS.1 value in v3.ext file to be same as the Common Name that you mentioned while generating the 
certificate signing request, in this case = example.com

- Gen self-signed cert using openssl

openssl x509 -req -sha256 -extfile v3.ext -days 365 -in server.csr -signkey server.key -out server.crt

- Create java user keyStore

keytool -genkey -alias example.com -keyalg RSA -keystore exampleKeyStore.jks -keysize 2048

- import cert to keyStore

keytool -certreq -alias example.com -keystore exampleKeyStore.jks -file server.crt

- Incase you need to delete keystore

keytool -delete -alias example.com -keystore exampleKeyStore.jks

- Compile:

kotlinc src/main/kotlin/com/example/echodemo/ssl/EchoServer.kt -d dist/ssl_echo_server.jar

kotlinc src/main/kotlin/com/example/echodemo/ssl/EchoClient.kt -d dist/ssl_echo_client.jar

- Run:

kotlin -cp dist/ssl_echo_server.jar com.example.p2.echodemo.ssl.EchoServerKt

kotlin -cp dist/ssl_echo_client.jar com.example.p2.echodemo.ssl.EchoClientKt


## TimeServer socketchannel

- compile

kotlinc src/main/kotlin/com/example/p3/timeserverchannel/TimeServer.kt -d dist/timechannelserver.jar

kotlinc src/main/kotlin/com/example/p3/timeserverchannel/TimeClient.kt -d dist/timechannelclient.jar

- Run

kotlin -cp dist/timechannelserver.jar com.example.p3.timeserverchannel.TimeServerKt

kotlin -cp dist/timechannelclient.jar com.example.p3.timeserverchannel.TimeClientKt

## Chat server socketchannel

- compile
kotlinc \
src/main/kotlin/com/example/p3/chat/ChatServer.kt \
src/main/kotlin/com/example/p3/chat/HelperMethods.kt \
-d dist/chatserver.jar

kotlinc \
src/main/kotlin/com/example/p3/chat/ChatClient.kt \
src/main/kotlin/com/example/p3/chat/HelperMethods.kt \
-d dist/chatclient.jar

- run

kotlin -cp dist/chatserver.jar com.example.p3.chat.ChatServerKt

kotlin -cp dist/chatclient.jar com.example.p3.chat.ChatClientKt

## Async server socketchannel

- compile

kotlinc \
src/main/kotlin/com/example/p3/asyn/Server.kt \
src/main/kotlin/com/example/p3/asyn/Client.kt \
src/main/kotlin/com/example/p3/chat/HelperMethods.kt \
-d dist/asynclienserver.jar

- run

kotlin -cp dist/asynclienserver.jar com.example.p3.asyn.ServerKt

kotlin -cp dist/asynclienserver.jar com.example.p3.asyn.ClientKt

## Simple HTTP server

- compile

kotlinc src/main/kotlin/com/example/p4/SimpleHttpClient.kt \
src/main/kotlin/com/example/p4/WebServer.kt \
-d dist/simplehttpserver.jar

- Run

kotlin -cp dist/simplehttpserver.jar com.example.p4.SimpleHttpClientKt

kotlin -cp dist/simplehttpserver.jar com.example.p4.WebServerKt

## FreePastry P2P

- Compile
kotlinc src/main/kotlin/com/example/p5/FreePastryExample.kt -cp ~/.gradle/**/pastry-2.1.jar -d dist/demo.jar

- Run first instance

kotlin -cp dist/demo.jar \
-cp ~/.gradle/**/pastry-2.1.jar \
com.example.p5.FreePastryExampleKt 9001

- Run second instance

kotlin -cp dist/demo.jar \
-cp ~/.gradle/**/pastry-2.1.jar \
com.example.p5.FreePastryExampleKt 9002

## UDP client server echo

- compile

kotlinc src/main/kotlin/com/example/p6/*.kt -d dist/demo.jar

- Run server

kotlin -cp dist/demo.jar com.example.p6.UdpServerKt

- Run Client

kotlin -cp dist/demo.jar com.example.p6.UdpClientKt

## UDP channel client server echo

- compile

kotlinc src/main/kotlin/com/example/p6/udpchannel/*.kt -d dist/demo.jar

- Run server

kotlin -cp dist/demo.jar com.example.p6.udpchannel.EchoServerKt

- Run Client

kotlin -cp dist/demo.jar com.example.p6.udpchannel.EchoClientKt

## Multicast

- compile

kotlinc src/main/kotlin/com/example/p6/multicast/*.kt -d dist/demo.jar

- Run server

kotlin -cp dist/demo.jar com.example.p6.multicast.ServerKt

- Run Clients

kotlin -cp dist/demo.jar com.example.p6.multicast.ClientKt

## UDP Multicast

- compile

kotlinc src/main/kotlin/com/example/p6/udpmulticast/*.kt -d dist/demo.jar

- Run server

kotlin -cp dist/demo.jar com.example.p6.udpmulticast.ServerKt

- Run Clients

kotlin -cp dist/demo.jar com.example.p6.udpmulticast.ClientKt

## SSL 
- gen server keystore pkcs12, cert 

 keytool -genkeypair -alias server -keyalg RSA -keysize 1024 -storetype pkcs12 -validity 365 -keypass mypassword -keystore p8-serverkeystore.p12 -storepass mypassword -dname "cn=localhost, ou=department, o=mycompany, l=some city, st=hanoi c=VN"

 keytool -export -alias server -storetype pkcs12 -keystore p8-serverkeystore.p12 -storepass mypassword -file p8-server.crt

- gen client keystore pkcs12, cert

 keytool -genkeypair -alias client -keyalg RSA -keysize 1024 -storetype pkcs12 -validity 365 -keypass mypassword -keystore p8-clientkeystore.p12 -storepass mypassword -dname "cn=localhost, ou=department, o=mycompany, l=some city, st=hanoi c=VN"

 keytool -export -alias client -storetype pkcs12 -keystore p8-clientkeystore.p12 -storepass mypassword -file p8-client.crt

- import client cert to server trust store at server side

  keytool -importcert -alias client -file p8-client.crt -keystore p8-servertruststore.p12 -storetype pkcs12 -keypass mypassword -storepass mypassword

- import server cert to client trust store at client side 

   keytool -importcert -alias server -file p8-server.crt -keystore p8-clienttruststore.p12 -storetype pkcs12 -keypass mypassword -storepass mypassword

- View a keystore

keytool -list -v -keystore p8-clienttruststore.p12 -storepass mypassword