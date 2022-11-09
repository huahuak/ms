# ms system
## prerequires
1. docker & docker-compose
2. network can communicate with docker-hub

## first
**IMPORTANT**

modify backend/conf/broker.conf 

set brokerIP1 to self computer ip ( such as brokerIP1=192.168.1.104)

```txt
brokerIP1=192.168.1.104
```

## linux
### run 
```sh
chmod +x bin/start.sh
bin/start.sh
```
please wait with patient, then you will see
```sh
backend-web: http://localhost:8083/doc.html
decision-system: http://localhost:8848/doc.html
frontend-manager: http://localhost:5006/doc.html
frontend-user: http://localhost:8080/doc.html
rocketMQ-consoler: http://localhost:18080
```
### stop
```sh
chmod +x bin/stop.sh
bin/stop.sh
```

## windows

### run 
```bat
bin\start.bat
```
### stop
```bat
bin\stop.bat
```