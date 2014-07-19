package org.oxenburgh

import org.spockframework.runtime.AbstractRunListener
import org.spockframework.runtime.model.ErrorInfo
import org.spockframework.runtime.model.FeatureInfo
import org.spockframework.runtime.model.SpecInfo

/**
 * Created by andrew on 20/07/14.
 */
class SpockListener extends AbstractRunListener {

    List<SpritnesseFeatureResult> results = []

    SpritnesseFeatureResult result = new SpritnesseFeatureResult()

    class SpritnesseFeatureResult {
        boolean skipped = false
        FeatureInfo featureInfo
        ErrorInfo errorInfo
    }


    @Override
    void beforeFeature(FeatureInfo featureInfo) {
        result.featureInfo = featureInfo
    }

    @Override
    void featureSkipped(FeatureInfo featureInfo) {
        result.featureInfo = featureInfo
        result.skipped = true
    }

    @Override
    void afterFeature(FeatureInfo feature) {
        results += result
    }

    @Override
    void error(ErrorInfo errorInfo) {
        result.errorInfo = errorInfo
    }

    @Override
    void afterSpec(SpecInfo spec) {
        String out = ''
        results.each {
            SpritnesseFeatureResult res ->
                out += res.skipped
        }
    }
}
