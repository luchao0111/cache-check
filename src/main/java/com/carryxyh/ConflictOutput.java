package com.carryxyh;

import com.carryxyh.lifecycle.Lifecycle;
import com.carryxyh.tempdata.TempData;

import java.util.List;

/**
 * ConflictOutput
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-08
 */
public interface ConflictOutput extends Lifecycle {

    void output(List<TempData> conflicts);
}
