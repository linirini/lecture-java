## 애플리케이션 실행하기
1. 해당 repository를 클론합니다.
```
git clone https://github.com/linirini/lecture-java.git
```
2. docker-compose.yml 파일이 있는 경로에서, docker 명령어로 Server를 실행합니다.
### Docker 실행하기
```
docker-compose -p lecture up -d
```

# Docker 정지하기
```
docker-compose -p lecture down
```

## API 문서
서버를 실행하면 다음 경로에서 API 문서를 확인 및 실행해볼 수 있습니다.
```
http://localhost:8080/swagger-ui/index.html
```
