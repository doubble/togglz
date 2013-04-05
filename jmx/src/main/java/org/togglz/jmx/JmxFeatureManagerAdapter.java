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

import java.util.HashSet;
import java.util.Set;
import org.togglz.core.Feature;
import org.togglz.core.manager.FeatureManager;
import org.togglz.core.repository.FeatureState;
import org.togglz.core.util.UntypedFeature;

/**
 *
 * @author doubble
 */
public class JmxFeatureManagerAdapter implements JmxFeatureManager {

    private FeatureManager fm;

    /**
     *
     * @param fm
     */
    public JmxFeatureManagerAdapter(FeatureManager fm) {
        this.fm = fm;
    }

    /**
     *
     * @return
     */
    @Override
    public Set<String> getFeatures() {
        Set<String> features = new HashSet<String>();
        for (Feature feature : this.fm.getFeatures()) {
            features.add(feature.name());
        }
        return features;

    }

    /**
     *
     * @param newState
     */
    @Override
    public void setFetaureState(JmxFeatureState newState) {

        FeatureState previousState = fm.getFeatureState(new UntypedFeature(newState.getFeatureName()));
        previousState.setEnabled(newState.isEnabled());
        previousState.setStrategyId(newState.getStrategyId());
        previousState.getParameterMap().clear();
        previousState.getParameterMap().putAll(newState.getParameters());
        fm.setFeatureState(previousState);
    }

    /**
     *
     * @param featureName
     * @return
     */
    @Override
    public JmxFeatureState getFetaureState(String featureName) {
        FeatureState state = fm.getFeatureState(new UntypedFeature(featureName));
        return new JmxFeatureState(featureName, state.isEnabled(), state.getStrategyId(), state.getParameterMap());
    }
}
