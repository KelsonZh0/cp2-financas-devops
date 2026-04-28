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

### Pré-requisitos
- Docker instalado
- Docker Compose instalado

> **Não tem o Docker instalado?**  
> Recomendamos a instalação do **Docker Desktop**, que já inclui o Docker Engine e o Docker Compose.
> - [Instalação para Windows](https://docs.docker.com/desktop/install/windows-install/)
> - [Instalação para Mac](https://docs.docker.com/desktop/install/mac-install/)
> - [Instalação para Linux](https://docs.docker.com/desktop/install/linux-install/)

> ⚠️ **Aviso para usuários de Windows:** Recomendamos rodar os comandos a seguir utilizando **Git Bash** ou **WSL**. Caso utilize o `CMD` ou `PowerShell`, preste atenção aos comandos que possuem quebra de linha com a barra invertida (`\`), pois eles podem não funcionar como esperado. Nesses terminais, pode ser necessário rodar tudo em uma única linha.

### Passos

1. **Clone o projeto:**
```bash
git clone https://github.com/KelsonZh0/cp2-financas-devops.git
cd cp2-financas-devops
```

2. **Suba os containers:**
```bash
docker compose up -d --build
```

3. **Verifique se os containers estão rodando:**
```bash
docker ps
```

4. **Teste a API:**
Acesse no navegador ou via cURL: `http://localhost:8080/financas`

---

## ☁️ Como Rodar em uma VM Ubuntu na Azure

1. **Acesse a VM via SSH:**
```bash
ssh admlnx@IP_PUBLICO_DA_VM
```

2. **Instale o Docker e Git:**
```bash
sudo apt update -y
sudo apt install -y docker.io docker-compose-plugin git
sudo systemctl enable --now docker
sudo usermod -aG docker $USER
newgrp docker
```

3. **Clone e execute o projeto:**
```bash
git clone https://github.com/KelsonZh0/cp2-financas-devops.git
cd cp2-financas-devops
docker compose up -d --build
```

### Liberação de Porta na Azure
Para acesso externo, a porta 8080 deve estar liberada no Network Security Group (NSG) da Azure:
- **Networking** > **Add inbound port rule**
  - **Destination port ranges:** 8080
  - **Protocol:** TCP
  - **Action:** Allow
  - **Name:** Allow-8080-Spring

Acesse em: `http://IP_PUBLICO_DA_VM:8080/financas`

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