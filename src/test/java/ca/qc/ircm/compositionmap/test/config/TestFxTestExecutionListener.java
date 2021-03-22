/*
 * Copyright (c) 2014 Institut de recherches cliniques de Montreal (IRCM)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package ca.qc.ircm.compositionmap.test.config;

import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

/**
 * Ensure JavaFX toolkit is started.
 */
public class TestFxTestExecutionListener extends AbstractTestExecutionListener {
  @Override
  public void beforeTestClass(TestContext testContext) throws Exception {
    ApplicationContext applicationContext = testContext.getApplicationContext();
    com.airhacks.afterburner.injection.Injector.setInstanceSupplier(applicationContext::getBean);
  }
}
