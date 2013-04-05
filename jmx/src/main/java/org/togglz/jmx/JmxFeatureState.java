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

import java.beans.ConstructorProperties;
import java.util.Map;
import org.togglz.core.Feature;
import org.togglz.core.repository.FeatureState;

/**
 *
 * @author doubble
 */
public class JmxFeatureState {

    private String featureName;
    private boolean enabled;
    private String strategyId;
    private Map<String, String> parameters;

    /**
     *
     * @param featureName
     * @param enabled
     * @param strategyId
     * @param parameters
     */
    @ConstructorProperties({"featureName", "enabled", "strategyId", "parameters"})
    public JmxFeatureState(String featureName, boolean enabled, String strategyId, Map<String, String> parameters) {
        this.featureName = featureName;
        this.enabled = enabled;
        this.strategyId = strategyId;
        this.parameters = parameters;
    }

    /**
     *
     * @param featureName
     */
    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    /**
     *
     * @param enabled
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     *
     * @param strategyId
     */
    public void setStrategyId(String strategyId) {
        this.strategyId = strategyId;
    }

    /**
     *
     * @param parameters
     */
    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    /**
     *
     * @return
     */
    public String getFeatureName() {
        return featureName;
    }

    /**
     *
     * @return
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     *
     * @return
     */
    public String getStrategyId() {
        return strategyId;
    }

    /**
     *
     * @return
     */
    public Map<String, String> getParameters() {
        return parameters;
    }
}
