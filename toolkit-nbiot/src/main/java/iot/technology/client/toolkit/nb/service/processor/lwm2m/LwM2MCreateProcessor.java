/*
 * Copyright © 2019-2023 The Toolkit Authors
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
import org.eclipse.leshan.client.resource.LwM2mObjectEnabler;
import org.eclipse.leshan.client.resource.LwM2mObjectTree;
import org.eclipse.leshan.client.resource.ObjectsInitializer;
import org.eclipse.leshan.core.model.ObjectModel;
import org.eclipse.leshan.core.model.StaticModel;

import java.util.List;

/**
 * @author mushuwei
 */
public class LwM2MCreateProcessor extends TkAbstractProcessor implements TkProcessor {

    @Override
    public boolean supports(ProcessContext context) {
        return context.getData().startsWith("create");
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
            sb.append(ColorUtils.blackBold("detail usage please enter: help create"));
            System.out.println(sb);
            return;
        }
        Integer objectId = null;
        if (arguArgs.size() == 2) {
            String objectIdStr = arguArgs.get(1);
            if (!validateParam(objectIdStr)) {
                StringBuilder sb = new StringBuilder();
                sb.append(ColorUtils.redError("objectId is not a number")).append(StringUtils.lineSeparator);
                sb.append(ColorUtils.blackBold("detail usage please enter: help create"));
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

        if (objectTree.getObjectEnabler(objectId) != null) {
            System.out.println(ColorUtils.redError(
                    String.format("Object %d already enabled.", objectId)));
        }
        ObjectModel objectModel = domain.getRepository().getObjectModel(objectId);
        if (objectModel == null) {
            System.out.println(ColorUtils.redError(
                    String.format("Unable to enable Object %d : there no model for this object.", objectId)));

        } else {
            ObjectsInitializer objectsInitializer = new ObjectsInitializer(new StaticModel(objectModel));
            objectsInitializer.setDummyInstancesForObject(objectId);
            LwM2mObjectEnabler object = objectsInitializer.create(objectId);
            objectTree.addObjectEnabler(object);
        }

    }
}
