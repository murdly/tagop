package com.akarbowy.tagop.ui.posts;

import com.akarbowy.tagop.ApplicationComponent;
import com.akarbowy.tagop.utils.ActivityScoped;

import dagger.Component;

@ActivityScoped
@Component(dependencies = ApplicationComponent.class, modules = PostsModule.class)
public interface PostsComponent {

    void inject(PostsActivity postsActivity);

}
