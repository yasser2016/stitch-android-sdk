/*
 * Copyright 2018-present MongoDB, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mongodb.stitch.core.services.mongodb.remote;

/**
 * The options for a count operation.
 */
public class RemoteCountOptions {
  private int limit;

  /**
   * Gets the limit to apply.  The default is 0, which means there is no limit.
   *
   * @return the limit
   */
  public int getLimit() {
    return limit;
  }

  /**
   * Sets the limit to apply.
   *
   * @param limit the limit
   * @return this
   */
  public RemoteCountOptions limit(final int limit) {
    this.limit = limit;
    return this;
  }

  @Override
  public String toString() {
    return "RemoteCountOptions{"
        + "limit=" + limit
        + '}';
  }
}
