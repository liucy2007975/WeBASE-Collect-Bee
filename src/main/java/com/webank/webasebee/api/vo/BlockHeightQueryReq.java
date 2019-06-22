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
package com.webank.webasebee.api.vo;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Range;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * BlockHeightQueryReq  
 *
 * @Description: BlockHeightQueryReq
 * @author maojiayu
 * @data Dec 21, 2018 11:06:21 AM
 *
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper=true)
public class BlockHeightQueryReq extends CommonPageReq {

    @NotBlank
    @Range(min = 0, max = Long.MAX_VALUE)
    private String blockHeight;

    public CommonParaQueryPageReq toCommonParaQueryPageReq() {
        CommonParaQueryPageReq req = new CommonParaQueryPageReq();
        req.setReqParaName("blockHeight").setReqParaValue(this.blockHeight).setOrder(this.getOrder())
                .setOrderBy(this.getOrderBy()).setPageNo(this.getPageNo()).setPageSize(this.getPageSize());
        return req;
    }

}
