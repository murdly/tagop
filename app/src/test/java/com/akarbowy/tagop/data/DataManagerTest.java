package com.akarbowy.tagop.data;

import com.akarbowy.tagop.data.model.PostModel;
import com.akarbowy.tagop.data.model.TagModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class DataManagerTest {

    private static List<PostModel> POSTS;

    @Mock
    private DataSource localDataSource;

    @Mock
    private DataSource remoteDataSource;

    @Mock
    private DataSource.GetHistoryCallback getHistoryCallback;

    @Mock
    private DataSource.GetPostsCallback getPostsCallback;

    @Captor
    private ArgumentCaptor<DataSource.GetHistoryCallback> historyCallbackArgumentCaptor;

    @Captor
    private ArgumentCaptor<DataSource.GetPostsCallback> postsCallbackArgumentCaptor;

    private DataManager repository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        repository = new DataManager(localDataSource, remoteDataSource);

        POSTS = new ArrayList<>();
        POSTS.add(new PostModel(1));
        POSTS.add(new PostModel(2));
    }

    @Test
    public void loadPosts_cachesAfterFirstApiCall() throws Exception {
        repository.allowCache(true);

        TagModel tag = new TagModel("0", "TAG");

        //first call
        repository.loadPosts(tag, 1, getPostsCallback);

        verify(localDataSource).loadPosts(any(TagModel.class), eq(1), postsCallbackArgumentCaptor.capture());
        postsCallbackArgumentCaptor.getValue().onDataNotAvailable();

        //second call
        repository.loadPosts(tag, 1, getPostsCallback);

        verify(localDataSource, times(2)).loadPosts(any(TagModel.class), eq(1), postsCallbackArgumentCaptor.capture());
        postsCallbackArgumentCaptor.getValue().onDataLoaded(POSTS, true);

    }


    @Test
    public void getTags_returnsTagsFromLocalDataSource() throws Exception {
        repository.getTags(getHistoryCallback);

        verify(localDataSource).getTags(any(DataSource.GetHistoryCallback.class));
    }

    @Test
    public void saveTag_savesTagInLocalDataSource() throws Exception {
        TagModel tag = new TagModel("a", true);
        repository.saveTag(tag);

        verify(localDataSource).saveTag(tag);
    }

}