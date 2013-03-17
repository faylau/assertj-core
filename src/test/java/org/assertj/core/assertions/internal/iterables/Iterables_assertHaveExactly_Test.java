/*
 * Created on Mar 17, 2012
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 * 
 * Copyright @2012 the original author or authors.
 */
package org.assertj.core.assertions.internal.iterables;

import static org.assertj.core.assertions.error.ElementsShouldHaveExactly.elementsShouldHaveExactly;
import static org.assertj.core.assertions.test.TestData.someInfo;
import static org.assertj.core.test.TestFailures.failBecauseExpectedAssertionErrorWasNotThrown;
import static org.assertj.core.util.Lists.newArrayList;


import static org.mockito.Mockito.verify;

import org.assertj.core.assertions.core.AssertionInfo;
import org.assertj.core.assertions.core.Condition;
import org.assertj.core.assertions.internal.Iterables;
import org.assertj.core.assertions.internal.IterablesWithConditionsBaseTest;
import org.junit.Test;


/**
 * Tests for <code>{@link Iterables#assertHaveExactly(AssertionInfo, Iterable, Condition, int)}</code> .
 * 
 * @author Nicolas François
 * @author Mikhail Mazursky
 * @author Joel Costigliola
 */
public class Iterables_assertHaveExactly_Test extends IterablesWithConditionsBaseTest {

  @Test
  public void should_pass_if_satisfies_exactly_times_condition() {
    actual = newArrayList("Yoda", "Luke", "Leia");
    iterables.assertHaveExactly(someInfo(), actual, 2, jediPower);
    verify(conditions).assertIsNotNull(jediPower);
  }

  @Test
  public void should_throw_error_if_condition_is_null() {
    thrown.expectNullPointerException("The condition to evaluate should not be null");
    actual = newArrayList("Yoda", "Luke");
    iterables.assertHaveExactly(someInfo(), actual, 2, null);
    verify(conditions).assertIsNotNull(null);
  }

  @Test
  public void should_fail_if_condition_is_not_met_enought() {
    testCondition.shouldMatch(false);
    AssertionInfo info = someInfo();
    try {
      actual = newArrayList("Yoda", "Solo", "Leia");
      iterables.assertHaveExactly(someInfo(), actual, 2, jediPower);
    } catch (AssertionError e) {
      verify(conditions).assertIsNotNull(jediPower);
      verify(failures).failure(info, elementsShouldHaveExactly(actual, 2, jediPower));
      return;
    }
    failBecauseExpectedAssertionErrorWasNotThrown();
  }

  @Test
  public void should_fail_if_condition_is_met_much() {
    testCondition.shouldMatch(false);
    AssertionInfo info = someInfo();
    try {
      actual = newArrayList("Yoda", "Luke", "Obiwan");
      iterables.assertHaveExactly(someInfo(), actual, 2, jediPower);
    } catch (AssertionError e) {
      verify(conditions).assertIsNotNull(jediPower);
      verify(failures).failure(info, elementsShouldHaveExactly(actual, 2, jediPower));
      return;
    }
    failBecauseExpectedAssertionErrorWasNotThrown();
  }

}