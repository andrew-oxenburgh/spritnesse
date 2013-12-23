Spritnesse 1.0
===

JUnit under Fitnesse


Why
===

Fitnesse is a great way to write integration tests, but it misses out in a couple of places where Spritnesse is trying to change.

1. It depends on a syntax which can be awkward to learn use, especially on large projects, and for testers.

2. It is often used for Acceptance tests, which may be able to re-use tests from the unit suite, but need to be re-written for use in the Acceptance suite.

Spritnesse allows a dev/tester to write tests in JUnit, then run them in Fitnesse and see the results as a table in fitnesse.

The results are manipulated in a 'user friendly' way.