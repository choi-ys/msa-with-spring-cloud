Spring Cloud Netflix Eureka
===

# Service Discovery
```
하나의 서비스를 제공하기 위해 시스템 내부에 수많은 마이크로 서비스가 존재한다는 가정에서부터 시작합니다.
각자의 역할과 책임을 가진 수많은 인스턴스는 고가용성을 위해 빈번하게 확장/축소하고,
서비스 제공을 위해 다른 인스턴스와 활발히 통신할 것입니다.
이때 확장/축소하며 수시로 변경되는 인스턴스의 연결정보를 다른 인스턴스는 어떻게 알 수 있을까요?
매번 담당자가 LB에 추가되거나 삭제되는 인스턴스의 연결정보를 수정하기는 힘들 것 같습니다.
이를 해결하기 위해 우리는 Spring Cloud Netflix Eureka를 살펴보고 고려해볼 수 있습니다.

Spring Cloud Netflix Eureka는 인스턴스의 연결정보의 Meta Data를 기반으로 인스턴스를 등록/해지하며,
인스턴스의 확장/축소 시에도 변경되는 각 인스턴스를 발견할 수 있는 역할을 담당합니다.
```

# Eureka 구성 요소
Eureka는 Eureka Client(클라이언트)와 Eureka Server(서버)로 구성

> Eureka Client
- Microservices의 각 인스턴스는 Eureka Client에 해당 
- Eureka Client들은 스스로를 Eureka Server에 등록
- Eureka Client들은 자신들의 연결 정보(ip, hostname, port)의 Meta Data들을 Eureka 서버에 등록
- Eureka Client들은 Eureka Server에 저장된 Registry 정보를 수신하고, 자신의 Local환경에 저장
- Eureka Client들은 Eureka Server에게 전달 받은 Registry 정보를 통해 다른 Client들의 정보를 식별
  
> Eureka Server
- Eureka Server는 Registry에 Eureka Client들을 등록
- Eureka Client들이 보낸 Meta Data들을 Eureka Server는 Registry에 등록
- Eureka Server는 Client들로부터 30초마다(Default value) Client들이 작동 중임을 알 수 있는 Heartbeat를 수신
- Heartbeat가 오지 않은 경우 Server는 Client가 죽었다고 판단, 90초 내에 해당 Client의 정보를 Registry에서 삭제
- 이렇게 유지된 Registry 정보를 Server는 Client들에게 전달

---

# Service Discovery의 효과
```
애플리케이션의 스케일링을 위해 인스턴스를 실행할 때마다 자동으로 포트가 부여되고,
사용자들이 인식하지 못한 상태에서 여러 개의 인스턴스가 생성되고 종료됩니다.
이 과정에서 각각의 인스턴스들은 디스커버리 서비스에 등록되어 라우팅 서비스와 게이트웨이에 의해 필요한 작업을
처리할 수 있게 됩니다.
```

---

# 참조 URL
- [spring.io official : spring-cloud-netflix ](https://cloud.spring.io/spring-cloud-netflix/reference/html/#spring-cloud-eureka-server)
 
- [baeldung : spring-cloud-netflix-eureka](https://www.baeldung.com/spring-cloud-netflix-eureka)

---