/*
 * Copyright 2013 doubble.
 *
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
 */
package org.togglz.jmx;

import java.lang.management.ManagementFactory;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import org.togglz.core.logging.Log;
import org.togglz.core.logging.LogFactory;
import org.togglz.core.manager.FeatureManager;
import org.togglz.core.spi.FeatureManagerListener;

/**
 *
 * @author doubble
 */
public class JmxEnablingFeatureManagerListener implements FeatureManagerListener {

    static {
        // TODO register listener 
    }
    private static final String MX_BEAN_NAME_PART = "org.togglz.jmx.FeatureManagement";
    private final Log log = LogFactory.getLog(JmxEnablingFeatureManagerListener.class);

    @Override
    public void start(FeatureManager featureManager) {
        ObjectName objectName = this.generateObjectName(featureManager.getId());

        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        try {
            server.registerMBean(new JmxFeatureManagerAdapter(featureManager), objectName);
        } catch (InstanceAlreadyExistsException ex) {
            log.error("Instance with ObjectName " + objectName + " already registered");
        } catch (Exception ex) {
            log.error("Error while registering FeatureManager " + featureManager.getId(), ex);
        }

    }

    @Override
    public void stop(FeatureManager featureManager) {
        ObjectName objectName = this.generateObjectName(featureManager.getId());
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        try {
            server.unregisterMBean(objectName);
        } catch (InstanceNotFoundException ex) {
            log.error(objectName + " was not registered to the MbeanServer!?");
        } catch (MBeanRegistrationException ex) {
            log.info("Failed to unregister " + objectName);
        }
    }

    @Override
    public int priority() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     *
     * @param id
     * @return
     */
    protected static ObjectName generateObjectName(String id) {
        try {
            return new ObjectName(MX_BEAN_NAME_PART, "id", id);
        } catch (Exception ex) {
            throw new IllegalStateException("Unable to create ObjectName for " + id, ex);
        }
    }
}
