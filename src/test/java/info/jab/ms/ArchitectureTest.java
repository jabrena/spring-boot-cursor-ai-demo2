package info.jab.ms;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.library.Architectures;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

public class ArchitectureTest {

    private final JavaClasses classes = new ClassFileImporter().importPackages("info.jab.ms");

    @Test
    public void layeredArchitectureShouldBeRespected() {
        var architecture = layeredArchitecture()
                .consideringAllDependencies()
                .layer("Controller").definedBy("..controller..")
                .layer("Service").definedBy("..service..")
                .layer("Repository").definedBy("..repository..")
                .layer("DTO").definedBy("..dto..")
                
                .whereLayer("Controller").mayNotBeAccessedByAnyLayer()
                .whereLayer("Service").mayOnlyBeAccessedByLayers("Controller")
                .whereLayer("Repository").mayOnlyBeAccessedByLayers("Service")
                .whereLayer("DTO").mayOnlyBeAccessedByLayers("Controller", "Service");

        architecture.check(classes);
    }

    @Test
    public void controllersShouldNotDependDirectlyOnRepositories() {
        ArchRule rule = noClasses()
                .that().resideInAPackage("..controller..")
                .and().haveSimpleNameNotEndingWith("Test")
                .should().dependOnClassesThat().resideInAPackage("..repository..");

        rule.check(classes);
    }

    @Test
    public void repositoriesShouldNotDependOnServices() {
        ArchRule rule = noClasses()
                .that().resideInAPackage("..repository..")
                .and().haveSimpleNameNotEndingWith("Test")
                .should().dependOnClassesThat().resideInAPackage("..service..");

        rule.check(classes);
    }

    @Test
    public void serviceClassesShouldHaveServiceSuffix() {
        ArchRule rule = classes()
                .that().resideInAPackage("..service..")
                .and().haveSimpleNameNotEndingWith("Test")
                .should().haveSimpleNameEndingWith("Service")
                .because("Service classes should end with 'Service'");

        rule.check(classes);
    }

    @Test
    public void controllerClassesShouldHaveControllerSuffix() {
        ArchRule rule = classes()
                .that().resideInAPackage("..controller..")
                .and().haveSimpleNameNotEndingWith("Test")
                .and().haveSimpleNameNotEndingWith("IntegrationTest")
                .should().haveSimpleNameEndingWith("Controller")
                .because("Controller classes should end with 'Controller'");

        rule.check(classes);
    }

    @Test
    public void repositoryInterfacesShouldHaveRepositorySuffix() {
        ArchRule rule = classes()
                .that().resideInAPackage("..repository..")
                .and().areInterfaces()
                .and().haveSimpleNameNotEndingWith("Test")
                .should().haveSimpleNameEndingWith("Repository")
                .because("Repository interfaces should end with 'Repository'");

        rule.check(classes);
    }

    @Test
    public void domainEntitiesShouldBeInRepositoryPackage() {
        ArchRule rule = classes()
                .that().areNotInterfaces()
                .and().areNotEnums()
                .and().resideInAPackage("..repository..")
                .and().haveSimpleNameNotEndingWith("Repository")
                .and().haveSimpleNameNotEndingWith("Test")
                .should().bePackagePrivate()
                .orShould().beProtected()
                .orShould().bePublic()
                .because("Domain entities should be in the repository package");

        rule.check(classes);
    }

    @Test
    public void dtoClassesShouldHaveDTOSuffix() {
        ArchRule rule = classes()
                .that().resideInAPackage("..dto..")
                .and().haveSimpleNameNotEndingWith("Test")
                .should().haveSimpleNameEndingWith("DTO")
                .because("DTO classes should end with 'DTO'");

        rule.check(classes);
    }
} 