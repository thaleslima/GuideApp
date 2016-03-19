package com.guideapp.guideapp.model.wrapper;

import java.util.List;

/**
 * Created by thales on 3/16/16.
 */
public class ListResponse<T> {
    private List<T> items;

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
