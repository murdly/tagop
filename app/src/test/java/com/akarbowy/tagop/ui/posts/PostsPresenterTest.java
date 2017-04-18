package com.akarbowy.tagop.ui.posts;

import com.akarbowy.tagop.data.DataManager;
import com.akarbowy.tagop.data.model.PostModel;
import com.akarbowy.tagop.data.model.TagModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PostsPresenterTest {

    public static final TagModel TAG = new TagModel("any", true);

    private static List<PostModel> POSTS;

    @Mock
    private DataManager repository;

    @Mock
    private PostsContract.View view;

    @Captor
    private ArgumentCaptor<DataManager.GetPostsCallback> getPostsCallbackCaptor;

    private PostsPresenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(view.isActive()).thenReturn(true);

        presenter = new PostsPresenter(TAG, repository, view);

        POSTS = new ArrayList<>();
        POSTS.add(new PostModel(1));
        POSTS.add(new PostModel(2));
    }

    @Test
    public void entryLoadAndAtTopWhenReady_ReplacesPostsInView() {
        presenter.loadPosts(true);

        when(view.isAtTop()).thenReturn(true);

        InOrder inOrder = inOrder(view);
        inOrder.verify(view).setRefreshing(true);

        verify(repository).loadPosts(any(TagModel.class), eq(1), getPostsCallbackCaptor.capture());
        getPostsCallbackCaptor.getValue().onDataLoaded(POSTS, true);

        inOrder.verify(view).setRefreshing(false);

        ArgumentCaptor<List> setItemsCaptor = ArgumentCaptor.forClass(List.class);
        verify(view).setItems(setItemsCaptor.capture(), eq(true));
        assertTrue(setItemsCaptor.getValue().size() == 2);
    }

    @Test
    public void entryLoadNoData_showsContentEmpty() {
        presenter.loadPosts(true);

        when(view.isAtTop()).thenReturn(true);

        InOrder inOrder = inOrder(view);
        inOrder.verify(view).setRefreshing(true);

        verify(repository).loadPosts(any(TagModel.class), eq(1), getPostsCallbackCaptor.capture());
        getPostsCallbackCaptor.getValue().onDataLoaded(new ArrayList<PostModel>(), true);

        inOrder.verify(view).setRefreshing(false);

        ArgumentCaptor<List> setItemsCaptor = ArgumentCaptor.forClass(List.class);
        verify(view).setItems(setItemsCaptor.capture(), eq(true));
        assertTrue(setItemsCaptor.getValue().size() == 0);

        verify(view).showContentEmpty();
    }

    @Test
    public void notAtTopWhenReady_showsActionIndicator() {
        presenter.loadPosts(true);

        when(view.isAtTop()).thenReturn(false);

        InOrder inOrder = inOrder(view);
        inOrder.verify(view).setRefreshing(true);

        verify(repository).loadPosts(any(TagModel.class), eq(1), getPostsCallbackCaptor.capture());
        getPostsCallbackCaptor.getValue().onDataLoaded(POSTS, true);

        inOrder.verify(view).setRefreshing(false);
        inOrder.verify(view).setActionIndicator(true);
    }

    @Test
    public void loadMorePostsIntoView() {
        presenter.loadNextPosts();

        InOrder inOrder = inOrder(view);
        inOrder.verify(view).setPageLoader(true);

        verify(repository).loadPosts(any(TagModel.class), anyInt(), getPostsCallbackCaptor.capture());
        getPostsCallbackCaptor.getValue().onDataLoaded(POSTS, true);

        inOrder.verify(view).setPageLoader(false);

        ArgumentCaptor<List> setItemsCaptor = ArgumentCaptor.forClass(List.class);
        verify(view).setItems(setItemsCaptor.capture(), eq(false));
        assertTrue(setItemsCaptor.getValue().size() == 2);
    }

    @Test
    public void unavailableRemoteData_showsError(){
        presenter.loadPosts(true);

        InOrder inOrder = inOrder(view);
        inOrder.verify(view).setRefreshing(true);

        verify(repository).loadPosts(any(TagModel.class), anyInt(), getPostsCallbackCaptor.capture());
        getPostsCallbackCaptor.getValue().onDataNotAvailable();

        inOrder.verify(view).showError(anyBoolean());
        inOrder.verify(view).setRefreshing(false);
    }

    @Test
    public void savesTagWhenForSaving(){
        presenter.loadPosts(true);

        verify(repository).saveTag(eq(TAG));
    }

    @Test
    public void clickOnActionIndicator_showsFreshData() {
        presenter.popFreshPosts();

        verify(view).setItems(anyListOf(PostModel.class), eq(true));
        verify(view).setActionIndicator(eq(false));
    }
}