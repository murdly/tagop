package com.akarbowy.tagop.ui.posts;

import com.akarbowy.tagop.data.database.model.TagModel;

import dagger.Module;
import dagger.Provides;

@Module
public class PostsModule {

    private PostsContract.View view;

    private TagModel postsForTag;

    public PostsModule(PostsContract.View view, TagModel postsForTag) {
        this.view = view;
        this.postsForTag = postsForTag;
    }

    @Provides PostsContract.View providePostsContractView(){
        return view;
    }

    @Provides TagModel providePostsTag(){
        return postsForTag;
    }

}
