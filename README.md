# OraclePLM Java

Welcome to the OraclePLM-java repository, your go-to resource for a feature-rich CRUD application developed in Java. This application seamlessly integrates with Oracle PD and MySQL databases, offering robust data synchronization capabilities.

## Table of Contents

- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

## Features

- **Oracle PD Integration:** Retrieve data from Oracle PD seamlessly.
- **MySQL Database:** Store synchronized data locally in a MySQL database.
- **CRUD Operations:** Perform Create, Read, Update, and Delete operations with ease.
- **Java Backend:** Utilizes the power of Java for backend functionality.
- **User-Friendly Interface:** Frontend powered by Knockout.js for an intuitive user experience.

## Prerequisites

Before you begin, ensure that you have the following installed on your system:

- [Eclipse IDE for Java Developers](https://eclipseide.org/)
- [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-downloads.html) (Java 8)
- [MySQL Database](https://www.mysql.com/)
- [Oracle PD Credentials](https://www.oracle.com/database/)

## Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/your-username/OraclePLM-java.git
   ```

2. Navigate to the project directory:

   ```bash
   cd OraclePLM-java
   ```

## Configuration

Create a .env file to set up your MySQL database connection details:

```properties
#---------------------------------------------------------
# Cloud configurations
#---------------------------------------------------------
cloud.url=
cloud.username=
cloud.password=
cloud.query=

#===================================================
# Database configurations
#===================================================
database.url=
database.username=
database.password=

#===================================================
# Logging
#===================================================
logs.path=.
#logs.path=/opt/tomcat/apache-tomcat-9.0.39
#logs.path=null
```

## Usage

1. Create a config.properties file at src/com/gosaas/constants/.

2. Configure Oracle PD and MySQL connection details in the application as explained in the previous step.

3. Open the application in Eclipse and wait while the IDE resolves Maven dependencies.

4. Install Tomcat 9.0 server through the IDE.

5. Run the application through Tomcat.

6. Access the application in your web browser and explore the CRUD functionality.

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details