# Shop Management System - Integrated Web & Desktop Application

This is a combined Spring Boot application that supports both web interface and desktop GUI for shop management operations.

## Features

### Web Application
- **Dashboard**: Overview with statistics of employees, products, and customers
- **Employee Management**: View all employees with their details
- **Product Management**: View all products with pricing and inventory
- **Customer Management**: View all customer information
- **Responsive Design**: Bootstrap-based modern UI

### Desktop Application  
- **Swing GUI**: Full desktop application with FlatLaf Look and Feel
- **Complete CRUD Operations**: Add, edit, delete employees, products, customers
- **Login System**: Secure authentication system
- **Database Integration**: MySQL database connectivity

## Technologies Used

- **Backend**: Spring Boot 2.7.18
- **Frontend Web**: Thymeleaf, Bootstrap 5
- **Frontend Desktop**: Java Swing with FlatLaf
- **Database**: MySQL with JPA/Hibernate
- **Build Tool**: Maven

## Project Structure

```
complete/
├── src/main/java/com/shopmanagement/
│   ├── ShopManagementApplication.java     # Main application class
│   ├── Start.java                         # Legacy desktop entry point
│   ├── activity/                          # Desktop GUI activities
│   ├── controller/                        # Spring controllers
│   │   └── web/                          # Web controllers
│   ├── entity/                           # JPA entities
│   ├── repository/                       # Data repositories
│   ├── service/                          # Business logic services
│   └── util/                             # Utility classes
├── src/main/resources/
│   ├── templates/shop/                   # Thymeleaf templates
│   └── application.properties            # Configuration
├── sql/                                  # Database scripts
├── run-web.sh                           # Script to run web mode
├── run-desktop.sh                       # Script to run desktop mode
└── pom.xml                              # Maven configuration
```

## Setup Instructions

### Prerequisites
1. Java 11 or higher
2. Maven 3.6+
3. MySQL 8.0+

### Database Setup
1. Create a MySQL database named `shopmanagement`
2. Run the SQL scripts in the `sql/` directory:
   ```bash
   mysql -u your_username -p shopmanagement < sql/shopmanagement.sql
   ```

### Application Configuration
1. Update `src/main/resources/application.properties` with your database credentials:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/shopmanagement
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

## Running the Application

### Web Mode (Default)
```bash
# Using the script
./run-web.sh

# Or using Maven directly
mvn spring-boot:run

# Or with arguments
mvn spring-boot:run -Dspring-boot.run.arguments="--mode=web"
```

The web application will be available at: http://localhost:8080

### Desktop Mode
```bash
# Using the script
./run-desktop.sh

# Or using Maven directly
mvn spring-boot:run -Dspring-boot.run.arguments="--mode=desktop"
```

## Web Interface

### Available Routes
- `/` - Root redirect to shop dashboard
- `/shop/` - Main dashboard with statistics
- `/shop/employees` - Employee list view
- `/shop/products` - Product list view  
- `/shop/customers` - Customer list view
- `/shop/login` - Login page (for future authentication)

### Dashboard Features
- **Employee Count**: Total number of employees
- **Product Count**: Total number of products  
- **Customer Count**: Total number of customers
- **Navigation**: Easy access to all management sections

## Desktop Interface

### Main Features
- **Login Screen**: Secure authentication
- **Dashboard**: Overview of system statistics
- **Employee Management**: Complete CRUD operations
- **Product Management**: Inventory and pricing management
- **Customer Management**: Customer relationship management
- **Profile Management**: User profile settings
- **Theme Settings**: Customizable interface themes

## Building for Production

### Create JAR file
```bash
mvn clean package
```

### Run the JAR
```bash
# Web mode
java -jar target/shop-management-web-system-1.0.0.jar --mode=web

# Desktop mode  
java -jar target/shop-management-web-system-1.0.0.jar --mode=desktop
```

## Development Notes

### Architecture
- **Single Application**: Both web and desktop functionality in one codebase
- **Mode Selection**: Runtime mode selection via command line arguments
- **Shared Services**: Common business logic for both interfaces
- **Separate Controllers**: Web controllers for REST endpoints, Activity classes for desktop GUI

### Database Schema
- **employee**: Employee information and credentials
- **product**: Product catalog and inventory
- **customer**: Customer information
- **login**: Authentication and user management

## Troubleshooting

### Common Issues

1. **Database Connection**: Ensure MySQL is running and credentials are correct
2. **Port Conflicts**: Web mode uses port 8080 by default
3. **Java Version**: Requires Java 11+ for Spring Boot 2.7.18
4. **Desktop Display**: Desktop mode requires GUI environment (not headless)

### Error Handling
- Web interface shows user-friendly error messages
- Database connection errors are caught and displayed
- Validation errors are shown in forms

## Future Enhancements

- [ ] Spring Security integration for web authentication
- [ ] REST API endpoints for mobile apps
- [ ] Advanced reporting features
- [ ] Email notifications
- [ ] Multi-tenant support
- [ ] Advanced search and filtering

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test both web and desktop modes
5. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.
