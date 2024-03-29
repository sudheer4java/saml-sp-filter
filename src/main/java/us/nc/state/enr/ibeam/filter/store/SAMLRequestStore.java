package us.nc.state.enr.ibeam.filter.store;

import java.util.HashSet;
import java.util.Set;

import org.opensaml.common.IdentifierGenerator;
import org.opensaml.common.impl.RandomIdentifierGenerator;

final public class SAMLRequestStore {
	private Set<String> samlRequestStorage = new HashSet<String>();
	private IdentifierGenerator identifierGenerator = new RandomIdentifierGenerator();
	private static SAMLRequestStore instance = new SAMLRequestStore();

	private SAMLRequestStore() {
	}

	public static SAMLRequestStore getInstance() {
		return instance;
	}

	public synchronized void storeRequest(String key) {
		if (samlRequestStorage.contains(key))
			throw new RuntimeException("SAML request storage has already contains key " + key);

		samlRequestStorage.add(key);
	}

	public synchronized String storeRequest() {
		String key = null;
		while (true) {
			key = identifierGenerator.generateIdentifier(20);
			if (!samlRequestStorage.contains(key)) {
				storeRequest(key);
				break;
			}
		}
		return key;
	}

	public synchronized boolean exists(String key) {
		return samlRequestStorage.contains(key);
	}

	public synchronized void removeRequest(String key) {
		samlRequestStorage.remove(key);
	}
}