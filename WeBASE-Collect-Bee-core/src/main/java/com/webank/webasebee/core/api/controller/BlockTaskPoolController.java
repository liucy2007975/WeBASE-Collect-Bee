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
package com.webank.webasebee.core.api.controller;

import java.io.IOException;

import org.fisco.bcos.web3j.protocol.Web3j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.webank.webasebee.common.enums.TxInfoStatusEnum;
import com.webank.webasebee.db.repository.BlockTaskPoolRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * BlockTaskInfoController
 *
 * @Description: BlockTaskInfoController
 * @author maojiayu
 * @data May 21, 2019 7:42:45 PM
 *
 */
@RestController
@RequestMapping("/api/blockTaskPool")
@Api(value = "BlockTaskPoolController", tags = "Block Task Pool Query")
@Slf4j
public class BlockTaskPoolController {
    @Autowired
    private Web3j web3j;
    @Autowired
    private BlockTaskPoolRepository blockTaskPoolRepository;

    @ResponseBody
    @RequestMapping("/blocks/get")
    @ApiOperation(value = "Get finished block count", httpMethod = "GET")
    public long getFinishedBlockCount() {
        return blockTaskPoolRepository.countBySyncStatus(TxInfoStatusEnum.DONE.getStatus());
    }

    @ResponseBody
    @RequestMapping("/blockHeight/get")
    @ApiOperation(value = "Get block height", httpMethod = "GET")
    public long getBlockHeight() {
        try {
            return web3j.getBlockNumber().send().getBlockNumber().longValue();
        } catch (IOException e) {
            log.error("get block height error: {}", e.getMessage());
            return -1;
        }
    }

}
