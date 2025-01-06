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
import org.eclipse.leshan.client.resource.LwM2mObjectTree;

import java.util.List;

/**
 * @author mushuwei
 */
public class LwM2MDeleteProcessor extends TkAbstractProcessor implements TkProcessor {

    @Override
    public boolean supports(ProcessContext context) {
        return context.getData().startsWith("del");
    }

    @Override
    public void handle(ProcessContext context) {
        Lwm2mProcessContext lwm2mProcessContext = (Lwm2mProcessContext) context;
        Lwm2mConfigSettingsDomain domain = lwm2mProcessContext.getDomain();

        List<String> arguArgs = List.of(context.getData().split(" "));
        if (arguArgs.size() > 2) {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format(ColorUtils.redError("argument:%s is illegal"), context.getData()))
                    .append(StringUtils.lineSeparator());
            sb.append(ColorUtils.blackBold("detail usage please enter: help delete"));
            System.out.println(sb);
            return;
        }
        Integer objectId = null;
        if (arguArgs.size() == 2) {
            String objectIdStr = arguArgs.get(1);
            if (!validateParam(objectIdStr)) {
                StringBuilder sb = new StringBuilder();
                sb.append(ColorUtils.redError("objectId is not a number")).append(StringUtils.lineSeparator);
                sb.append(ColorUtils.blackBold("detail usage please enter: help delete"));
                System.out.println(sb);
                return;
            }
            objectId = Integer.valueOf(objectIdStr);
        }

        LwM2mObjectTree objectTree = domain.getLeshanClient().getObjectTree();
        if (objectTree == null || objectId == null) {
            System.out.println("no object.");
            return;
        }

        if (objectId == 0 || objectId == 1 || objectId == 3) {
            System.out.println(ColorUtils.redError(
                    String.format("Object %d can not be disabled.", objectId)));
            return;
        }
        if (objectTree.getObjectEnabler(objectId) == null) {
            System.out.println(ColorUtils.redError(
                    String.format("Object %d can not be disabled.", objectId)));
            return;
        }
        objectTree.removeObjectEnabler(objectId);
    }
}
