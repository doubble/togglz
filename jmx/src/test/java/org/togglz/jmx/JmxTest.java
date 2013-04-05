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
import javax.management.JMX;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.togglz.core.Feature;
import org.togglz.core.activation.UsernameActivationStrategy;
import org.togglz.core.context.FeatureContext;
import org.togglz.core.manager.FeatureManager;
import org.togglz.core.manager.FeatureManagerBuilder;
import org.togglz.core.repository.mem.InMemoryStateRepository;
import org.togglz.core.user.NoOpUserProvider;

/**
 *
 * @author doubble
 */
public class JmxTest {

    private static FeatureManager featureManager;
    private MBeanServer server;
    private ObjectName name;

    /**
     * Feature under test
     */
    private enum MyFeatures implements Feature {

        DELETE_USERS,
        EXPERIMENTAL;

        @Override
        public boolean isActive() {
            return featureManager.isActive(this);
        }
    }

    @Before
    public void deployMBean() {
        featureManager = new FeatureManagerBuilder()
                .featureEnum(MyFeatures.class)
                .stateRepository(new InMemoryStateRepository())
                .userProvider(new NoOpUserProvider())
                .build();
//        FeatureContext 
        // TODO check exceptions in declaration of #start
        new JmxEnablingFeatureManagerListener().start(featureManager);
        server = ManagementFactory.getPlatformMBeanServer();
        name = JmxEnablingFeatureManagerListener.generateObjectName(featureManager.getId());
    }

    @Test
    public void testGetFeatures() throws Exception {
        JmxFeatureManager jmxFM = JMX.newMXBeanProxy(server, name, JmxFeatureManager.class);
        assertThat(jmxFM.getFeatures().size(), is(2));
    }
    
    @Test
    public void testGetFeatureState() throws Exception {
        JmxFeatureManager jmxFM = JMX.newMXBeanProxy(server, name, JmxFeatureManager.class);
        JmxFeatureState deleteUsersState = jmxFM.getFetaureState(MyFeatures.DELETE_USERS.name());
        assertThat(deleteUsersState.getFeatureName(), is (MyFeatures.DELETE_USERS.name()));
        assertThat(deleteUsersState.getStrategyId(), is (UsernameActivationStrategy.ID));
    }
}
