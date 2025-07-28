package org.keycloak.quickstart.storage.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.jboss.logging.Logger;
import org.keycloak.component.ComponentModel;
import org.keycloak.connections.jpa.JpaConnectionProvider;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputUpdater;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.models.GroupModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RoleModel;
import org.keycloak.models.UserCredentialModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.cache.CachedUserModel;
import org.keycloak.models.cache.OnUserCache;
import org.keycloak.models.credential.PasswordCredentialModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.user.UserLookupProvider;
import org.keycloak.storage.user.UserQueryProvider;
import org.keycloak.storage.user.UserRegistrationProvider;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

public class MuserpolUserStorageProvider implements UserStorageProvider, UserLookupProvider, UserQueryProvider {

    private final KeycloakSession session;
    private final ComponentModel model;
    private final UserRepository userRepository;

    public MuserpolUserStorageProvider(KeycloakSession session, ComponentModel model) {
        this.session = session;
        this.model = model;
        this.userRepository = new UserRepository(); // implementado por ti usando JPA o JDBC
    }

    private UserModel mapToUserModel(UserEntity userEntity, RealmModel realm) {
        Map<String, String> attributes = new HashMap<>();
        attributes.put("first_name", userEntity.getFirstName());
        attributes.put("second_name", userEntity.getSecondName());
        attributes.put("lastname", userEntity.getLastname());
        attributes.put("mother_lastname", userEntity.getMotherLastname());
        attributes.put("birthdate", userEntity.getBirthdate().toString());
        attributes.put("celphone", userEntity.getCelphone());

        Set<String> roles = userEntity.getRoles() != null ? new HashSet<>(userEntity.getRoles()) : Set.of();

        return new MuserpolUserAdapter(session, realm, model, userEntity.getId(), userEntity.getCi(), attributes, roles);
    }

    @Override
    public UserModel getUserByUsername(RealmModel realm, String ci) {
        Optional<UserEntity> userOpt = userRepository.findByCi(ci);
        return userOpt.map(user -> mapToUserModel(user, realm)).orElse(null);
    }

    @Override
    public UserModel getUserById(RealmModel realm, String id) {
        StorageId storageId = new StorageId(id);
        UUID uuid = UUID.fromString(storageId.getExternalId());
        Optional<UserEntity> userOpt = userRepository.findById(uuid);
        return userOpt.map(user -> mapToUserModel(user, realm)).orElse(null);
    }

    @Override
    public UserModel getUserByEmail(RealmModel realm, String email) {
        return null; // no usamos email
    }

    @Override
    public Stream<UserModel> getUsersStream(RealmModel realm, int firstResult, int maxResults) {
        return userRepository.findAll(firstResult, maxResults).stream()
                .map(user -> mapToUserModel(user, realm));
    }

    @Override
    public void close() {}
}