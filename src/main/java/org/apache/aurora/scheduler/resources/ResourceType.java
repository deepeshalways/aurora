/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.aurora.scheduler.resources;

import com.google.common.annotations.VisibleForTesting;

import static java.util.Objects.requireNonNull;

/**
 * Describes Mesos resource types.
 */
@VisibleForTesting
public enum ResourceType {
  /**
   * CPU resource.
   */
  CPUS("cpus"),

  /**
   * RAM resource.
   */
  RAM_MB("mem"),

  /**
   * DISK resource.
   */
  DISK_MB("disk"),

  /**
   * Port resource.
   */
  PORTS("ports");

  private final String resourceName;

  ResourceType(String resourceName) {
    this.resourceName = requireNonNull(resourceName);
  }

  public String getMesosName() {
    return resourceName;
  }
}
