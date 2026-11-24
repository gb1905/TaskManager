# Task Manager API

API REST desenvolvida em Java com Spring Boot para gerenciamento de tarefas. O projeto foi desenvolvido como atividade acadêmica da disciplina de Back-End Frameworks.

## Tecnologias utilizadas

- Java 21
- Spring Boot 4.2.0
- Maven
- Spring Data JPA
- PostgreSQL
- JUnit 5
- Mockito
- Docker
- Docker Compose
- Spring Boot Actuator

## Funcionalidades

- Cadastrar tarefas.
- Listar todas as tarefas.
- Buscar uma tarefa pelo ID.
- Atualizar tarefas.
- Excluir tarefas.
- Tratar situações em que uma tarefa não é encontrada.
- Persistir os dados no PostgreSQL.
- Monitorar a saúde da aplicação pelo Actuator.

## Estrutura do projeto

```text
taskmanager/
├── src/
│   ├── main/
│   │   ├── java/br/com/taskmanager/
│   │   │   ├── controller/
│   │   │   │   └── TarefaController.java
│   │   │   ├── exception/
│   │   │   │   ├── GlobalExceptionHandler.java
│   │   │   │   └── TarefaNaoEncontradaException.java
│   │   │   ├── model/
│   │   │   │   └── Tarefa.java
│   │   │   ├── repository/
│   │   │   │   └── TarefaRepository.java
│   │   │   ├── service/
│   │   │   │   └── TarefaService.java
│   │   │   └── TaskmanagerApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/br/com/taskmanager/
│           └── TarefaServiceTest.java
├── evidencias/
├── target/
├── .env
├── .gitignore
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── README.md
```

## Arquitetura

A aplicação utiliza arquitetura em camadas:

- **Controller:** recebe as requisições HTTP e encaminha as operações.
- **Service:** contém as regras de negócio e coordena as operações.
- **Repository:** realiza o acesso aos dados por meio do Spring Data JPA.
- **Model:** representa a entidade Tarefa armazenada no banco.
- **Exception:** contém o tratamento de erros da aplicação.

Fluxo principal:

```text
Cliente
   ↓
Controller
   ↓
Service
   ↓
Repository
   ↓
PostgreSQL
```

## Entidade Tarefa

A entidade possui os seguintes atributos:

| Atributo | Tipo | Descrição |

| id | Long | Identificador gerado automaticamente |
| titulo | String | Título da tarefa |
| descricao | String | Descrição da tarefa |
| status | String | Situação atual |
| prioridade | String | Prioridade da tarefa |
| dataCriacao | LocalDateTime | Data de criação |

## Endpoints

| Método | Endpoint | Descrição |

| POST | `/tarefas` | Cadastrar tarefa |
| GET | `/tarefas` | Listar todas as tarefas |
| GET | `/tarefas/{id}` | Buscar tarefa por ID |
| PUT | `/tarefas/{id}` | Atualizar tarefa |
| DELETE | `/tarefas/{id}` | Excluir tarefa |
| GET | `/actuator/health` | Verificar saúde da aplicação |

### Exemplo de cadastro

Requisição `POST /tarefas`:

```json
{
  "titulo": "Estudar Java",
  "descricao": "Revisar Spring Boot",
  "status": "PENDENTE",
  "prioridade": "ALTA"
}
```

## Banco de dados

O projeto utiliza PostgreSQL para armazenar as tarefas. A persistência é realizada com Spring Data JPA.

As configurações de conexão são fornecidas por variáveis de ambiente:

- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`

## Testes automatizados

Os testes da camada Service foram desenvolvidos com JUnit 5 e Mockito.

São verificados quatro cenários:

1. Cadastro de tarefa com sucesso.
2. Busca de tarefa existente.
3. Exceção ao buscar tarefa inexistente.
4. Impedimento da exclusão de tarefa inexistente.

Para executar os testes:

```bash
mvn clean test
```

Resultado obtido: 5 testes executados, sem falhas ou erros, incluindo o teste de contexto da aplicação.

## Gerar o JAR executável

Execute:

```bash
mvn clean package
```

O arquivo gerado fica em:

```text
target/taskmanager-0.0.1-SNAPSHOT.jar
```

Para executar fora da IDE:

```bash
java -jar target/taskmanager-0.0.1-SNAPSHOT.jar
```

## Executar com Docker

Configure a variável `DB_PASSWORD` no arquivo `.env`, localizado na raiz do projeto.

Em seguida, execute:

```bash
docker build -t taskmanager-api .
docker compose up -d
```

A aplicação utiliza dois serviços:

- `api`: executa a API Spring Boot.
- `db`: executa o PostgreSQL.

O Docker Compose configura a conexão entre os containers. Por isso, a API utiliza o endereço `db:5432` para acessar o banco, em vez de `localhost`.

### Persistência dos dados

O PostgreSQL utiliza um volume Docker chamado `postgres_data`.

Esse volume mantém os dados mesmo quando os containers são reiniciados ou recriados, desde que o volume não seja removido.

Para consultar os containers:

```bash
docker compose ps
```

Para consultar os logs da API:

```bash
docker compose logs --tail=50 api
```

Para parar os serviços:

```bash
docker compose down
```

## Monitoramento

A aplicação disponibiliza o endpoint:

```text
http://localhost:8080/actuator/health
```

O status `UP` indica que a aplicação está funcionando. Durante os testes, o Actuator também confirmou a disponibilidade da conexão com o banco de dados.

## Evidências

As capturas de tela estão organizadas na pasta `evidencias/`:

- `01-testes-automatizados.png`
- `02-docker-containers.png`
- `03-actuator-health.png`
- `04-crud-docker.png`
- `05-persistencia-docker.png`
- `06-logs-api.png`

## Segurança

As credenciais do banco são fornecidas por variáveis de ambiente. O arquivo `.env` não deve ser enviado ao GitHub. Em ambientes de produção, também é necessário restringir a exposição de informações detalhadas do Actuator.

## Autor
