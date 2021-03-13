/*
 * Copyright (c) 2021 Institut de recherches cliniques de Montreal (IRCM)
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

package ca.qc.ircm.compositionmap;

import ca.qc.ircm.compositionmap.gui.MainGui;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

/**
 * Java FX application using Spring Boot.
 */
public class FxApplication extends Application {
  protected ConfigurableApplicationContext applicationContext;

  @Override
  public void init() throws Exception {
    ApplicationContextInitializer<GenericApplicationContext> initializer =
        new ApplicationContextInitializer<GenericApplicationContext>() {
          @Override
          public void initialize(GenericApplicationContext genericApplicationContext) {
            genericApplicationContext.registerBean(Application.class, () -> FxApplication.this);
            genericApplicationContext.registerBean(Parameters.class, () -> getParameters());
            genericApplicationContext.registerBean(HostServices.class, () -> getHostServices());
          }
        };

    this.applicationContext = new SpringApplicationBuilder().sources(Main.class)
        .initializers(initializer).build().run(getParameters().getRaw().toArray(new String[0]));
    com.airhacks.afterburner.injection.Injector.setInstanceSupplier(applicationContext::getBean);
  }

  @Override
  public void start(Stage stage) throws Exception {
    MainGui app = new MainGui();
    notifyPreloader(new ApplicationStarted());
    app.show();
  }

  @Override
  public void stop() throws Exception {
    super.stop();
    applicationContext.close();
  }
}
