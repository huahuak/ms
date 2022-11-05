docker-compose -f "./backend/docker-compose.yml" up -d
echo "it need some time, please wait with patient..."
sleep 20
docker run -dp 8083:8083 --network msnet --name backend-backend moflower/backend-web:1.0 
docker run -dp 8090:8090 --network msnet --name backend-web moflower/backend-backend:1.0
docker run -dp 8848:8848 --network msnet --name decision-system moflower/decision-system:1.0
docker run -dp 8080:8080 --network msnet --name frontend-user moflower/frontend-user:1.0
docker run -dp 5006:5006 --network msnet --name frontend-manager moflower/frontend-manager:1.0
sleep 20
echo "ok, ms system is runing"
echo "backend-web: http://localhost:8083/doc.html"
echo "decision-system: http://localhost:8848/doc.html"
echo "frontend-manager: http://localhost:5006"
echo "frontend-user: http://localhost:8080"