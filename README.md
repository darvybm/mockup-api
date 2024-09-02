<p align="center">
  <img src="https://github.com/user-attachments/assets/8ed08170-3908-4f61-815c-90d1605244b0" alt="Header Image" width="500"/>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Language-Java-blue" alt="Java Badge"/>
  <img src="https://img.shields.io/badge/Framework-Spring%20Boot-brightgreen" alt="Spring Boot Badge"/>
  <img src="https://img.shields.io/badge/Frontend-Bootstrap-lightgrey" alt="Bootstrap Badge"/>
  <img src="https://img.shields.io/badge/Virtualization-Docker-blueviolet" alt="Docker Badge"/>
  <img src="https://img.shields.io/badge/Authentication-JWT-yellowgreen" alt="JWT Badge"/>
  <img src="https://img.shields.io/badge/Build%20Tool-Gradle-orange" alt="Gradle Badge"/>
</p>

# MockupAPI
Your go-to tool for simulating and managing REST API mocks efficiently.

## Table of Contents
- [Project Description](#project-description)
- [Features](#features)
- [Application](#application)
- [Tools Used](#tools-used)
- [How to Install](#how-to-install)
- [Contributors](#contributors)
- [License](#license)
- [Contact Me](#contact-me)

## Project Description
**MockupAPI** is a web application designed to help companies create REST API mocks for testing purposes. With this tool, users can log in, create projects (which represent APIs), and within these projects, create and manage mocks that serve as endpoints. Each endpoint is highly configurable, allowing users to attach metadata, add headers, set responses in specified formats, and define validations like JWT. Additionally, multiple HTTP methods are supported, including `POST`, `GET`, `PUT`, `DELETE`, and more.

The project is built in Java using Spring Boot, with Thymeleaf for view generation in a clear MVC architecture. The views are developed with HTML, CSS, JavaScript, and Bootstrap. On the backend, Spring Security is implemented for route authentication and authorization, and two database options are supported: H2 for local testing and MySQL when deployed with Docker. The application also utilizes JWT for security and Gradle as the build system.

## Features
- Project and mock management.
- Endpoint creation with configurable HTTP methods.
- Attach metadata, add headers, and customize responses.
- JWT validation for endpoints.
- Role management and user administration.
- Support for both English and Spanish.

## Application
Here are some screenshots and GIFs showcasing the application in action (The most Important views):

**Login Screen**
<p align="left">
  <img src="https://github.com/user-attachments/assets/d4a67c15-52b0-4da9-9ded-0c1316556332" alt="login" height="400" margin="500"/>
</p>

**Project List**
<p align="left">
  <img src="https://github.com/user-attachments/assets/5ae7dc52-3d57-49c4-bf80-3e3e69980ca9" alt="project list" height="400"/>
</p>

**Project Detail**
<p align="left">
  <img src="https://github.com/user-attachments/assets/e5c8798c-ad92-4e4a-89e3-bfd2b61a7936" alt="project detail" height="400"/>
</p>

**Create and Access a Mock**
<p align="left">
  <img src="https://github.com/user-attachments/assets/cff77eff-d086-4ce2-9e3a-a8be709cacda" alt="create mock" height="400"/>
</p>

**Users View**
<p align="left">
  <img src="https://github.com/user-attachments/assets/4b2bddb3-c598-45e9-9a27-b8447ae1315d" alt="users" height="400"/>
</p>

## Tools Used
- **Language:** Java ![Java](https://img.shields.io/badge/Language-Java-blue)
- **Frameworks:** Spring Boot, Spring Security ![Spring Boot](https://img.shields.io/badge/Framework-Spring%20Boot-brightgreen) ![Spring Security](https://img.shields.io/badge/Spring%20Security-5A9F57?style=flat&logo=spring)
- **Frontend:** Thymeleaf, HTML, CSS, JavaScript, Bootstrap ![Thymeleaf](https://img.shields.io/badge/Frontend-Thymeleaf-orange) ![HTML](https://img.shields.io/badge/HTML5-E34F26?style=flat&logo=html5&logoColor=white) ![CSS](https://img.shields.io/badge/CSS3-1572B6?style=flat&logo=css3&logoColor=white) ![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=flat&logo=javascript&logoColor=black) ![Bootstrap](https://img.shields.io/badge/Bootstrap-563D7C?style=flat&logo=bootstrap&logoColor=white)
- **Database:** H2, MySQL ![H2](https://img.shields.io/badge/Database-H2-lightgrey) ![MySQL](https://img.shields.io/badge/Database-MySQL-4479A1?style=flat&logo=mysql&logoColor=white)
- **Authorization:** JWT ![JWT](https://img.shields.io/badge/Authentication-JWT-yellowgreen)
- **Build Tool:** Gradle ![Gradle](https://img.shields.io/badge/Build%20Tool-Gradle-02303A?style=flat&logo=gradle&logoColor=white)
- **Virtualization:** Docker, Docker Compose ![Docker](https://img.shields.io/badge/Virtualization-Docker-blue) ![Docker Compose](https://img.shields.io/badge/Docker%20Compose-2496ED?style=flat&logo=docker&logoColor=white)

## How to Install
To run the application, follow these steps:

1. Clone this repository:
    ```bash
    git clone https://github.com/darvybm/mockup-api.git
    ```
2. **Navigate into the project directory:**
    ```bash
    cd mockup-api
    ```
3. Configure the variables in `application-local.properties` if necessary.
4. Run the project using your preferred IDE or Gradle:
    ```bash
    ./gradlew bootRun
    ```
5. Alternatively, you can run the application using Docker:
    ```bash
    docker-compose up --build
    ```

> [!NOTE]  
> If you need to customize some settings, modify them in the `docker-compose.yml` file. This file contains configurations for Docker and allows you to adjust environment variables and service settings.

## Contributors
Here are the contributors to this project:

<table>
  <tr>
    <td align="center">
      <a href="https://github.com/darvybm">
        <img src="https://github.com/darvybm.png" width="100px;" alt="darvybm"/>
        <br />
        <sub><b>darvybm</b></sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/AnthonyBeato">
        <img src="https://github.com/AnthonyBeato.png" width="100px;" alt="AnthonyBeato"/>
        <br />
        <sub><b>AnthonyBeato</b></sub>
      </a>
    </td>
  </tr>
</table>

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact Me

<p align="center">
  <a href="https://www.linkedin.com/in/darvybm" target="_blank">
    <img src="https://img.shields.io/badge/LinkedIn-@darvybm-blue?style=flat&logo=linkedin&logoColor=white" alt="LinkedIn Badge"/>
  </a>
  <a href="mailto:darvybm@gmail.com" target="_blank">
    <img src="https://img.shields.io/badge/Email-Contact%20Me-orange" alt="Email Badge"/>
  </a>
</p>
