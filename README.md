# University System “INTRANET”

## Object-Oriented Programming & Design Project

### Team Members
- Safarova Sofiya
- Magrupov Almas
- Zhakyp Amir

---

# Project Overview

“INTRANET” is a console-based university management system developed in Java using Object-Oriented Programming (OOP) principles and design patterns.

The project simulates the internal academic and administrative processes of a university and provides interaction between:
- Students
- Teachers
- Managers
- Administrators
- Tech Support Specialists
- Researchers

The system automates course registration, grading, transcript generation, research management, news publishing, request processing, and communication between university members.

---

# Main Features

## Academic System
- Course registration and dropping
- GPA calculation
- Transcript generation
- Marks and grading system
- Student organizations
- Teacher rating system

## Administration System
- User management
- Course management
- Academic reports
- System logs
- Request management

## Research Module
- Research paper publishing
- H-index calculation
- Research projects
- Journal subscriptions
- Citation generation
- Researcher profiles

## Communication System
- Internal employee messaging
- News and comments
- Notifications for journal subscribers

## Additional Features
- Multilanguage support (EN/RU/KZ)
- Serialization and persistent data storage
- Console-based menus for different user roles

---

# Technologies Used

- Java
- Object-Oriented Programming
- Java Collections Framework
- Serialization
- UML Diagrams
- Design Patterns
- Git/GitHub

---

# Design Patterns

## Singleton Pattern
Implemented in:
- `DataStorage`

Purpose:
- Centralized system data management

---

## Factory Pattern
Implemented in:
- `UserFactory`

Purpose:
- Simplified creation of different user types

---

## Decorator Pattern
Implemented in:
- `ResearcherDecorator`

Purpose:
- Dynamically extending users with researcher functionality

---

## Observer Pattern
Implemented in:
- `Journal`

Purpose:
- Automatic notification of subscribers when a new paper is published

