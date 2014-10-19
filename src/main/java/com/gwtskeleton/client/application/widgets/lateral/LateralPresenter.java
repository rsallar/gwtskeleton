package com.gwtskeleton.client.application.widgets.lateral;

/*
 * #%L
 * GwtBootstrap3
 * %%
 * Copyright (C) 2013 GwtBootstrap3
 * %%
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
 * #L%
 */

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtskeleton.client.application.ApplicationPresenter;
import com.gwtskeleton.client.application.place.NameTokens;

public class LateralPresenter extends Presenter<LateralPresenter.MyView, LateralPresenter.MyProxy> {

    public interface MyView extends View {
    }

    @NameToken(NameTokens.HOME)
    @ProxyCodeSplit
    public interface MyProxy extends ProxyPlace<LateralPresenter> {
    }

    @Inject
    public LateralPresenter(final EventBus eventBus,
                         final MyView view,
                         final MyProxy proxy) {
        super(eventBus, view, proxy);
    }
}