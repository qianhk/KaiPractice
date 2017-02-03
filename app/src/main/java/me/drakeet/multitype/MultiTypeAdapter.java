/*
 * Copyright 2016 drakeet. https://github.com/drakeet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.drakeet.multitype;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author drakeet
 */
public class MultiTypeAdapter extends RecyclerView.Adapter<BaseViewHolder> implements FlatTypeAdapter, TypePool {

    private ArrayList<Object> mDataList;
    @NonNull
    private TypePool mDelegate;
    @Nullable
    private LayoutInflater mInflater;
    @Nullable
    private FlatTypeAdapter mProvidedFlatTypeAdapter;

    private OnMultiTypeViewListener mMultiTypeViewListener;

    public MultiTypeAdapter() {
        this(null, new MultiTypePool(), null);
    }

    public MultiTypeAdapter(List<?> items) {
        this(items, new MultiTypePool(), null);
    }


    public MultiTypeAdapter(List<?> items, int initialCapacity) {
        this(items, new MultiTypePool(initialCapacity), null);
    }


    public MultiTypeAdapter(List<?> items, TypePool pool) {
        this(items, pool, null);
    }


    public MultiTypeAdapter(List<?> items, @NonNull TypePool delegate, @Nullable FlatTypeAdapter providedFlatTypeAdapter) {
        this.mDelegate = delegate;
        this.mProvidedFlatTypeAdapter = providedFlatTypeAdapter;
        if (items != null) {
            flushData(items);
        }
    }

    public void clearData() {
        if (mDataList != null) {
            mDataList.clear();
            notifyDataSetChanged();
        }
    }

    public void flushData(List<?> data) {
        clearData();
        if (data != null) {
            if (mDataList == null) {
                mDataList = new ArrayList<>(data);
            } else {
                mDataList.addAll(data);
            }
        }
        notifyDataSetChanged();
    }

    public void appendData(List<?> data) {
        if (mDataList == null) {
            flushData(data);
        } else {
            mDataList.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void removeData(Object data) {
        if (mDataList != null) {
            mDataList.remove(data);
            notifyDataSetChanged();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public int getItemViewType(int position) {
        Object item = mDataList.get(position);
        return indexOf(flattenClass(item));
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int indexViewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }
        ItemViewProvider provider = getProviderByIndex(indexViewType);
        provider.adapter = MultiTypeAdapter.this;
        BaseViewHolder viewHolder = provider.onCreateViewHolder(mInflater, parent);
        viewHolder.setMultiTypeViewListener(mMultiTypeViewListener);
        return viewHolder;
    }


    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        Object item = mDataList.get(position);
        ItemViewProvider provider = getProviderByClass(flattenClass(item));
        provider.onBindViewHolder(holder, flattenItem(item));
    }


    @Override
    public int getItemCount() {
        return mDataList != null ? mDataList.size() : 0;
    }


    public boolean isEmpty() {
        return mDataList == null || mDataList.isEmpty();
    }

    @Override
    public void register(@NonNull Class<?> clazz, @NonNull ItemViewProvider provider) {
        mDelegate.register(clazz, provider);
    }


    public void registerAll(@NonNull final MultiTypePool pool) {
        for (int i = 0; i < pool.getContents().size(); i++) {
            mDelegate.register(pool.getContents().get(i), pool.getProviders().get(i));
        }
    }


    public void applyGlobalMultiTypePool() {
        for (int i = 0; i < GlobalMultiTypePool.getContents().size(); i++) {
            final Class<?> clazz = GlobalMultiTypePool.getContents().get(i);
            final ItemViewProvider provider = GlobalMultiTypePool.getProviders().get(i);
            if (!this.getContents().contains(clazz)) {
                this.register(clazz, provider);
            }
        }
    }


    @Override
    public int indexOf(@NonNull Class<?> clazz)
            throws ProviderNotFoundException {
        int index = mDelegate.indexOf(clazz);
        if (index >= 0) {
            return index;
        }
        throw new ProviderNotFoundException(clazz);
    }


    /**
     * Set the FlatTypeAdapter to instead of the default inner FlatTypeAdapter of
     * MultiTypeAdapter.
     * <p>Note: You could use {@link FlatTypeClassAdapter} and {@link FlatTypeItemAdapter}
     * to create a special FlatTypeAdapter conveniently.</p>
     *
     * @param flatTypeAdapter the FlatTypeAdapter
     * @since v2.3.2
     */
    public void setFlatTypeAdapter(@NonNull FlatTypeAdapter flatTypeAdapter) {
        this.mProvidedFlatTypeAdapter = flatTypeAdapter;
    }


    @NonNull
    @SuppressWarnings("deprecation")
    Class flattenClass(@NonNull final Object item) {
        if (mProvidedFlatTypeAdapter != null) {
            return mProvidedFlatTypeAdapter.onFlattenClass(item);
        }
        return onFlattenClass(item);
    }


    @NonNull
    @SuppressWarnings("deprecation")
    Object flattenItem(@NonNull final Object item) {
        if (mProvidedFlatTypeAdapter != null) {
            return mProvidedFlatTypeAdapter.onFlattenItem(item);
        }
        return onFlattenItem(item);
    }


    /**
     * @deprecated Use {@link MultiTypeAdapter#setFlatTypeAdapter(FlatTypeAdapter)} instead.
     * The method may be removed next time.
     */
    @NonNull
    @Override
    public Class onFlattenClass(@NonNull final Object item) {
        return item.getClass();
    }


    /**
     * @deprecated Use {@link MultiTypeAdapter#setFlatTypeAdapter(FlatTypeAdapter)} instead.
     * The method may be removed next time.
     */
    @NonNull
    @Override
    public Object onFlattenItem(@NonNull final Object item) {
        return item;
    }


    @NonNull
    @Override
    public ArrayList<Class<?>> getContents() {
        return mDelegate.getContents();
    }


    @NonNull
    @Override
    public ArrayList<ItemViewProvider> getProviders() {
        return mDelegate.getProviders();
    }


    @NonNull
    @Override
    public ItemViewProvider getProviderByIndex(int index) {
        return mDelegate.getProviderByIndex(index);
    }


    @NonNull
    @Override
    public <T extends ItemViewProvider> T getProviderByClass(@NonNull Class<?> clazz) {
        return mDelegate.getProviderByClass(clazz);
    }

    public OnMultiTypeViewListener getMultiTypeViewListener() {
        return mMultiTypeViewListener;
    }

    public void setMultiTypeViewListener(OnMultiTypeViewListener multiTypeViewListener) {
        mMultiTypeViewListener = multiTypeViewListener;
    }

}
