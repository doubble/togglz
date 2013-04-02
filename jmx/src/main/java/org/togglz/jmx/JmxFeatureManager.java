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

import java.util.Set;
import javax.management.MXBean;

/**
 *
 * @author doubble
 */
@MXBean
public interface JmxFeatureManager {

    /**
     *
     * @return
     */
    Set<String> getFeatures();
    /**
     *
     * @param featureName
     * @return
     */
    public JmxFeatureState getFetaureState(String featureName);
    /**
     *
     * @param newState
     */
    void setFetaureState(JmxFeatureState newState);
}
