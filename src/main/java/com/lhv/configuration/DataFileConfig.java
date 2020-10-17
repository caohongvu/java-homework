/**
 * SQSConfig.java
 *
 * @license Use of this software requires acceptance of the Evaluation License Agreement. See
 *          LICENSE file.
 * @copyright Copyright © 2016-present Heidelberger Payment GmbH. All rights reserved.
 *
 * @author vukhang
 *
 */
package com.lhv.configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 *
 */
@Configuration
public class DataFileConfig {

  @Value("${data.folder}")
  private String dataFolder;

  public String getDataFolder() {
	  return dataFolder;
  }
}
