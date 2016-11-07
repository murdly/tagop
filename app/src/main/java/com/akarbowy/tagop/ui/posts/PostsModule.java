package com.akarbowy.tagop.ui.posts;

import dagger.Module;
import dagger.Provides;

@Module
public class PostsModule {

    private PostsContract.View view;

    private String postsTag;

    public PostsModule(PostsContract.View view, String postsTag) {
        this.view = view;
        this.postsTag = postsTag;
    }

    @Provides PostsContract.View providePostsContractView(){
        return view;
    }

    @Provides String providePostsTag(){
        return postsTag;
    }

//    @Provides PostsContract.Presenter providePresenter(PostsRepository repository){
//        return new PostsPresenter(postsTag, repository, view);
//    }
}
