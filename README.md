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
### Response

```text
Tabela tarifária cadastrada com sucesso com suas respectivas faixas!
```

> **Observação:** o sistema suporta 4 tipos de consumidores:
> `COMERCIAL`, `INDUSTRIAL`, `PARTICULAR` e `PUBLICO`.

---

## 2. Editar Valor Unitário de uma Faixa de Consumo

Permite alterar o valor unitário de uma faixa de consumo já cadastrada.

- **Método HTTP:** `PUT`
- **URL:** `http://localhost:8080/api/faixa-consumo/{id}`

### Request Body (JSON)

```json
{
  "novoValorUnitario": 2
}
```

### Response

```text
Faixa de consumo editada com sucesso!
```

> **Observação:** caso o `id` não exista ou haja inconsistência nos dados, o sistema retornará uma exceção com a descrição do erro.

---

## 3. Listar Histórico de Tabelas Tarifárias

Lista todas as tabelas tarifárias cadastradas, incluindo as ativas e inativas.

- **Método HTTP:** `GET`
- **URL:** `http://localhost:8080/api/tabelas-tarifarias`

### Response (JSON)

```json
[
  {
    "ativo": true,
    "dataCriacao": "2026-05-17",
    "dataVigencia": "2026-05-20",
    "faixas": [
      {
        "consumidor": "PARTICULAR",
        "consumoFinal": 10,
        "consumoInicial": 0,
        "id": 5,
        "valorUnitario": 1.5
      },
      {
        "consumidor": "PARTICULAR",
        "consumoFinal": 99999,
        "consumoInicial": 11,
        "id": 6,
        "valorUnitario": 3.4
      },
      {
        "consumidor": "INDUSTRIAL",
        "consumoFinal": 99999,
        "consumoInicial": 21,
        "id": 8,
        "valorUnitario": 7.8
      },
      {
        "consumidor": "INDUSTRIAL",
        "consumoFinal": 20,
        "consumoInicial": 0,
        "id": 7,
        "valorUnitario": 2.74
      }
    ],
    "id": 2,
    "nome": "Tabela Tarifária de Água 2026"
  },
  {
    "ativo": false,
    "dataCriacao": "2026-05-16",
    "dataVigencia": "2026-05-20",
    "faixas": [
      {
        "consumidor": "PARTICULAR",
        "consumoFinal": 10,
        "consumoInicial": 0,
        "id": 1,
        "valorUnitario": 1.5
      },
      {
        "consumidor": "PARTICULAR",
        "consumoFinal": 99999,
        "consumoInicial": 11,
        "id": 2,
        "valorUnitario": 3.4
      },
      {
        "consumidor": "INDUSTRIAL",
        "consumoFinal": 20,
        "consumoInicial": 0,
        "id": 3,
        "valorUnitario": 4.5
      },
      {
        "consumidor": "INDUSTRIAL",
        "consumoFinal": 99999,
        "consumoInicial": 21,
        "id": 4,
        "valorUnitario": 7.8
      }
    ],
    "id": 1,
    "nome": "Tabela Tarifária de Água 2026"
  }
]
```

---

## 4. Remover Tabela Tarifária

Remove uma tabela tarifária e todas as faixas de consumo associadas.

- **Método HTTP:** `DELETE`
- **URL:** `http://localhost:8080/api/tabelas-tarifarias/{id}`

### Response

```text
Tabela tarifária e todas as suas faixas de consumo foram excluídas com sucesso!
```

> **Observação:** a exclusão só será realizada caso o `id` informado exista na base de dados.

---

## 5. Realizar Cálculo de Consumo

Realiza o cálculo progressivo do valor da conta de água com base no consumo e categoria informados.

- **Método HTTP:** `POST`
- **URL:** `http://localhost:8080/api/calculos`

### Request Body (JSON)

```json
{
  "consumo": 30,
  "categoria": "INDUSTRIAL"
}
```

### Response (JSON)

```json
{
  "categoria": "INDUSTRIAL",
  "consumoTotal": 30,
  "valorTotal": 112.0,
  "detalhamento": [
    {
      "faixa": {
        "inicio": 0,
        "fim": 20
      },
      "m3Cobrados": 20,
      "valorUnitario": 2.0,
      "subtotal": 40.0
    },
    {
      "faixa": {
        "inicio": 21,
        "fim": 99999
      },
      "m3Cobrados": 9,
      "valorUnitario": 8.0,
      "subtotal": 72.0
    }
  ]
}
```

