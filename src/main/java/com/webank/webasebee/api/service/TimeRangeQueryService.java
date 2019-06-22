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
package com.webank.webasebee.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import com.webank.webasebee.api.vo.CommonPageRes;
import com.webank.webasebee.api.vo.TimeRangeQueryReq;
import com.webank.webasebee.entity.CommonResponse;
import com.webank.webasebee.sys.db.condition.CommonTimeCondition;
import com.webank.webasebee.sys.db.specification.TimeSpecification;
import com.webank.webasebee.tools.ResponseUtils;

import cn.hutool.core.date.DateException;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * TimeRangeQueryService
 *
 * @Description: TimeRangeQueryService
 * @author maojiayu
 * @data Dec 21, 2018 4:30:19 PM
 *
 */
@Service
@Slf4j
public class TimeRangeQueryService {

    /**
     * page query by a time range.
     * 
     * @param req
     * @param repository
     * @return
     */
    public <T> CommonResponse getPageListByTimeRange(TimeRangeQueryReq req, JpaSpecificationExecutor<T> repository) {
        CommonTimeCondition condition = new CommonTimeCondition();
        try {
            condition.setBeginTime(DateUtil.parse(req.getBeginTime())).setEndTime(DateUtil.parse(req.getEndTime()));
        } catch (DateException e) {
            log.error("DateUtil convert error: {}", e.getMessage());
            return ResponseUtils.paramError("invalid time format, " + e.getMessage());
        }
        int pageNo = req.getPageNo();
        int pageSize = req.getPageSize();
        PageRequest pr = (PageRequest) req.convert();
        Specification<T> spec = TimeSpecification.queryByCriteria(condition);
        Page<T> page = repository.findAll(spec, pr);
        CommonPageRes<T> ret = new CommonPageRes<>(req);
        ret.setResult(page.getContent()).setTotalCount(page.getTotalElements()).setPageNo(req.getPageNo())
                .setPageSize(req.getPageSize());
        return ResponseUtils.data(ret);
    }
}
