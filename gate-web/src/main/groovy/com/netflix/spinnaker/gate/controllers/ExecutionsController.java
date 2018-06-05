/*
 * Copyright 2017 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.netflix.spinnaker.gate.controllers;

import com.netflix.spinnaker.gate.security.RequestContext;
import com.netflix.spinnaker.gate.services.internal.OrcaServiceSelector;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/executions")
@RestController
public class ExecutionsController {

  private OrcaServiceSelector orcaServiceSelector;

  @Autowired
  public ExecutionsController(OrcaServiceSelector orcaServiceSelector) {
    this.orcaServiceSelector = orcaServiceSelector;
  }

  @ApiOperation(value = "Retrieve a list of the most recent pipeline executions for the provided `pipelineConfigIds` that match the provided `statuses` query parameter")
  @RequestMapping(method = RequestMethod.GET)
  List getLatestExecutionsByConfigIds(@RequestParam(value = "pipelineConfigIds") String pipelineConfigIds,
                                      @RequestParam(value = "limit", required = false) Integer limit,
                                      @RequestParam(value = "statuses", required = false) String statuses) {
    return orcaServiceSelector.withContext(RequestContext.get()).getLatestExecutionsByConfigIds(pipelineConfigIds, limit, statuses);
  }

  // TODO(joonlim)
  @ApiOperation(value = "Retrieve a list of the most recent pipeline executions for the provided `pipelineConfigIds` that match the provided `statuses` query parameter")
  @RequestMapping(value = "/search", method = RequestMethod.GET)
  List searchForPipelineExecutions(
    @RequestParam(value = "application", required = false) String application,
    @RequestParam(value = "statuses", required = false) String statuses,
    @RequestParam(value = "buildTimeStartBoundary", defaultValue = "0") long buildTimeStartBoundary,
    @RequestParam(value = "buildTimeEndBoundary", defaultValue = "9223372036854775807" /* Long.MAX_VALUE */) long buildTimeEndBoundary,
    @RequestParam(value = "page", defaultValue =  "0") int page,
    @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
    @RequestParam(value = "reverse", defaultValue = "false") boolean reverse,
    @RequestParam(value = "expand", defaultValue = "false") boolean expand,
    @RequestParam Map<String, String> params
  ) {
    return orcaServiceSelector.withContext(RequestContext.get()).searchForPipelineExecutions(application, statuses, buildTimeStartBoundary, buildTimeEndBoundary, page, pageSize, reverse, expand, params);
  }
}
