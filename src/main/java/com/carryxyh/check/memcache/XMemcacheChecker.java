package com.carryxyh.check.memcache;

import com.carryxyh.CheckResult;
import com.carryxyh.CheckStrategy;
import com.carryxyh.TempData;
import com.carryxyh.TempDataDB;
import com.carryxyh.check.AbstractChecker;
import com.carryxyh.client.memcache.xmemcache.XMemcacheClient;
import com.carryxyh.client.memcache.xmemcache.XMemcachedResult;
import com.carryxyh.common.Command;
import com.carryxyh.common.DefaultCommand;
import com.carryxyh.config.Config;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * XMemcacheChecker
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-08
 */
public final class XMemcacheChecker extends AbstractChecker {

    protected XMemcacheClient source;

    protected XMemcacheClient target;

    private CheckStrategy checkStrategy;

    public XMemcacheChecker(TempDataDB tempDataDB,
                            CheckStrategy checkStrategy,
                            XMemcacheClient source,
                            XMemcacheClient target) {

        super(tempDataDB);
        this.source = source;
        this.target = target;
        this.checkStrategy = checkStrategy;
    }

    @Override
    protected List<TempData> firstCheck(List<String> keys) {
        List<TempData> conflictData = Lists.newArrayList();
        for (String key : keys) {
            Command getCmd = new DefaultCommand(key, null);
            XMemcachedResult sourceValue = source.get(getCmd);
            XMemcachedResult targetValue = target.get(getCmd);

            CheckResult check = checkStrategy.check(key, sourceValue, targetValue);
            if (check.isConflict()) {
                TempData t = new TempData();
            }
        }
        return conflictData;
    }

    @Override
    protected List<TempData> roundCheck(List<TempData> tempData) {
        List<TempData> conflictData = Lists.newArrayList();
        for (TempData conflict : tempData) {
            String key = conflict.getKey();
            Command getCmd = new DefaultCommand(key, null);
            XMemcachedResult sourceValue = source.get(getCmd);
            XMemcachedResult targetValue = target.get(getCmd);
            CheckResult check = checkStrategy.check(key, sourceValue, targetValue);
            if (check.isConflict()) {
                conflictData.add(conflict);
            }
        }
        return conflictData;
    }

    @Override
    protected void doInit(Config config) throws Exception {
        super.doInit(config);
    }

    @Override
    protected void doStart() throws Exception {
        super.doStart();
        this.source.start();
        this.target.start();
    }

    @Override
    protected void doStop() throws Exception {
        super.doStop();
        this.source.stop();
        this.target.stop();
    }

    @Override
    protected void doClose() throws Exception {
        super.doClose();
        this.source.close();
        this.target.close();
    }

    @Override
    public XMemcacheClient source() {
        return source;
    }

    @Override
    public XMemcacheClient target() {
        return target;
    }
}