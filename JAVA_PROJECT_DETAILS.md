# Java Project Details

TODO: (LOW PRIORITY) Reorganize or regenerate the file

A modern Java/Maven project that provides currency conversion functionality, demonstrating Maven build and package publishing workflows.

## Features

- Currency conversion between multiple currencies (USD, EUR, GBP, JPY, CAD)
- BigDecimal-based calculations for precise financial operations
- Input validation for currency codes and amounts
- Comprehensive error handling with custom exception messages
- Java 21 features including `var` keyword and modern language constructs
- Comprehensive test suite with JUnit 5
- Maven-based build system with automated testing
- Support for publishing to GitHub Packages

## Prerequisites

- Java >= 21
- Maven >= 3.8
- Git (for version control)

## Project Structure

```
.
├── pom.xml                                    # Maven project configuration
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── example/
│   │               └── CurrencyConverter.java # Main application class
│   └── test/
│       └── java/
│           └── com/
│               └── example/
│                   └── CurrencyConverterTest.java # Test suite
├── target/                                    # Build output directory (generated)
│   ├── classes/                               # Compiled classes
│   ├── surefire-reports/                      # Test reports
│   └── currency-converter-1.0-SNAPSHOT.jar   # Generated JAR file
└── README.md                                  # Project README
```

## Publishing to GitHub Packages

The project is configured to publish to GitHub Packages. To publish:

1. Set up authentication in your GitHub Actions workflow or locally:

```bash
export GITHUB_TOKEN=<your-token>
```

2. Configure Maven settings (`~/.m2/settings.xml`):
```xml
<settings>
  <servers>
    <server>
      <id>github</id>
      <username>YOUR_GITHUB_USERNAME</username>
      <password>${env.GITHUB_TOKEN}</password>
    </server>
  </servers>
</settings>
```

## Common Maven Commands

```bash
# Validate project structure
mvn validate

# Compile source code
mvn compile

# Run tests
mvn test

# Package application
mvn package

# Install to local repository
mvn install

# Clean build artifacts
mvn clean

# Run all phases up to install
mvn clean install

# Generate site documentation
mvn site

# Generate sources JAR
mvn source:jar

# Generate Javadoc JAR
mvn javadoc:jar
```

3. Deploy to GitHub Packages:

```bash
mvn deploy
```

The package will be published to: `https://maven.pkg.github.com/<owner>/<repository>`

## Installation

1. Clone the repository:

    ```bash
    git clone <repository-url> <repository-name>
    cd <repository-name>
    ```

1. Build the project:

    ```bash
    mvn clean install
    ```

This will:

- Compile the source code
- Run all tests
- Package the application as a JAR file
- Install the package to your local Maven repository

## Configuration

The project uses Maven for configuration. Key settings are defined in `pom.xml`:

| Property | Value | Description |
|----------|-------|-------------|
| `maven.compiler.source` | `21` | Java source version |
| `maven.compiler.target` | `21` | Java target version |
| `project.build.sourceEncoding` | `UTF-8` | Source file encoding |
| `junit.version` | `5.10.1` | JUnit version for testing |

### Exchange Rates

Exchange rates are hardcoded in the `CurrencyConverter` class and are relative to USD:

| Currency | Rate (to USD) |
|----------|---------------|
| USD      | 1.00          |
| EUR      | 0.92          |
| GBP      | 0.79          |
| JPY      | 149.50        |
| CAD      | 1.36          |

## Running the Application

### Build and Run

```bash
# Build the project
mvn clean package

# Run the application
java -cp target/currency-converter-1.0-SNAPSHOT.jar com.example.CurrencyConverter
```

### Using Maven Exec Plugin

```bash
mvn exec:java -Dexec.mainClass="com.example.CurrencyConverter"
```

The application will demonstrate currency conversions and print results to the console.

## API Reference

### CurrencyConverter Class

#### Constructor

```java
public CurrencyConverter()
```

Creates a new instance of the currency converter.

#### Methods

##### `convert(BigDecimal amount, String fromCurrency, String toCurrency)`

Converts an amount from one currency to another.

**Parameters:**

- `amount` - The amount to convert (must be non-negative)
- `fromCurrency` - The source currency code (e.g., "USD", "EUR")
- `toCurrency` - The target currency code (e.g., "USD", "EUR")

**Returns:**

- `BigDecimal` - The converted amount, rounded to 2 decimal places

**Throws:**

- `IllegalArgumentException` - If currency codes are invalid or amount is negative

**Example:**

```java
CurrencyConverter converter = new CurrencyConverter();
BigDecimal result = converter.convert(new BigDecimal("100"), "USD", "EUR");
// Returns: 92.00
```

##### `isSupportedCurrency(String currencyCode)`

Checks if a currency code is supported.

