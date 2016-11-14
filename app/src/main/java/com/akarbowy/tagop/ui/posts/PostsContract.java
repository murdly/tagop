package com.akarbowy.tagop.ui.posts;


import com.akarbowy.tagop.data.database.model.PostModel;
import com.akarbowy.tagop.data.network.model.Post;

import java.util.List;

public interface PostsContract {

    interface View {

        void setPageLoader(boolean insert);

        void setRefreshing(boolean refreshing);

        void setState(int state);

        void setItems(List<PostModel> data, boolean clear);

        boolean isActive();

    }

    interface Presenter {

        void loadPosts();

        void loadNextPosts();

    }
}
