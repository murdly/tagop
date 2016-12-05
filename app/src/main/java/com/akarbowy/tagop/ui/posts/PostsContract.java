package com.akarbowy.tagop.ui.posts;


import com.akarbowy.tagop.data.database.model.PostModel;

import java.util.List;

public interface PostsContract {

    interface View {

        void setPageLoader(boolean insert);

        void setRefreshing(boolean refreshing);

        void setActionIndicator(boolean visible);

        void showError(boolean firstPage);

        void setItems(List<PostModel> data, boolean clearAndTop);

        boolean isAtTop();

        boolean isActive();

    }

    interface Presenter {

        void loadPosts(boolean entry);

        void loadNextPosts();

        void popFreshPosts();

    }
}
