/*
 * Copyright © 2019-2025 The Toolkit Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package iot.technology.client.toolkit.nb.service.processor.lwm2m;

import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.common.rule.TkAbstractProcessor;
import iot.technology.client.toolkit.common.rule.TkProcessor;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;
import iot.technology.client.toolkit.nb.service.lwm2m.domain.Lwm2mConfigSettingsDomain;
import iot.technology.client.toolkit.nb.service.processor.Lwm2mProcessContext;
import org.eclipse.leshan.client.send.ManualDataSender;
import org.eclipse.leshan.client.send.NoDataException;
import org.eclipse.leshan.client.send.SendService;
import org.eclipse.leshan.client.servers.ServerIdentity;
import org.eclipse.leshan.core.node.InvalidLwM2mPathException;
import org.eclipse.leshan.core.node.LwM2mPath;
import org.eclipse.leshan.core.request.ContentFormat;
import org.eclipse.leshan.core.response.ErrorCallback;
import org.eclipse.leshan.core.response.ResponseCallback;
import org.eclipse.leshan.core.response.SendResponse;

import java.util.*;

/**
 * @author mushuwei
 */
public class LwM2MSendProcessor extends TkAbstractProcessor implements TkProcessor {

    public static final long timeout = 2000;

    public static final ContentFormat SENML_CBOR = ContentFormat.SENML_CBOR;

    @Override
    public boolean supports(ProcessContext context) {
        return context.getData().startsWith("send");
    }

    @Override
    public void handle(ProcessContext context) {
        Lwm2mProcessContext lwm2mProcessContext = (Lwm2mProcessContext) context;
        Lwm2mConfigSettingsDomain domain = lwm2mProcessContext.getDomain();

        List<String> arguArgs = List.of(context.getData().split(" "));
        if (arguArgs.size() > 3) {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format(ColorUtils.redError("argument:%s is illegal"), context.getData()))
                    .append(StringUtils.lineSeparator());
            sb.append(ColorUtils.blackBold("detail usage please enter: help send"));
            System.out.println(sb);
            return;
        }

        ContentFormat paramFormat = SENML_CBOR;
        List<String> paths = new ArrayList<>();
        if (arguArgs.size() == 2) {
            List<String> pathPreList = Arrays.asList(arguArgs.get(1).split(","));
            pathPreList.forEach(pr -> {
                try {
                    new LwM2mPath(pr);
                    paths.add(pr);
                } catch (InvalidLwM2mPathException e) {
                    System.out.println(String.format(ColorUtils.redError("path:%s is illegal"), pr));
                }
            });
        }

        if (arguArgs.size() == 3) {
            String format = arguArgs.get(2);
            if (format.equals("1")) {
            } else if (format.equals("2")) {
                paramFormat = ContentFormat.SENML_JSON;
            } else {
                System.out.println(String.format(ColorUtils.redError("format:%s is illegal"), context.getData()));
                return;
            }
        }
        Map<String, ServerIdentity> registeredServers = domain.getLeshanClient().getRegisteredServers();
        if (registeredServers.isEmpty()) {
            System.out.println(ColorUtils.blackBold("There is no registered server to send to."));
            return;
        }

        // for each server send data
        for (final ServerIdentity server : registeredServers.values()) {
            System.out.println(String.format("Sending Collected data to %s using %s.", server, paramFormat));
            // send collected data
            SendService sendService = domain.getLeshanClient().getSendService();
            ResponseCallback<SendResponse> responseCallback = (response) -> {
                if (response.isSuccess())
                    System.out.println(String.format("Data sent successfully to {} [{}].", server, response.getCode()));
                else
                    System.out.println(String.format("Send data to {} failed [{}] : {}.", server, response.getCode(),
                            response.getErrorMessage() == null ? "" : response.getErrorMessage()));
            };
            ErrorCallback errorCallback = (e) -> System.out.println(String.format("Unable to send data to {}.", server, e));
            try {
                sendService.sendData(server, paramFormat, paths, timeout, responseCallback, errorCallback);
            } catch (NoDataException e) {
                System.out.println(ColorUtils.blackBold("No data collected"));
            }

        }
    }
}
