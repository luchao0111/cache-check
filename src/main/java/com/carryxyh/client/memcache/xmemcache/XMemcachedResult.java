package com.carryxyh.client.memcache.xmemcache;

import com.carryxyh.common.Result;

/**
 * XMemcachedResult
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-07
 */
public final class XMemcachedResult implements Result<byte[]> {

    private static final long serialVersionUID = -2989553210151211673L;

    private final byte[] result;

    public XMemcachedResult(byte[] result) {
        this.result = result;
    }

    @Override
    public byte[] result() {
        return result;
    }

    public static XMemcachedResult valueOf(byte[] result) {
        return new XMemcachedResult(result);
    }
}
