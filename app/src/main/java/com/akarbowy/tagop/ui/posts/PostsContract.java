package com.akarbowy.tagop.ui.posts;


import com.akarbowy.tagop.data.network.model.TagEntry;

import java.util.List;

public interface PostsContract {

    interface View {
        void setPresenter(Presenter presenter);

        void setPageLoader(boolean insert);

        void setRefreshing(boolean refreshing);

        void setState(int state);

        void setItems(List<TagEntry> data, boolean clear);

        boolean isActive();

    }

    interface Presenter {

        void loadPosts(boolean forceUpdate);

        void loadNextPosts();

    }
}
