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
import iot.technology.client.toolkit.nb.service.lwm2m.domain.Lwm2mConfigSettingsDomain;
import iot.technology.client.toolkit.nb.service.processor.Lwm2mProcessContext;
import org.eclipse.leshan.client.resource.LwM2mObjectEnabler;
import org.eclipse.leshan.client.resource.LwM2mObjectTree;
import org.eclipse.leshan.core.model.ObjectModel;
import org.eclipse.leshan.core.model.ResourceModel;

import java.util.List;

/**
 * @author mushuwei
 */
public class LwM2MListProcessor extends TkAbstractProcessor implements TkProcessor {

    @Override
    public boolean supports(ProcessContext context) {
        return context.getData().startsWith("list");
    }

    @Override
    public void handle(ProcessContext context) {
        Lwm2mProcessContext lwm2mProcessContext = (Lwm2mProcessContext) context;
        Lwm2mConfigSettingsDomain domain = lwm2mProcessContext.getDomain();

        LwM2mObjectTree objectTree = domain.getLeshanClient().getObjectTree();
        if (objectTree == null) {
            System.out.println("no object.");
            return;
        }
        // print all objects
        objectTree.getObjectEnablers().forEach((objectId, objectEnabler) -> {
            printObject(objectEnabler);
        });
    }

    public void printObject(LwM2mObjectEnabler objectEnabler) {
        ObjectModel objectModel = objectEnabler.getObjectModel();
        objectEnabler.getAvailableInstanceIds().forEach(instance -> {
            System.out.println(String.format("/%d/%d :  %s", objectModel.id, instance, objectModel.name));
            List<Integer> availableResources = objectEnabler.getAvailableResourceIds(instance);
            availableResources.forEach(resourceId -> {
                ResourceModel resourceModel = objectModel.resources.get(resourceId);
                System.out.println(String.format("/%d :  %s",  resourceId, resourceModel.name));
            });
        });
    }
}