**Parameters:**

- `currencyCode` - The currency code to check

**Returns:**

- `boolean` - `true` if supported, `false` otherwise

**Example:**

```java
CurrencyConverter converter = new CurrencyConverter();
boolean supported = converter.isSupportedCurrency("USD"); // Returns: true
boolean unsupported = converter.isSupportedCurrency("XXX"); // Returns: false
```

## Error Handling

The application provides clear error messages for invalid inputs:

### Unsupported Currency

```java
IllegalArgumentException: Unsupported currency: XXX. Supported currencies: [USD, EUR, GBP, JPY, CAD]
```

### Negative Amount

```java
IllegalArgumentException: Amount cannot be negative
```

## Testing

### Run Tests

```bash
mvn test
```

### Run Tests with Verbose Output

```bash
mvn test -X
```

### Run Specific Test Class

```bash
mvn test -Dtest=CurrencyConverterTest
```

### Test Coverage

The test suite includes:

- Basic currency conversion tests (USD to EUR)
- Cross-currency conversion tests (GBP to JPY)
- Same currency conversion tests (USD to USD)
- Error handling tests (unsupported currency, negative amounts)
- Currency validation tests
- Edge case tests (zero amounts)

Test results are integrated with Maven's Surefire plugin and can be viewed in the `target/surefire-reports` directory.

## Code Quality

### Compile

```bash
mvn compile
```

### Format Code

The project follows standard Java code formatting conventions. Consider using:

- Google Java Format
- Checkstyle
- SpotBugs

### Linting

```bash
# Check code style (if Checkstyle is configured)
mvn checkstyle:check
```

### Generate Javadoc

```bash
mvn javadoc:javadoc
```

Javadoc will be generated in `target/site/apidocs/`.

### Clean Build Artifacts

```bash
mvn clean
```

This removes:

- `target/` directory
- Compiled classes
- Generated JAR files
- Test reports


## Development

### Adding New Currencies

To add support for new currencies:

1. Update the `EXCHANGE_RATES` map in `CurrencyConverter.java`:
```java
private static final Map<String, BigDecimal> EXCHANGE_RATES = Map.of(
    "USD", new BigDecimal("1.00"),
    "EUR", new BigDecimal("0.92"),
    // Add new currency here
    "CHF", new BigDecimal("0.88")
);
```

2. Add tests for the new currency
3. Update this documentation

### Adding New Features

1. Create feature branch
2. Implement feature with tests
3. Ensure all tests pass: `mvn test`
4. Generate Javadoc: `mvn javadoc:javadoc`
5. Update documentation
6. Create pull request

### Running Individual Commands

```bash
# Compile only
mvn compile

# Run tests only
mvn test

# Package without running tests
mvn package -DskipTests

# Install to local repository
mvn install

# Clean and rebuild
mvn clean install

# Generate all artifacts (JAR, sources, Javadoc)
mvn clean package source:jar javadoc:jar
```

## Troubleshooting

### Build Failures

If the build fails:

1. Ensure Java 21 is installed:
```bash
java -version
```

2. Ensure Maven is installed:
```bash
mvn -version
```

3. Clean and rebuild:
```bash
mvn clean install
```

### Test Failures

If tests fail:

1. Check test output in `target/surefire-reports/`
2. Ensure all dependencies are resolved:
```bash
mvn dependency:resolve
```

3. Run tests with verbose output:
```bash
mvn test -X
```

### Import Errors in IDE

If your IDE shows import errors:

1. Refresh Maven project (in IntelliJ: Right-click `pom.xml` → Maven → Reload Project)
2. Ensure IDE is using Java 21
3. Reimport Maven dependencies

### Javadoc Warnings

To fix Javadoc warnings:

1. Ensure all public classes and methods have Javadoc comments
2. Run Javadoc generation:
```bash
mvn javadoc:javadoc
```

3. Check warnings in the output

## Maven Plugins Used

- **maven-compiler-plugin**: Compiles Java source code
- **maven-surefire-plugin**: Runs unit tests
- **maven-jar-plugin**: Creates executable JAR files
- **maven-source-plugin**: Generates source JAR files
- **maven-javadoc-plugin**: Generates Javadoc documentation
- **maven-release-plugin**: Manages version releases

## Version Management

The project uses Maven's versioning system:

- Current version: `1.0-SNAPSHOT` (defined in `pom.xml`)
- For releases, update the version in `pom.xml`
- The release plugin can automate version bumping and tagging

## License

This project uses the MIT License as specified in `pom.xml`.

## Contributing

When contributing to this project:

1. Follow Java coding conventions
2. Add Javadoc comments for all public classes and methods
3. Write tests for new features
4. Ensure all tests pass before submitting
5. Update documentation as needed
