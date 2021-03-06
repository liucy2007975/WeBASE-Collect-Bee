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
package com.webank.webasebee.core.service;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.fisco.bcos.web3j.abi.FunctionReturnDecoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.Utils;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint64;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.BcosBlock.Block;
import org.fisco.bcos.web3j.protocol.core.methods.response.BcosBlock.TransactionResult;
import org.fisco.bcos.web3j.protocol.core.methods.response.BcosTransactionReceipt;
import org.fisco.bcos.web3j.protocol.core.methods.response.Transaction;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.webank.webasebee.common.tools.JacksonUtils;
import com.webank.webasebee.core.BaseTest;
import com.webank.webasebee.extractor.ods.EthClient;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * BlockInfoServiceTest
 *
 * @Description: BlockInfoServiceTest
 * @author graysonzhang
 * @data 2018-11-14 15:15:17
 *
 */
@Slf4j
public class BlockInfoServiceTest extends BaseTest {

    @Autowired
    private EthClient ethClient;
    @Autowired
    private Web3j web3j;

    @Test
    public void testInput() throws IOException {
        Block block = ethClient.getBlock(BigInteger.valueOf(8677));
        List<TransactionResult> transactionResults = block.getTransactions();
        log.info("transactionResults.size:{}", transactionResults.size());
        for (TransactionResult result : transactionResults) {
            BcosTransactionReceipt ethGetTransactionReceipt = web3j.getTransactionReceipt((String) result.get()).send();
            Optional<TransactionReceipt> opt = ethGetTransactionReceipt.getTransactionReceipt();
            if (opt.isPresent()) {
                log.info("TransactionReceipt hash: {}", opt.get().getTransactionHash());
                Optional<Transaction> optt =
                        web3j.getTransactionByHash(opt.get().getTransactionHash()).send().getTransaction();
                if (optt.isPresent()) {
                    log.info("transaction hash : {}", optt.get().getHash());
                    log.info("transaction info : {}", optt.get().getValue());
                    List<Type> lt = new ArrayList<>();
                    lt.add(Uint64.DEFAULT);
                    List<TypeReference<?>> references = new ArrayList<>();
                    TypeReference<Uint64> typeReference = new TypeReference<Uint64>() {
                    };
                    references.add(typeReference);
                    List<TypeReference<Type>> ll = Utils.convert(references);
                    List<Type> inputList = FunctionReturnDecoder.decode(optt.get().getInput(), ll);
                    log.info("input : {}", inputList.size());
                    log.info("input : {}", JacksonUtils.toJson(inputList));
                }

            }
        }

    }

}
