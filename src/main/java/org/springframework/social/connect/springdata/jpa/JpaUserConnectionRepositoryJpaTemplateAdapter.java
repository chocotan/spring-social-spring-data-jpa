package org.springframework.social.connect.springdata.jpa;
/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.social.connect.jpa.RemoteUser;
import org.springframework.social.connect.springdata.AbstractUserConnectionRepositoryJpaTemplateAdapter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Michael Lavelle
 */
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
