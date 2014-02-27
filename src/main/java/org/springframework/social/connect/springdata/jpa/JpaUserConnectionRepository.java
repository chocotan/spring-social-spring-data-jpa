package org.springframework.social.connect.springdata.jpa;

import java.util.List;

import org.springframework.social.connect.springdata.UserConnectionRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface JpaUserConnectionRepository extends
		UserConnectionRepository<JpaUserConnectionKey,JpaUserConnection> {

	List<JpaUserConnection> findByUserConnectionKeyProviderIdAndUserConnectionKeyProviderUserId(String providerId,String providerUserId);

}
