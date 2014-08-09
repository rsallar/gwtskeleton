package com.gwtskeleton.client.gin;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.gwtplatform.mvp.client.PreBootstrapper;

public class PreBootstrapperImpl implements PreBootstrapper {
	
	Logger logger = Logger.getLogger(PreBootstrapperImpl.class.getName());
    @Override
    public void onPreBootstrap() {
        GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
            @Override
            public void onUncaughtException(final Throwable e) {
            	logger.log(Level.SEVERE, e.getMessage(), e);
            }
        });
    }
} 