/*
 * Copyright 2021 Uppsala University Library
 *
 * This file is part of Cora.
 *
 *     Cora is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Cora is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Cora.  If not, see <http://www.gnu.org/licenses/>.
 */
package se.uu.ub.cora.indexloader;

import java.util.ArrayList;
import java.util.List;

import se.uu.ub.cora.javaclient.cora.CoraClient;
import se.uu.ub.cora.javaclient.cora.CoraClientFactory;

public class CoraClientFactorySpy implements CoraClientFactory {

	public List<CoraClientSpy> factoredClientSpies = new ArrayList<>();
	public String appTokenVerifierUrl;
	public String baseUrl;
	public String userId;
	public String appToken;
	public String authToken;

	public CoraClientFactorySpy(String appTokenVerifierUrl, String baseUrl) {
		this.appTokenVerifierUrl = appTokenVerifierUrl;
		this.baseUrl = baseUrl;
	}

	@Override
	public CoraClient factor(String userId, String appToken) {
		this.userId = userId;
		this.appToken = appToken;
		CoraClientSpy coraClientSpy = new CoraClientSpy();
		factoredClientSpies.add(coraClientSpy);
		return coraClientSpy;
	}

	public static CoraClientFactorySpy usingAppTokenVerifierUrlAndBaseUrl(
			String appTokenVerifierUrl, String baseUrl) {
		return new CoraClientFactorySpy(appTokenVerifierUrl, baseUrl);
	}

	@Override
	public CoraClient factorUsingAuthToken(String authToken) {
		this.authToken = authToken;
		CoraClientSpy coraClientSpy = new CoraClientSpy();
		factoredClientSpies.add(coraClientSpy);
		return coraClientSpy;
	}

}
