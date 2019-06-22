/**
 * Copyright 2014-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.webank.webasebee.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.webank.webasebee.api.manager.EventManager;
import com.webank.webasebee.api.vo.UnitParaQueryPageReq;
import com.webank.webasebee.api.vo.UnitQueryPageReq;
import com.webank.webasebee.api.vo.UnitTimeRangeQueryPageReq;
import com.webank.webasebee.entity.CommonResponse;
import com.webank.webasebee.tools.ResponseUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * EventController
 *
 * @Description: EventController
 * @author maojiayu
 * @data Dec 24, 2018 11:06:25 AM
 *
 */
@RestController
@RequestMapping("/api/event")
@Api(value = "EventController", tags = "Event Infomation Query")
public class EventController {
    @Autowired
    private EventManager eventManager;

    @PostMapping("paras/get")
    @ApiOperation(value = "get by event and paras", httpMethod = "POST")
    public CommonResponse getByEventParas(@RequestBody @Valid UnitParaQueryPageReq<String> req, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseUtils.validateError(result);
        }
        return eventManager.getPageListByReq(req);
    }

    @PostMapping("name/get")
    @ApiOperation(value = "get by event name", httpMethod = "POST")
    public CommonResponse getByEventName(@RequestBody @Valid UnitQueryPageReq<String> req, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseUtils.validateError(result);
        }
        return eventManager.find(req);
    }

    @ResponseBody
    @RequestMapping("/time/get")
    @ApiOperation(value = "Base on time range", httpMethod = "POST")
    public CommonResponse getListByTimeRange(@RequestBody @Valid UnitTimeRangeQueryPageReq req, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseUtils.validateError(result);
        }
        return eventManager.getPageListByReq(req);
    }

}
