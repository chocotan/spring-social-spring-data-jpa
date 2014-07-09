spring-social-spring-data-jpa
==================================


Spring Social UsersConnectionRepository implementation using a SpringData JPA Repository for persistence as an alternative to JDBC versions in spring-social-core


## Quick Start ##

- 1. Download the jar though Maven:


```xml
<repository>
	<id>opensourceagility-snapshots</id>
	<url>http://repo.opensourceagility.com/snapshots</url
</repository>
```

```xml
<dependency>
  <groupId>org.springframework.social</groupId>
  <artifactId>spring-social-spring-data-jpa</artifactId>
  <version>1.1.0-SNAPSHOT</version>
</dependency>
```

- 2.  Ensure you have a persistence.xml file for a persistence unit of name "persistenceUnit" enabled for Hibernate in src/main/resources/META-INF directory.  Add <class>org.springframework.social.connect.springdata.jpa.JpaUserConnection</class> to your persistence.xml, eg:

```
       <?xml version="1.0" encoding="UTF-8" standalone="no"?>
<persistence xmlns="http://java.sun.com/xml/ns/persistence" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" version="2.0" xsi:schemaLocation="http://java.sun.com/xml/ns/persistence http://java.sun.com/xml/ns/persistence/persistence_2_0.xsd">
<persistence-unit name="persistenceUnit" transaction-type="RESOURCE_LOCAL">
        <provider>org.hibernate.ejb.HibernatePersistence</provider>
        <class>org.springframework.social.connect.springdata.jpa.JpaUserConnection</class> 
        <properties>
            <property name="hibernate.dialect" value="org.hibernate.dialect.MySQLDialect"/>
            <!-- value="create" to build a new database on each run; value="update" to modify an existing database; value="create-drop" means the same as "create" but also drops tables when Hibernate closes; value="validate" makes no changes to the database -->
            <property name="hibernate.hbm2ddl.auto" value="create"/>
            <property name="hibernate.connection.charSet" value="UTF-8"/>
            <!-- Uncomment the following two properties for JBoss only -->
            <!-- property name="hibernate.validator.apply_to_ddl" value="false" /-->
            <!-- property name="hibernate.validator.autoregister_listeners" value="false" /-->
        </properties>
    </persistence-unit>
</persistence>
            ....


- 3. Setup JPA configuration as well as enabling Spring Social Spring Data JPA repository support.

```java
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef="entityManagerFactoryBean",basePackages = "org.springframework.social.connect.springdata.jpa")
public class JpaConfig {

	...

	@Bean 
	public JpaTransactionManager transactionManager(EntityManagerFactory emf)
	{
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(emf);
		return txManager;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean()
	{
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setDataSource(dataSource());
		entityManagerFactory.setPersistenceUnitName("persistenceUnit");
		return entityManagerFactory;
	}
}
```


- 4. Create a Jpa Repository JpaTemplate bean instance in your application context

```
@Bean
public JpaTemplate springDataTemplate()
{
	return new JpaUserConnectionRepositoryJpaTemplateAdapter();
}
```
- 5. Replace the JdbcUsersConnectionRepository returned by the SocialConfigurer.getUsersConnectionrepository method of your SocialConfig with JpaUsersConnectionRepository,
wiring in your JpaTemplate bean

```
public class SocialConfig implements SocialConfigurer 
{
....
@Override
	public UsersConnectionRepository getUsersConnectionRepository(

		UsersConnectionRepository usersConnectionRepository = new JpaUsersConnectionRepository(
				springDataTemplate(), connectionFactoryLocator, Encryptors.noOpText());
                
		return usersConnectionRepository;
		
	}
```

