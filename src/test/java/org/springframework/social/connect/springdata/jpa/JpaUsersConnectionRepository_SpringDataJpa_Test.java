package org.springframework.social.connect.springdata.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactory;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.jpa.JpaTemplate;
import org.springframework.social.connect.jpa.JpaUsersConnectionRepository;
import org.springframework.social.extension.connect.jdbc.AbstractUsersConnectionRepositoryTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/META-INF/spring/applicationContext.xml" })
public class JpaUsersConnectionRepository_SpringDataJpa_Test extends
		AbstractUsersConnectionRepositoryTest<JpaUsersConnectionRepository> {

	@Autowired
	private JpaTemplate dataAccessor;

	@PersistenceContext(unitName = "persistenceUnit")
	private EntityManager entityManager;

	private boolean testMySqlCompatiblity = true;

	private EmbeddedDatabase database;

	@Autowired
	private ResourceDatabasePopulator resourceDatabasePopulator;

	protected Boolean checkIfProviderConnectionsExist(String providerId) {
		Query query = entityManager
				.createQuery("select u from JpaUserConnection u where u.userConnectionKey.providerId = ? order by u.rank");
		query.setParameter(1, providerId);
		@SuppressWarnings("unchecked")
		List<JpaUserConnection> users = (List<JpaUserConnection>) query
				.getResultList();
		return users.size() > 0;

	}

	@Override
	protected JpaUsersConnectionRepository createUsersConnectionRepository() {

		EmbeddedDatabaseFactory factory = new EmbeddedDatabaseFactory();
		if (testMySqlCompatiblity) {
			factory.setDatabaseConfigurer(new DataSourceTestConfig.MySqlCompatibleH2DatabaseConfigurer());
		} else {
			factory.setDatabaseType(EmbeddedDatabaseType.H2);
		}

		factory.setDatabasePopulator(resourceDatabasePopulator);
		database = factory.getDatabase();

		return new JpaUsersConnectionRepository(dataAccessor,
				connectionFactoryRegistry, Encryptors.noOpText());

	}

	@After
	public void tearDown() {
		if (database != null) {
			database.shutdown();
		}
	}

	@Override
	protected void insertConnection(String userId, String providerId,
			String providerUserId, int rank, String displayName,
			String profileUrl, String imageUrl, String accessToken,
			String secret, String refreshToken, Long expireTime) {
		dataAccessor.createRemoteUser(userId, providerId, providerUserId, rank,
				displayName, profileUrl, imageUrl, accessToken, secret, null,
				null);

	}

	@Override
	protected void setConnectionSignUpOnUsersConnectionRepository(
			JpaUsersConnectionRepository usersConnectionRepository,
			ConnectionSignUp connectionSignUp) {
		usersConnectionRepository.setConnectionSignUp(connectionSignUp);
	}

}
