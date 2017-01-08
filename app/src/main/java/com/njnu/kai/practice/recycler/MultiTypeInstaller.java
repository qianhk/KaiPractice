package com.njnu.kai.practice.recycler;

import com.njnu.kai.practice.recycler.modal.Category;
import com.njnu.kai.practice.recycler.provider.CategoryItemViewProvider;

import me.drakeet.multitype.GlobalMultiTypePool;

public final class MultiTypeInstaller {

    public static void start() {
        GlobalMultiTypePool.register(Category.class, new CategoryItemViewProvider());
    }
}
