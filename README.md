# Video Rental Store Application
---

## Description

The Video Rental Store Application is a comprehensive educational project designed to explore the core functionalities of Hibernate, focusing on ORM (Object-Relational Mapping) principles, entity mapping, and seamless integration with service-oriented APIs. This project serves as an excellent resource for learners to understand and implement real-world business logic within a software application. By covering a broad range of Hibernate's features, from basic mappings to complex associations and transaction management, this project offers a deep dive into the framework, making it an ideal learning endeavor for anyone looking to enhance their backend development skills.

### Project Overview

At its core, the Video Rental Store Application simulates the operations of a physical video rental store in a digital format. It models the various aspects of the business through a well-defined set of entities and their relationships, encapsulating the intricacies of video rental processes. This project is not just about understanding Hibernate but also about seeing it in action in a practical, API-driven environment.

### Basic Requirements and Features

The application's data model includes a comprehensive suite of tables designed to represent the essential components of a video rental business. The entities involved include:

- **Actor**: Represents individuals acting in films.
- **Address**, **City**, **Country**: Define the geographical hierarchy for customers and stores.
- **Category**: Categorizes films into genres or themes.
- **Customer**: Maintains customer information, including rentals.
- **Film**: Details about films available for rent, including title, description, and classification.
- **Inventory**: Tracks the availability of films at various store locations.
- **Language**: Specifies the languages in which films are available.
- **Payment**: Manages transactions related to film rentals.
- **Rental**: Records details about film rentals.
- **Manager** (StaffManager): Information about store managers.
- **Store**: Information about store locations and inventory.

#### Implemented Functionalities

- **Register New Customer**: Allows new customers to be added to the system, capturing essential information and linking them to an address and a store.
- **Register New Film**: Facilitates the addition of new films to the catalog, including details such as title, description, categories, and actors.
- **Rent Film**: Enables customers to rent films based on the availability of inventory at their associated store. This feature requires checking both the presence of the film in the inventory and its availability for rent.
- **Return Rented Film**: Manages the return process of rented films, updating the inventory status and processing any necessary payments.

### Learning Outcomes

By engaging with this project, learners will gain hands-on experience with:

- Configuring and utilizing Hibernate for data persistence.
- Mapping entities to database tables and establishing relationships among them.
- Implementing CRUD operations and managing transactions within a service-oriented architecture.
- Understanding real-world application development challenges and solutions.

This Video Rental Store Application stands as a testament to the power of Hibernate in bridging the gap between object-oriented programming languages and relational databases. It's a great starting point for anyone interested in mastering backend development, database design, and ORM frameworks.

### Technology Stack

- Java
- Hibernate
- JPA (Java Persistence API)
- Maven
- H2 Database (for tests)
- PostgreSQL
- JUnit
- 
### Setup

#### Prerequisites

- JDK 21
- Maven 
- IntelliJ IDEA
- PostgreSQL

**Configure the database**

   Update the `src/main/resources/META-INF/persistence.xml` file with your PostgreSQL settings. This file contains all necessary configurations for connecting to your database.


