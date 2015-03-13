package org.oxenburgh

import spock.lang.Ignore
import spock.lang.Specification

class HelloSpockTest extends Specification {

    def "length of Spock's and his friends' names"() {
        expect:
        name.size() == length

        where:
        name     | length
        "Spock"  | 5
        "Kirk"   | 4
        "Scotty" | 6
    }

    def 'demo method 1'(){
        given: 'demo given 1'
        true
        when: 'demo when 1'
        true
        then: 'demo then 1'
        true
    }

    def 'demo method 3'(){
        given: 'demo given 1'
        true
        when: 'demo when 1'
        true
        then: 'demo then 1'
        true
    }

    def 'demo method 2'(){
        given: 'demo given 2'
        true
        when: 'demo when 2'
        true
        then: 'demo then 2'
        true
        and: 'demo then-and 2'
        true
    }

    def 'demo method error'(){
        given: 'demo given error'
        true
        when: 'demo when error'
        true
        then: 'demo then error'
        true
        and: 'demo then-and error'
        1 + 2 + 3 == 2 + 2 + 3
    }

    @Ignore
    def 'this test is ignored'(){
        given: '2 numbers'
        def i = 2
        def j = 3
        def expectedSum = 5
        when: 'we add them'
        def k = 2 + 3
        then: 'they sum to the correct number'
        k == expectedSum + 1
    }
}
