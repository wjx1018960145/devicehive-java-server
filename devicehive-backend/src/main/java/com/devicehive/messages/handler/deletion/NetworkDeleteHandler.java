package com.devicehive.messages.handler.deletion;

/*
 * #%L
 * DeviceHive Common Module
 * %%
 * Copyright (C) 2016 - 2017 DataArt
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


import com.devicehive.eventbus.EventBus;
import com.devicehive.model.rpc.NetworkDeleteRequest;
import com.devicehive.model.rpc.NetworkDeleteResponse;
import com.devicehive.shim.api.Request;
import com.devicehive.shim.api.Response;
import com.devicehive.shim.api.server.RequestHandler;
import com.devicehive.vo.DeviceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class NetworkDeleteHandler implements RequestHandler {

    private EventBus eventBus;
    @Autowired
    public void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public Response handle(Request request) {
        final NetworkDeleteRequest req = (NetworkDeleteRequest) request.getBody();
        final Long networkId = req.getNetworkId();
        final Set<DeviceVO> devices = req.getDevices();

        eventBus.unsubscribeNetwork(networkId, devices);

        return Response.newBuilder()
                .withBody(new NetworkDeleteResponse())
                .withCorrelationId(request.getCorrelationId())
                .buildSuccess();
    }
}