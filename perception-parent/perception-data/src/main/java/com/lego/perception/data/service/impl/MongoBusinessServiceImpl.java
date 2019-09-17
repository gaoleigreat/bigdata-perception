package com.lego.perception.data.service.impl;

import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.template.model.entity.FormTemplate;
import com.lego.framework.template.model.entity.SearchParam;
import com.lego.perception.data.service.IBusinessService;
import com.lego.perception.data.utils.QueryUtils;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @author : yanglf
 * @version : 1.0
 * @created IntelliJ IDEA.
 * @date : 2019/9/5 11:41
 * @desc :
 */
@Service(value = "mongoBusinessServiceImpl")
public class MongoBusinessServiceImpl implements IBusinessService {


    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public RespVO createBusinessTable(FormTemplate formTemplate) {
        return RespVOBuilder.success();
    }

    @Override
    public RespVO insertBusinessData(FormTemplate formTemplate,
                                     List<Map<String, Object>> data,
                                     Long fileId) {
        if (CollectionUtils.isEmpty(data)) {
            return RespVOBuilder.success();
        }
        String tableName = formTemplate.getDescription();
        mongoTemplate.insert(data, tableName);
        return RespVOBuilder.success();
    }

    @Override
    public RespVO<RespDataVO<Map>> queryBusinessData(String tableName,
                                                     List<SearchParam> params) {
        Criteria criteria = new Criteria();
        if (!CollectionUtils.isEmpty(params)) {
            for (SearchParam param : params) {
                String symbol = param.getSymbol();
                String absoluteField = param.getAbsoluteField();
                String value = param.getValue();
                Integer dataType = param.getDataType();
                if (null == symbol || null == absoluteField || null == value || null == dataType) {
                    continue;
                }
                QueryUtils.addAdvancedCondition(criteria, symbol, absoluteField, value);
            }
        }
        Query query = new Query();
        query.addCriteria(criteria);
        List<Map> mapList = mongoTemplate.find(query, Map.class, tableName);
        if (!CollectionUtils.isEmpty(mapList)) {
            for (Map map : mapList) {
                map.remove("fileId");
            }
        }
        return RespVOBuilder.success(mapList);
    }

    @Override
    public RespVO updateBusinessData(String tableName, Map<String, Object> data) {
        if (!data.containsKey("id")) {
            return RespVOBuilder.failure("修改条件缺失");
        }
        String id = (String) data.get("id");
        Map map = mongoTemplate.findById(new ObjectId(id), Map.class, tableName);
        if (map == null) {
            return RespVOBuilder.failure();
        }
        Criteria c = new Criteria();
        c.and("_id").is(id);
        Query query = new Query(c);
        Update update = Update.fromDocument(new Document(data));
        UpdateResult result = mongoTemplate.upsert(query, update, tableName);
        if (result.getModifiedCount() > 0) {
            return RespVOBuilder.success();
        }
        return RespVOBuilder.failure();
    }

    @Override
    public RespVO delBusinessData(String tableName, Map<String, Object> data) {
        if (!data.containsKey("id")) {
            return RespVOBuilder.failure("删除条件缺失");
        }
        String id = (String) data.get("id");
        Map map = mongoTemplate.findById(new ObjectId(id), Map.class, tableName);
        if (map != null) {
            Criteria c = new Criteria();
            c.and("_id").is(id);
            Object query = new Query(c);
            DeleteResult deleteResult = mongoTemplate.remove(query, tableName);
            if (deleteResult.getDeletedCount() <= 0) {
                return RespVOBuilder.failure();
            }
        }
        return RespVOBuilder.failure();
    }
}
