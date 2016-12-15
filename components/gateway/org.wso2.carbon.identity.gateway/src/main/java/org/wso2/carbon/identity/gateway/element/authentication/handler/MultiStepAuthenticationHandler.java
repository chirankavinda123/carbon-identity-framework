/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wso2.carbon.identity.gateway.element.authentication.handler;

import org.wso2.carbon.identity.framework.handler.AbstractHandler;
import org.wso2.carbon.identity.framework.handler.HandlerConfig;
import org.wso2.carbon.identity.framework.handler.HandlerException;
import org.wso2.carbon.identity.framework.handler.HandlerIdentifier;
import org.wso2.carbon.identity.framework.handler.HandlerResponseStatus;
import org.wso2.carbon.identity.gateway.context.GatewayMessageContext;

import java.util.ArrayList;
import java.util.List;

/**
 * This authenticator provides the ability to do multi option authentication in a step.
 */
public class MultiStepAuthenticationHandler extends AbstractHandler<HandlerIdentifier, HandlerConfig,
        AbstractHandler, GatewayMessageContext> {

    private List<AbstractHandler> abstractHandlers = new ArrayList<>();

    public MultiStepAuthenticationHandler(HandlerIdentifier handlerIdentifier) {

        super(handlerIdentifier);
    }


    public void addIdentityGatewayEventHandler(AbstractHandler abstractHandler) {

        this.abstractHandlers.add(abstractHandler);
    }


    @Override
    public HandlerResponseStatus handle(GatewayMessageContext messageContext) throws HandlerException {

        for (AbstractHandler abstractHandler : abstractHandlers) {
            if (abstractHandler.canHandle(messageContext)) {
                setNextHandler(abstractHandler);
                break;
            }
        }
        return HandlerResponseStatus.CONTINUE;
    }

    @Override
    public boolean canHandle(GatewayMessageContext messageContext) {

        return true;
    }
}