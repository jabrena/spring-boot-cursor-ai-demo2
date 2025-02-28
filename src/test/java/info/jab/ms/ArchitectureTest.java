package info.jab.ms;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.onionArchitecture;

public class ArchitectureTest {

    private final JavaClasses classes = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("info.jab.ms");

    @Test
    public void onionArchitectureShouldBeRespected() {
        var architecture = onionArchitecture()
                .domainModels("..domain.model..")
                .domainServices("..domain.service..")
                .applicationServices("..application..")
                .adapter("web", "..adapter.web..")
                .adapter("persistence", "..adapter.persistence..")
                .adapter("config", "..config..");

        architecture.check(classes);
    }

    @Test
    public void domainModelsShouldNotDependOnAnyOtherLayer() {
        ArchRule rule = noClasses()
                .that().resideInAPackage("..domain.model..")
                .should().dependOnClassesThat().resideInAnyPackage(
                        "..domain.service..",
                        "..application..",
                        "..adapter..",
                        "..config..");

        rule.check(classes);
    }

    @Test
    public void domainServicesShouldOnlyDependOnDomainModels() {
        ArchRule rule = classes()
                .that().resideInAPackage("..domain.service..")
                .and().haveSimpleNameNotEndingWith("Test")
                .should().onlyDependOnClassesThat().resideInAnyPackage(
                        "..domain.service..",
                        "..domain.model..",
                        "java..",
                        "org.springframework..",
                        "javax..",
                        "jakarta..");

        rule.check(classes);
    }

    @Test
    public void applicationServicesShouldNotDependOnAdapters() {
        ArchRule rule = noClasses()
                .that().resideInAPackage("..application..")
                .should().dependOnClassesThat().resideInAPackage("..adapter..");

        rule.check(classes);
    }

    @Test
    public void adaptersShouldNotDependOnEachOther() {
        ArchRule rule = noClasses()
                .that().resideInAPackage("..adapter.web..")
                .should().dependOnClassesThat().resideInAPackage("..adapter.persistence..")
                .andShould().dependOnClassesThat().resideInAPackage("..adapter.config..");

        rule.check(classes);
    }

    @Test
    public void domainModelClassesShouldFollowNamingConvention() {
        ArchRule rule = classes()
                .that().resideInAPackage("..domain.model..")
                .and().haveSimpleNameNotEndingWith("Test")
                .and().haveSimpleNameNotEndingWith("Repository")
                .should().haveSimpleNameNotEndingWith("DTO")
                .andShould().haveSimpleNameNotEndingWith("Controller")
                .andShould().haveSimpleNameNotEndingWith("Service")
                .because("Domain model classes should represent pure domain concepts");

        rule.check(classes);
    }

    @Test
    public void applicationClassesShouldHaveAppropriateNames() {
        ArchRule rule = classes()
                .that().resideInAPackage("..application..")
                .and().haveSimpleNameNotEndingWith("Test")
                .should().haveSimpleNameEndingWith("Service")
                .orShould().haveSimpleNameEndingWith("UseCase")
                .because("Application classes should be use cases or services");

        rule.check(classes);
    }

    @Test
    public void webAdapterClassesShouldFollowNamingConvention() {
        ArchRule rule = classes()
                .that().resideInAPackage("..adapter.web..")
                .and().haveSimpleNameNotEndingWith("Test")
                .and().haveSimpleNameNotEndingWith("IntegrationTest")
                .should().haveSimpleNameEndingWith("Controller")
                .orShould().haveSimpleNameEndingWith("DTO")
                .because("Web adapter classes should be controllers or DTOs");

        rule.check(classes);
    }

    @Test
    public void persistenceAdapterClassesShouldFollowNamingConvention() {
        ArchRule rule = classes()
                .that().resideInAPackage("..adapter.persistence..")
                .and().areInterfaces()
                .and().haveSimpleNameNotEndingWith("Test")
                .should().haveSimpleNameEndingWith("Repository")
                .because("Persistence adapter interfaces should be repositories");

        rule.check(classes);
    }
} 