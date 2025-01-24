docker-compose.yml 파일이 있는 경로에서, docker 명령어로 Server를 실행
# Docker 실행하기
docker-compose -p chess up -d
# Docker 정지하기
docker-compose -p chess down

# API 문서
서버를 실행하면 다음 경로에서 API 문서를 확인 및 실행해볼 수 있습니다.
```text
http://localhost:8080/swagger-ui/index.html
```
