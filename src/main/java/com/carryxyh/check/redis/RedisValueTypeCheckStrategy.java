package com.carryxyh.check.redis;

import com.carryxyh.CheckResult;
import com.carryxyh.DefaultCheckResult;
import com.carryxyh.client.redis.RedisCacheClient;
import com.carryxyh.constants.ConflictType;
import com.carryxyh.constants.ValueType;

/**
 * RedisValueTypeCheckStrategy
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-12
 */
class RedisValueTypeCheckStrategy extends RedisKeyExistCheckStrategy {

    public RedisValueTypeCheckStrategy(RedisCacheClient source, RedisCacheClient target) {
        super(source, target);
    }

    @Override
    public CheckResult check(String key, String subKey) {
        CheckResult c = super.check(key, subKey);
        if (c.isConflict()) {
            return c;
        }

        String sourceType = source.type(key);
        String targetType = target.type(key);

        if (sourceType.equals(targetType)) {
            return DefaultCheckResult.nonConflict();
        } else {
            return DefaultCheckResult.
                    conflict(ConflictType.VALUE_TYPE, ValueType.NONE, sourceType, targetType);
        }
    }
}
