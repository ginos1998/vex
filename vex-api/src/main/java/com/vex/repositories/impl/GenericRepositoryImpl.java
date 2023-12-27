package com.vex.repositories.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.r2dbc.core.DatabaseClient;

@Getter
@RequiredArgsConstructor
@Primary
public abstract class GenericRepositoryImpl {
    protected final DatabaseClient databaseClient;
}
