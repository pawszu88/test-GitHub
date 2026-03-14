# 02_02 Build and Publish a Software Package

Continuous delivery workflows for software packages follow a pattern with these steps:

- Configure the project to work with the package registry
- Authenticate with the target registry
- Build the package
- Publish the package to the registry

## Registry configuration and authentication

Each language has a specific configuration that identifies the target registry and how to authenticate with it.

|Language  |Config File          |
|----------|---------------------|
|JavaScript|package.json         |
|Ruby      |.gemspec             |
|Java      |settings.xml, pom.xml|
|.Net      |.csproj              |

## Build and publish

Each language will also use its own, native tooling to build and publish a package.

|Language  |Build, publish Commands       |
|----------|------------------------------|
|JavaScript|npm ci; npm publish           |
|Ruby      |gem build; gem push           |
|Java      |mvn package; maven deploy     |
|.Net      |dotnet pack; dotnet nuget push|

## Package Versions

The configuration files for a package also define a version number for the package being published.

> [!IMPORTANT]
> Version numbers can't be reused.

Update code to reference a new version number with each new release.

## References

| Reference | Description |
|----------|-------------|
| [Working with a GitHub Packages registry](https://docs.github.com/en/packages/working-with-a-github-packages-registry) | Official GitHub documentation for working with GitHub Packages |
| [actions/setup-java on GitHub Marketplace](https://github.com/marketplace/actions/setup-java-jdk) | GitHub Action for setting up Java JDK environments |
| [actions/checkout on GitHub Marketplace](https://github.com/marketplace/actions/checkout) | GitHub Action for checking out repository code |
| [The updated workflow for this lesson](maven-publish.yml) | The complete workflow file for this lesson |

## Lab: Publishing a Java Package to GitHub Packages with Maven

In this lab, you’ll configure and run a GitHub Actions workflow that builds a Java project with Maven and publishes the resulting package to GitHub Packages. By the end of the lab, you’ll have a versioned Java artifact automatically built and delivered using a push-based workflow trigger.

### Overview

You’ll start with a Java project that already includes:

- Application source code
- A test suite
- A Maven `pom.xml` configured for packaging and publishing

Using a GitHub-provided starter workflow, you'll customize the workflow to:

- Run on every push
- Use updated GitHub Actions versions
- Match the Java version expected by the project

### Prerequisites

Before starting this lab, make sure you have:

- A GitHub repository containing the provided exercise files

### Instructions

#### 1. Review the Project Files

In your repository, review the files that make up the Java project:

- `CurrencyConverter.java` — the main application code
- `CurrencyConverterTest.java` — the test suite
- `pom.xml` — the Maven configuration file

While reviewing `pom.xml`, note the following:

- The project version uses the `GITHUB_RUN_NUMBER` environment variable to generate a unique artifact version for each workflow run.
- The `distributionManagement` section is configured to publish the package to GitHub Packages.

These settings allow Maven to package the application and deliver it to GitHub’s package registry during the workflow run.

#### 2. Create the Workflow Using a Starter Template

1. Navigate to the **Actions** tab in your repository.
2. Review the suggested workflows based on the repository contents.
3. Locate and select **“Publish Java Package with Maven.”**
4. Choose **Configure** to open the workflow editor.

This starter workflow already includes the core steps needed to compile and publish a Java package.

#### 3. Update the Workflow

1. Add a `push:` trigger

    By default, the workflow is triggered by a release event. Update it so it also runs on every push.

    - Under the `on:` section, add a `push:` trigger.

    This change ensures the workflow runs automatically whenever you commit changes to the repository.

2. Update the actions

    To ensure you're using current tooling, update the following actions:

    - `actions/checkout`
    - `actions/setup-java`

    Use the [references section](#references) to find the latest version for each action.

3. Update the Java Version

    The project's Maven configuration expects a specific Java version.

    - Change the setup-java step to use **Java 25**
    - Update the `java-version` value accordingly

    This ensures Maven runs with the correct Java runtime during the build and publish steps.

4. Commit the Workflow

    Commit the updated workflow file to the repository.

    Because the workflow now includes a push trigger, committing the file immediately starts a workflow run.

#### 4. Monitor the Workflow Run

1. Return to the **Actions** tab.
2. Select the running workflow.
3. Wait for the job to complete.

A successful run indicates that:

- The project was compiled
- Tests passed
- The package was published successfully

#### 5. Verify the Published Package

1. Navigate back to the repository’s **Code** tab.
2. Look for the **Packages** section on the repository home page.
3. Refresh the page if needed; packages may take a moment to appear.
4. Select the package name once it becomes visible.

On the package page, you'll find:

- Installation instructions
- Version details
- Download statistics
- Direct links to the published artifacts

## Shenanigans

### 1. What's a POM!?

> A Project Object Model or POM is the fundamental unit of work in Maven. It is an XML file that contains information about the project and configuration details used by Maven to build the project.

- Quoted from [Introduction to the POM](https://maven.apache.org/guides/introduction/introduction-to-the-pom.html)

### 2. Creating a Release

Instead of triggering the workflow with a push event, you can also use GitHub’s **release** mechanism to initiate the package build and publish process. When a release is published:

- GitHub Actions automatically runs the release-triggered workflow
- Maven builds the project using the version defined in the release tag
- The compiled artifact is published to GitHub Packages

#### Prerequisites

Before starting, make sure that:

- The **Publish Java Package with Maven** workflow already exists in your repository
- The workflow includes a `release` trigger

#### Instructions

1. From your repository, select the **Code** tab to return to the main project view.
1. Locate the **Releases** section on the right-hand side of the page.
1. Select **Create a new release**.
1. Create a Version Tag
1. Select **Choose a tag**.
1. Enter the value `v1.0.0`.
1. Select **Create a new tag on publish**.
1. Enter `v1.0.0` as the **Release title**.
1. Enter `v1.0.0` again in the **Release description**.
1. Select **Publish release**.
1. Navigate to the **Actions** tab.
1. Select the workflow run triggered by the release.
1. Wait for the job to complete.
1. After the workflow completes, return to the repository’s **Code** tab.
1. Look for the **Packages** section.
1. Select the published package.

<!-- FooterStart -->
---
[← 02_01 Deliver Software artifacts and Packages](../02_01_deliver_software_artifacts/README.md) | [02_03 Build and Publish a Container Image →](../02_03_build_publish_a_container_image/README.md)
<!-- FooterEnd -->
