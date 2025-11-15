# 🏢 Tricol V2 - Système de Gestion des Fournisseurs et Stock

Application Spring Boot pour la gestion des fournisseurs, commandes, produits et mouvements de stock avec valorisation CUMP (Coût Unitaire Moyen Pondéré).

## 📋 Table des Matières

- [Fonctionnalités](#-fonctionnalités)
- [Technologies](#-technologies)
- [Prérequis](#-prérequis)
- [Installation](#-installation)
- [Configuration](#-configuration)
- [Exécution](#-exécution)
- [Tests](#-tests)
- [Documentation API](#-documentation-api)
- [Structure du Projet](#-structure-du-projet)
- [Valorisation du Stock](#-valorisation-du-stock)

## ✨ Fonctionnalités

### 🏭 Gestion des Fournisseurs
- CRUD complet des fournisseurs
- Recherche par société, ville, ICE
- Pagination et filtrage avancé

### 📦 Gestion des Produits
- CRUD complet des produits
- Gestion automatique du stock
- Calcul automatique du coût unitaire moyen (CUMP)
- Recherche par nom, catégorie, prix
- Alertes de stock (stock faible/élevé)

### 🛒 Gestion des Commandes Fournisseurs
- Création de commandes avec lignes de commande
- Gestion du cycle de vie (EN_ATTENTE, EN_COURS, LIVREE, ANNULEE)
- Allocation automatique du stock lors de la livraison
- Calcul automatique des montants
- Recherche par statut, fournisseur, période

### 📊 Mouvements de Stock
- Traçabilité complète (ENTREE/SORTIE)
- Historique des mouvements par produit
- Association aux commandes fournisseurs
- Filtrage par type, produit, commande

## 🛠 Technologies

- **Backend**: Spring Boot 3.3.4
- **Base de données**: MySQL 8 (Production) / H2 (Tests)
- **ORM**: Hibernate / JPA
- **Migrations**: Liquibase
- **Mapping**: MapStruct 1.5.5
- **Validation**: Jakarta Validation
- **Documentation API**: Swagger/OpenAPI 3
- **Tests**: JUnit 5, Mockito, MockMvc
- **Build**: Maven
- **Containerisation**: Docker

## 📋 Prérequis

- Java 17 ou supérieur
- Maven 3.8+
- MySQL 8.0+ (ou Docker)
- Git

## 🚀 Installation

### 1. Cloner le projet
```bash
git clone https://github.com/Abdelmoudiri/Tricol-fullStack.git
cd TricolV2_test
```

### 2. Configurer la base de données

#### Option A : MySQL local
```sql
CREATE DATABASE tricol_db;
CREATE USER 'tricol_user'@'localhost' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON tricol_db.* TO 'tricol_user'@'localhost';
FLUSH PRIVILEGES;
```

#### Option B : Docker
```bash
docker run -d \
  --name mysql-tricol \
  -e MYSQL_ROOT_PASSWORD=rootpassword \
  -e MYSQL_DATABASE=tricol_db \
  -e MYSQL_USER=tricol_user \
  -e MYSQL_PASSWORD=your_password \
  -p 3306:3306 \
  mysql:8.0
```

### 3. Configurer l'application

Modifier `src/main/resources/application.yml` :

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/tricol_db
    username: tricol_user
    password: your_password
```

### 4. Compiler le projet
```bash
./mvnw clean install
```

## ⚙️ Configuration

### Méthode de Valorisation du Stock

Configurer dans `application.yml` :

```yaml
app:
  stock:
    valuation-method: CUMP  # Options: CUMP, FIFO
```

- **CUMP** (Coût Unitaire Moyen Pondéré) - Par défaut
- **FIFO** (First In, First Out) - À implémenter

### Base de données

```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: none  # validate, update, create, none
  liquibase:
    enabled: true
    change-log: classpath:db/changelog/db.changelog-master.xml
```

## 🏃 Exécution

### Mode développement
```bash
./mvnw spring-boot:run
```

### Avec profil spécifique
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

### Avec Docker
```bash
# Build l'image
docker build -t tricol-app .

# Run le conteneur
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/tricol_db \
  -e SPRING_DATASOURCE_USERNAME=tricol_user \
  -e SPRING_DATASOURCE_PASSWORD=your_password \
  tricol-app
```

### Package JAR
```bash
./mvnw clean package -DskipTests
java -jar target/tricol-suppliers-1.0.0.jar
```

L'application sera accessible sur : `http://localhost:8080`

## 🧪 Tests

### Exécuter tous les tests
```bash
./mvnw test
```

### Tests unitaires uniquement
```bash
./mvnw test -Dtest="*UnitTest"
```

### Tests d'intégration uniquement
```bash
./mvnw test -Dtest="*ControllerTest"
```

### Tests avec couverture (JaCoCo)
```bash
./mvnw clean test jacoco:report
```

Rapport disponible dans : `target/site/jacoco/index.html`

### Structure des tests

```
src/test/java/
├── unit/service/              # Tests unitaires des services
│   ├── FournisseurServiceUnitTest.java
│   ├── ProduitServiceUnitTest.java
│   ├── MouvementStockServiceUnitTest.java
│   └── CommandeFournisseurServiceUnitTest.java
└── integration/controller/    # Tests d'intégration des contrôleurs
    ├── FournisseurControllerTest.java
    ├── ProduitControllerTest.java
    ├── MouvementStockControllerTest.java
    └── CommandeFournisseurControllerTest.java
```

## 📚 Documentation API

### Swagger UI
Accessible sur : `http://localhost:8080/swagger-ui.html`

### OpenAPI JSON
Accessible sur : `http://localhost:8080/v3/api-docs`

### Endpoints principaux

#### Fournisseurs
- `GET /api/v2/fournisseurs` - Liste tous les fournisseurs
- `POST /api/v2/fournisseurs` - Créer un fournisseur
- `GET /api/v2/fournisseurs/{id}` - Détails d'un fournisseur
- `PUT /api/v2/fournisseurs/{id}` - Modifier un fournisseur
- `DELETE /api/v2/fournisseurs/{id}` - Supprimer un fournisseur

#### Produits
- `GET /api/v2/produits` - Liste tous les produits
- `POST /api/v2/produits` - Créer un produit
- `GET /api/v2/produits/{id}` - Détails d'un produit
- `PUT /api/v2/produits/{id}` - Modifier un produit
- `DELETE /api/v2/produits/{id}` - Supprimer un produit
- `GET /api/v2/produits/stock/lessThan/{stock}` - Produits en stock faible

#### Commandes Fournisseurs
- `GET /api/v2/commandes-fournisseur` - Liste toutes les commandes
- `POST /api/v2/commandes-fournisseur` - Créer une commande
- `GET /api/v2/commandes-fournisseur/{id}` - Détails d'une commande
- `PUT /api/v2/commandes-fournisseur/{id}` - Modifier une commande
- `PUT /api/v2/commandes-fournisseur/{id}/statut` - Changer le statut
- `DELETE /api/v2/commandes-fournisseur/{id}` - Supprimer une commande

#### Mouvements de Stock
- `GET /api/v2/mouvements` - Liste tous les mouvements
- `GET /api/v2/mouvements/by-produit?produitId={id}` - Par produit
- `GET /api/v2/mouvements/by-commande?commandeId={id}` - Par commande
- `GET /api/v2/mouvements/by-type?type={ENTREE|SORTIE}` - Par type

## 📁 Structure du Projet

```
src/main/java/com/tricol/tricolV2/
├── config/                    # Configuration de l'application
│   ├── AppProperties.java     # Propriétés personnalisées (CUMP/FIFO)
│   └── SwaggerConfig.java     # Configuration Swagger
├── controller/                # Contrôleurs REST
│   ├── CommandeFournisseure.java
│   ├── FournisseurController.java
│   ├── MouvementStockController.java
│   └── ProduitController.java
├── dto/                       # Data Transfer Objects
│   ├── CommandeFournisseurDTO.java
│   ├── FournisseurDTO.java
│   ├── LigneCommandeDTO.java
│   ├── MouvementStockDTO.java
│   └── ProduitDTO.java
├── entity/                    # Entités JPA
│   ├── CommandeFournisseur.java
│   ├── Fournisseur.java
│   ├── LigneCommandeFournisseur.java
│   ├── MouvementStock.java
│   ├── Produit.java
│   └── enums/
│       ├── StatutCommande.java
│       └── TypeMouvement.java
├── exception/                 # Gestion des exceptions
│   ├── BusinessException.java
│   ├── GlobalExceptionHandler.java
│   └── NotFoundException.java
├── mapper/                    # MapStruct mappers
│   ├── CommandeFournisseurMapper.java
│   ├── FournisseurMapper.java
│   ├── LigneCommandeMapper.java
│   └── ProduitMapper.java
├── repository/                # Repositories JPA
│   ├── CommandeFournisseurRepository.java
│   ├── FournisseurRepository.java
│   ├── MouvementStockRepository.java
│   └── ProduitRepository.java
├── service/                   # Services métier
│   ├── CommandeFournisseurService.java
│   ├── CommandeFournisseurServiceImpl.java
│   ├── FournisseurService.java
│   ├── FournisseurServiceImpl.java
│   ├── MouvementStockService.java
│   ├── MouvementStockServiceImpl.java
│   ├── ProduitService.java
│   └── ProduitServiceImpl.java
└── util/                      # Utilitaires
    └── ValorisationUtil.java  # Calcul CUMP
```

## 💰 Valorisation du Stock

### Méthode CUMP (Coût Unitaire Moyen Pondéré)

Le système utilise la méthode CUMP pour calculer automatiquement le coût moyen des produits :

**Formule** :
```
Nouveau CUMP = (Valeur Stock Actuel + Valeur Entrée) / (Quantité Actuelle + Quantité Entrée)
```

**Exemple** :
- Stock actuel : 100 unités à 50 DH = 5 000 DH
- Nouvelle entrée : 50 unités à 60 DH = 3 000 DH
- **Nouveau CUMP** = (5 000 + 3 000) / (100 + 50) = **53.33 DH**

### Mouvements de Stock

#### Type ENTREE
- Créé lors de l'ajout d'un produit avec stock initial
- Créé lors de la mise à jour du stock (augmentation)
- Recalcule automatiquement le CUMP

#### Type SORTIE
- Créé automatiquement lors du passage d'une commande au statut `LIVREE`
- Allocation intelligente multi-produits
- Vérification du stock disponible avant sortie
- Mise à jour automatique du stock produit

## 🔧 Commandes Utiles

### Maven
```bash
# Nettoyer le projet
./mvnw clean

# Compiler
./mvnw compile

# Tester
./mvnw test

# Packager
./mvnw package

# Skip tests
./mvnw package -DskipTests

# Exécuter
./mvnw spring-boot:run
```

### Git
```bash
# Voir l'historique
git log --oneline --date=iso -10

# Voir les changements
git status

# Créer une branche
git checkout -b feature/nouvelle-fonctionnalite
```

## 🐛 Résolution de Problèmes

### Port 8080 déjà utilisé
```yaml
server:
  port: 8081
```

### Erreur Liquibase
```bash
# Désactiver temporairement
spring.liquibase.enabled=false
```

### Erreur de connexion MySQL
```bash
# Vérifier que MySQL est démarré
sudo systemctl status mysql

# Tester la connexion
mysql -u tricol_user -p tricol_db
```

## 📝 License

Ce projet est sous licence MIT.

## 👥 Auteurs

- **Abdelmoudiri** - [GitHub](https://github.com/Abdelmoudiri)

## 🤝 Contribution

Les contributions sont les bienvenues ! N'hésitez pas à ouvrir une issue ou soumettre une pull request.

1. Fork le projet
2. Créer une branche (`git checkout -b feature/AmazingFeature`)
3. Commit vos changements (`git commit -m 'Add some AmazingFeature'`)
4. Push vers la branche (`git push origin feature/AmazingFeature`)
5. Ouvrir une Pull Request

## 📧 Contact

Pour toute question, contactez : [GitHub Issues](https://github.com/Abdelmoudiri/Tricol-fullStack/issues)
