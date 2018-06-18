package com.projects.melih.wonderandwander.di;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import dagger.releasablereferences.CanReleaseReferences;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Melih Gültekin on 18.06.2018
 */
@Retention(RUNTIME)
@CanReleaseReferences
@Scope
public @interface ScopeFragment {
}