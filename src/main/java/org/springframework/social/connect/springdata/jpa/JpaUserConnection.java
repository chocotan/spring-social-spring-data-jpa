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
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.social.connect.springdata.UserConnection;

/**
 * @author Michael Lavelle
 */
@Entity
@Table(name="UserConnection",uniqueConstraints = { @UniqueConstraint(columnNames = { "userId",
		"providerId", "rank" }) })
public class JpaUserConnection implements
		UserConnection<JpaUserConnectionKey> {

	@Id
	private JpaUserConnectionKey userConnectionKey = new JpaUserConnectionKey();

	private String accessToken;
	private String displayName;
	private Long expireTime;
	private String imageUrl;
	private String profileUrl;
	private int rank;
	private String refreshToken;
	private String secret;
	

	@Override
	public String getProviderId() {
		return userConnectionKey.getProviderId();
	}

	@Override
	public void setProviderId(String providerId) {
		userConnectionKey.setProviderId(providerId);
	}

	@Override
	public String getProviderUserId() {
		return userConnectionKey.getProviderUserId();
	}

	@Override
	public void setProviderUserId(String providerUserId) {
		userConnectionKey.setProviderUserId(providerUserId);
	}

	@Override
	public String getUserId() {
		return userConnectionKey.getUserId();
	}

	@Override
	public void setUserId(String userId) {
		userConnectionKey.setUserId(userId);
	}

	@Override
	public void setUserConnectionKey(JpaUserConnectionKey connectionKey) {
		this.userConnectionKey = connectionKey;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Long expireTime) {
		this.expireTime = expireTime;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getProfileUrl() {
		return profileUrl;
	}

	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	
	
}
