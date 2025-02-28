package info.jab.ms.config;

import info.jab.ms.domain.service.ActorDomainService;
import info.jab.ms.domain.service.ActorDomainServiceImpl;
import info.jab.ms.domain.model.ActorRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for domain layer beans.
 */
@Configuration
public class DomainConfig {
    
    /**
     * Creates a bean for the actor domain service.
     *
     * @param actorRepository the actor repository
     * @return the actor domain service
     */
    @Bean
    public ActorDomainService actorDomainService(ActorRepository actorRepository) {
        return new ActorDomainServiceImpl(actorRepository);
    }
} 