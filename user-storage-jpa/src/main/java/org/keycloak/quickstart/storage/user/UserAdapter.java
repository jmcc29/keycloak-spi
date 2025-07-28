package main.java.org.keycloak.quickstart.storage.user;

import org.jboss.logging.Logger;
import org.keycloak.common.util.MultivaluedHashMap;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.adapter.AbstractUserAdapterFederatedStorage;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

public class UserAdapter implements UserStorageProvider, UserLookupProvider, UserQueryProvider {

    private final UUID id;
    private final String ci;
    private final Map<String, String> attributes;
    private final Set<String> roles;

    public UserAdapter(KeycloakSession session, RealmModel realm, ComponentModel model, UUID id, String ci, Map<String, String> attributes, Set<String> roles)
                             {
        super(session, realm, model);
        this.id = id;
        this.ci = ci;
        this.attributes = attributes;
        this.roles = roles;
    }

    @Override
    public String getUsername() {
        return ci;
    }

    @Override
    public String getId() {
        return StorageId.keycloakId(model, id.toString());
    }

    @Override
    public String getFirstAttribute(String name) {
        return attributes.get(name);
    }

    @Override
    public Map<String, List<String>> getAttributes() {
        return attributes.entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                e -> List.of(e.getValue())
            ));
    }

    @Override
    public Set<String> getRoleMappingsStream() {
        return roles;
    }

    // Disable password login
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isEmailVerified() {
        return true;
    }
}
