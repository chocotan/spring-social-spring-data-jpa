package org.springframework.social.connect.springdata.jpa;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.social.connect.jpa.RemoteUser;
import org.springframework.social.connect.springdata.AbstractUserConnectionRepositoryJpaTemplateAdapter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JpaUserConnectionRepositoryJpaTemplateAdapter
		extends
		AbstractUserConnectionRepositoryJpaTemplateAdapter<JpaUserConnectionKey, JpaUserConnection, JpaUserConnectionRepository> {

	@Override
	public JpaUserConnection instantiateRemoteUser() {
		return new JpaUserConnection();
	}

	@Override
	protected List<JpaUserConnection> findByProviderIdAndProviderUserId(
			String providerId, String providerUserId) {
		return repository
				.findByUserConnectionKeyProviderIdAndUserConnectionKeyProviderUserId(
						providerId, providerUserId);
	}

	@Override
	public JpaUserConnectionKey instantiateUserConnectionKey(String userId,
			String providerId, String providerUserId) {
		JpaUserConnectionKey key = new JpaUserConnectionKey();
		key.setProviderId(providerId);
		key.setProviderUserId(providerUserId);
		key.setUserId(userId);
		return key;
	}

	@Override
	@Transactional
	public void remove(String userId, String providerId, String providerUserId) {
		try {
			RemoteUser existing = get(userId, providerId, providerUserId);
			if (existing != null) {
				super.remove(userId, providerId, providerUserId);
			}
		} catch (EmptyResultDataAccessException e) {
			// No op
		}
	}

}
