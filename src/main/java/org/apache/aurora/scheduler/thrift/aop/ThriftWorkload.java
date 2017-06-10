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
package org.apache.aurora.scheduler.thrift.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.Function;

import org.apache.aurora.gen.Result;

/**
 * Marks a scheduler thrift server method as a target for workload measuring.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ThriftWorkload {

  /**
   * Translates Thrift server call {@link Result} to an {@literal Integer} that represents the
   * amount of workload generated by the call.
   */
  interface ThriftWorkloadCounter extends Function<Result, Integer> { }

  /**
   * Specify the class that will do the thrift load mapping.
   */
  Class<? extends ThriftWorkloadCounter> value() default ThriftWorkloadCounterImpl.class;

  class ThriftWorkloadCounterImpl implements ThriftWorkloadCounter {
    @Override
    public Integer apply(Result result) {
      int count = 0;
      if (result.isSetScheduleStatusResult()) {
        count = result.getScheduleStatusResult().getTasksSize();
      } else if (result.isSetGetPendingReasonResult()) {
        count = result.getGetPendingReasonResult().getReasonsSize();
      } else if (result.isSetConfigSummaryResult()) {
        count = result.getConfigSummaryResult().getSummary().getGroupsSize();
      } else if (result.isSetRoleSummaryResult()) {
        count = result.getRoleSummaryResult().getSummariesSize();
      } else if (result.isSetJobSummaryResult()) {
        count = result.getJobSummaryResult().getSummariesSize();
      } else if (result.isSetGetJobsResult()) {
        count = result.getGetJobsResult().getConfigsSize();
      } else if (result.isSetGetJobUpdateSummariesResult()) {
        count = result.getGetJobUpdateSummariesResult().getUpdateSummariesSize();
      } else if (result.isSetGetJobUpdateDetailsResult()) {
        count = result.getGetJobUpdateDetailsResult().getDetailsListSize();
      }
      return count;
    }
  }

}