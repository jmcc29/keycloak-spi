package org.keycloak.quickstart.storage.user;

import org.jboss.logging.Logger;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.storage.UserStorageProviderFactory;

public class MuserpolUserStorageProviderFactory implements UserStorageProviderFactory<MuserpolUserStorageProvider> {

    public static final String PROVIDER_ID = "muserpol-user-provider";
    private static final Logger logger = LoggerFactory.getLogger(MuserpolUserStorageProviderFactory.class);

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public void init(Config.Scope config) {
        logger.info("[MUSERPOL SPI] Inicializando configuración global del proveedor externo");
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
        logger.info("[MUSERPOL SPI] Post inicialización");
    }

    @Override
    public void close() {
        logger.info("[MUSERPOL SPI] Cerrando factory");
    }

    @Override
    public MuserpolUserStorageProvider create(KeycloakSession session, ComponentModel model) {
        logger.info("[MUSERPOL SPI] Creando instancia del proveedor");
        return new MuserpolUserStorageProvider(session, model);
    }
}