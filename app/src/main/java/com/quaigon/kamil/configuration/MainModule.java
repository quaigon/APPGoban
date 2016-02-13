package com.quaigon.kamil.configuration;

import com.google.inject.AbstractModule;
import com.quaigon.kamil.connection.AuthenticationRepository;
import com.quaigon.kamil.connection.AuthenticationRepositoryImp;

/**
 * Created by Kamil on 08.02.2016.
 */
public class MainModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(AuthenticationRepository.class).to(AuthenticationRepositoryImp.class);
    }
}
