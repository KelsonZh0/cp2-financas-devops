# CP2 DevOps & Cloud Computing — API de Finanças

Projeto desenvolvido para o CheckPoint 2 da disciplina **DevOps Tools & Cloud Computing**.

A aplicação é uma API REST de finanças construída com **Java + Spring Boot**, conectada a um banco de dados **MySQL**. O projeto utiliza **Docker** e **Docker Compose** para orquestrar a aplicação e o banco em containers, facilitando o deploy tanto local quanto em nuvem.

---

## 🛠 Tecnologias

- **Java 17**
- **Spring Boot**
- **Spring Data JPA**
- **MySQL 8**
- **Docker & Docker Compose**
- **Azure VM** (Ubuntu)
- **GitHub**

---

## 🏗 Arquitetura

O projeto é composto por dois containers que se comunicam através da rede interna do Docker:

| Container | Função |
|---|---|
| `api-financas` | API Java/Spring Boot (Porta 8080) |
| `mysql-financas` | Banco de dados MySQL (Porta 3306) |

A API acessa o banco de dados utilizando o nome do serviço `mysql-financas` como hostname.

---

## 📁 Estrutura do Projeto

```txt
financas/
├── Dockerfile              # Definição da imagem da API
├── docker-compose.yml       # Orquestração dos containers
├── pom.xml                  # Dependências Maven
├── mysql/
│   └── init.sql             # Script de inicialização do banco
└── src/                     # Código fonte da aplicação
```

## 🚀 Como Rodar Localmente

Você pode executar este projeto de duas formas: usando Docker (ambiente completo) ou rodando a API diretamente via Maven (ideal para desenvolvimento).

### Opção 1: Usando Docker (Recomendado)

**Pré-requisitos:**
- Docker e Docker Compose instalados.

**Passos:**
1. Clone o repositório e entre na pasta:
   ```bash
   git clone https://github.com/KelsonZh0/cp2-financas-devops.git
   cd cp2-financas-devops
   ```
2. Suba os containers da API e do banco de dados:
   ```bash
   docker compose up -d --build
   ```
3. Aguarde alguns segundos para o banco de dados inicializar e acesse a API no navegador:
   `http://localhost:8080/financas`

### Opção 2: Usando Maven e Java Nativamente (Sem Docker para a API)

**Pré-requisitos:**
- Java 17 instalado
- MySQL 8 instalado e rodando localmente (ou apenas o container do MySQL)
- Maven (ou utilize o `mvnw` incluso no projeto)

**Passos:**
1. Clone o projeto e acesse o diretório:
   ```bash
   git clone https://github.com/KelsonZh0/cp2-financas-devops.git
   cd cp2-financas-devops
   ```
2. Caso não tenha o MySQL instalado localmente, você pode subir apenas o banco de dados pelo Docker:
   ```bash
   docker compose up -d mysql-financas
   ```
3. Execute a aplicação Spring Boot utilizando o Maven Wrapper:
   ```bash
   # No Windows (CMD ou PowerShell)
   mvnw.cmd spring-boot:run

   # No Linux ou Git Bash
   ./mvnw spring-boot:run
   ```
4. A API estará disponível no endereço: `http://localhost:8080/financas`

---

## ☁️ Como Rodar em uma VM Ubuntu na Azure

Para realizar o deploy do projeto em uma máquina virtual (VM) Linux na nuvem da Azure, siga o passo a passo abaixo.

### 1. Acesso e Preparação do Ambiente
Acesse sua VM remotamente via SSH utilizando o IP Público fornecido pela Azure:
```bash
ssh admlnx@IP_PUBLICO_DA_VM
```

Atualize a lista de pacotes do sistema e instale as dependências (Docker, Docker Compose e Git):
```bash
sudo apt update -y
sudo apt install -y docker.io docker-compose-plugin git
```

Inicie o serviço do Docker e configure para que inicie automaticamente com o sistema:
```bash
sudo systemctl enable --now docker
```

*(Opcional)* Adicione seu usuário ao grupo do Docker para não precisar digitar `sudo` em todos os comandos:
```bash
sudo usermod -aG docker $USER
newgrp docker
```

### 2. Clonagem e Execução
Faça o download do projeto na sua VM:
```bash
git clone https://github.com/KelsonZh0/cp2-financas-devops.git
cd cp2-financas-devops
```

Realize o build das imagens e levante a infraestrutura:
```bash
docker compose up -d --build
```
Verifique se a aplicação e o banco estão rodando normalmente com `docker ps`.

### 3. Liberação de Portas (NSG Azure)
Para que a API seja acessível pela internet, é obrigatório liberar a porta `8080` no **Network Security Group (NSG)** da sua VM na Azure:
1. No Portal do Azure, vá até a página da sua VM.
2. No menu lateral, acesse **Networking** (Rede).
3. Clique em **Add inbound port rule** (Adicionar regra de porta de entrada).
4. Configure os parâmetros:
   - **Destination port ranges:** `8080`
   - **Protocol:** `TCP`
   - **Action:** `Allow`
   - **Name:** `Allow-8080-Spring`
5. Salve a regra.

**Teste final:** Acesse pelo navegador `http://IP_PUBLICO_DA_VM:8080/financas`.

---

## 🛣 Endpoints da API

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/financas` | Lista todas as despesas |
| GET | `/financas/{id}` | Busca uma despesa por ID |
| POST | `/financas` | Cadastra uma nova despesa |
| PUT | `/financas/{id}` | Atualiza uma despesa existente |
| DELETE | `/financas/{id}` | Remove uma despesa |

---

## 📝 Exemplos de Uso

**Listar todas as despesas**
```bash
curl http://localhost:8080/financas
```
*(Exemplo usando o IP público da VM na Azure: `curl http://IP_PUBLICO_DA_VM:8080/financas`)*

**Buscar despesa por ID**
```bash
curl http://localhost:8080/financas/1
```

**Cadastrar nova despesa**
```bash
curl -X POST http://localhost:8080/financas \
-H "Content-Type: application/json" \
-d '{"descricao":"Curso Java","categoria":"Educação","valor":250.00,"data":"2026-04-24"}'
```

**Atualizar uma despesa**
```bash
curl -X PUT http://localhost:8080/financas/1 \
-H "Content-Type: application/json" \
-d '{"descricao":"Mercado atualizado","categoria":"Alimentação","valor":500.00,"data":"2026-04-24"}'
```

**Remover uma despesa**
```bash
curl -X DELETE http://localhost:8080/financas/1
```

---

## 🗄 Acessar o Banco MySQL via Terminal

Se precisar verificar os dados diretamente no banco:
```bash
docker exec -it mysql-financas mysql -ufiap -pfiap123 financasdb
```

Comandos SQL úteis:
```sql
SELECT * FROM despesas;
EXIT;
```

---

## 🛠 Comandos Úteis de Manutenção

- **Ver logs da API:** `docker logs api-financas`
- **Parar aplicação:** `docker compose down`
- **Reiniciar e aplicar mudanças:** `docker compose up -d --build`

---

## 👨‍💻 Autor

Kelson Zhang — RM563748 — 2TDSPG