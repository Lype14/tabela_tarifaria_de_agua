## Pré-requisitos

Antes de iniciar, certifique-se de ter instalado em sua máquina:
* **Java JDK:** Versão 21 (LTS)
* **PostgreSQL:** Versão 14 ou superior
* **Maven:** Versão 3.8+ (ou utilize o wrapper `./mvnw` incluso)
* **Ferramenta de testes:** Postman ou Insomnia

---
## Instruções de Instalação e Execução

### 1. Clonando o Repositório

1. Escolha ou crie um diretório de sua preferência em seu computador.
2. Abra o terminal (ou PowerShell) dentro dessa pasta.
3. Execute o comando `git clone` com o link do projeto:

```bash
git clone https://github.com/Lype14/tabela_tarifaria_de_agua

## Configuração do Banco de Dados 

Abra o seu terminal do PostgreSQL (psql) ou seu gerenciador (pgAdmin/DBeaver) e crie o banco de dados da aplicação com o nome exato do projeto:
   ```sql
   CREATE DATABASE tabela_agua;

# Após isso acesse o arquivo application.properties e altere as configurações da conexão com o seu banco

# Configurações de Conexão do Banco
spring.datasource.url=jdbc:postgresql://localhost:5432/tabela_agua
spring.datasource.username=seu_usuario_postgres
spring.datasource.password=sua_senha_postgres

# Estratégia do Hibernate (O Spring cria as tabelas automaticamente)
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

