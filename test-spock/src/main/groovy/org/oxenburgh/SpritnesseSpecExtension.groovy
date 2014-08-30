package org.oxenburgh

import org.spockframework.runtime.extension.IGlobalExtension
import org.spockframework.runtime.model.SpecInfo
import spock.lang.Ignore
import spock.lang.Specification

class SpritnesseSpecExtension implements IGlobalExtension {

    @Override
    Object invokeMethod(String name, Object args) {
    }

    @Override
    void visitSpec(SpecInfo specInfo) {
        specInfo.addListener(new SpockListener())
    }
}
