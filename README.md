## Pré-requisitos

Antes de iniciar, certifique-se de ter instalado em sua máquina:

- **Java JDK:** versão 21 (LTS)
- **PostgreSQL:** versão 14 ou superior
- **Maven:** versão 3.8 ou superior (ou utilize o wrapper `./mvnw` incluso no projeto)
- **Ferramenta de testes:** Postman ou Insomnia

---

## Instruções de Instalação e Execução

### 1. Clonando o Repositório

1. Escolha ou crie um diretório de sua preferência em seu computador.
2. Abra o terminal (ou PowerShell) dentro dessa pasta.
3. Execute o comando abaixo para clonar o projeto:

```bash
git clone https://github.com/Lype14/tabela_tarifaria_de_agua
```

4. Acesse a pasta do projeto:

```bash
cd tabela_tarifaria_de_agua
```

---

## Configuração do Banco de Dados

Abra o terminal do PostgreSQL (`psql`) ou um gerenciador como pgAdmin ou DBeaver e crie o banco de dados com o nome abaixo:

```sql
CREATE DATABASE tabela_agua;
```

Após isso, acesse o arquivo `application.properties` e altere as configurações da conexão com o seu banco.

### Configurações de conexão com o banco

```properties
spring.datasource.url=sua_url_postgres
spring.datasource.username=seu_usuario_postgres
spring.datasource.password=sua_senha_postgres
```

### Configurações do Hibernate

O Spring criará as tabelas automaticamente. **Não é necessário alterar estas configurações:**

```properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

---

## Executando o Projeto

Por fim, execute o comando abaixo no terminal, dentro do diretório do projeto:

### Windows (PowerShell)

```bash
.\mvnw spring-boot:run
```

### Linux / macOS

```bash
./mvnw spring-boot:run
```

---

## 🧪 Documentação dos Endpoints

A API disponibiliza os seguintes endpoints para gerenciamento e simulação das tarifas, que podem ser testados via **Postman** ou **Insomnia**.

| Método | Endpoint | Descrição |
|---|---|---|
| **POST** | `/api/tabelas-tarifarias` | Cadastra uma nova tabela tarifária com faixas em cascata |
| **PUT** | `/api/faixa-consumo/{id}` | Edita o valor unitário de uma faixa de consumo específica |
| **GET** | `/api/tabelas-tarifarias` | Lista o histórico completo de todas as tabelas cadastradas |
| **DELETE** | `/api/tabelas-tarifarias/{id}` | Remove uma tabela e todas as suas faixas associadas (cascata) |
| **POST** | `/api/calculos` | Realiza o cálculo progressivo de consumo por categoria |

---

## 1. Cadastrar Nova Tabela Tarifária

Insere uma nova parametrização de tabela com suas respectivas faixas de consumo associadas.

- **Método HTTP:** `POST`
- **URL:** `http://localhost:8080/api/tabelas-tarifarias`

### Request Body (JSON)

```json
{
  "nome": "Tabela Tarifária de Água 2026",
  "dataVigencia": "2026-05-20",
  "faixas": [
    {
      "consumidor": "PARTICULAR",
      "consumoInicial": 0,
      "consumoFinal": 10,
      "valorUnitario": 1.50
    },
    {
      "consumidor": "PARTICULAR",
      "consumoInicial": 11,
      "consumoFinal": 99999,
      "valorUnitario": 3.40
    },
    {
      "consumidor": "INDUSTRIAL",
      "consumoInicial": 0,
      "consumoFinal": 20,
      "valorUnitario": 4.50
    },
    {
      "consumidor": "INDUSTRIAL",
      "consumoInicial": 21,
      "consumoFinal": 99999,
      "valorUnitario": 7.80
    }
  ]
}
```
