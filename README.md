# Summary Service

Microsserviço que gera relatórios e resumos com base nas tarefas gerenciadas pelo Task Service.

## Funcionalidades

- Exibir resumo geral de tarefas
- Listar tarefas concluídas
- Listar tarefas pendentes
- Contar total de tarefas

## Tecnologias

- Java 21
- Spring WebFlux
- WebClient (para consumo do Task Service)
- JUnit 5 + Mockito

## Executando localmente

Configure a URL do serviço de tarefas (`task-service`) nas propriedades:

```properties
task.service.url=http://localhost:8080
```

Ou use o DNS interno no Kubernetes:
```properties
task.service.url=http://task-service:8080
```

## Endpoints REST

- `GET /summary` - resumo geral
- `GET /summary/completed` - tarefas concluídas
- `GET /summary/pending` - tarefas pendentes
- `GET /summary/total` - total de tarefas

## Testes

- Testes unitários e de integração
- Simulação de chamadas com `WebTestClient`
