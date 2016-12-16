package com.akarbowy.tagop.ui.search;

import com.akarbowy.tagop.data.DataManager;
import com.akarbowy.tagop.data.model.TagModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

public class SearchPresenterTest {

    private static List<TagModel> TAGS;

    @Mock
    private SearchContract.View view;

    @Mock
    private DataManager repository;

    @Captor
    private ArgumentCaptor<DataManager.GetHistoryCallback> historyArgumentCaptorCallback;

    private SearchPresenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        presenter = new SearchPresenter(view, repository);

        TAGS = new ArrayList<>();
        TAGS.add(new TagModel("1", "a"));
        TAGS.add(new TagModel("2", "b"));
    }

    @Test
    public void loadHistoryTagsFromRepositoryIntoView() {
        presenter.start();

        verify(repository).getTags(historyArgumentCaptorCallback.capture());
        historyArgumentCaptorCallback.getValue().onDataLoaded(TAGS);

        ArgumentCaptor<List> setItemsCaptor = ArgumentCaptor.forClass(List.class);
        verify(view).setItems(setItemsCaptor.capture(), eq(false));
        assertTrue(setItemsCaptor.getValue().size() == 2);
    }

    @Test
    public void clearHistory_DeletesTagsAndShowsEmptyState() {
        presenter.clearHistory();

        verify(repository).deleteAllTags();
        verify(view).showEmptyState();
    }

    @Test
    public void removeTag_RemovesSingleTag() {
        TagModel tagToRemove = any(TagModel.class);

        presenter.removeFromHistory(tagToRemove);

        verify(repository).deleteTag(tagToRemove);

        verify(view).setItems(anyList(), eq(false));
    }

    @Test
    public void unavailableTags_showsEmptyState(){
        presenter.start();

        verify(repository).getTags(historyArgumentCaptorCallback.capture());
        historyArgumentCaptorCallback.getValue().onDataNotAvailable();

        verify(view).showEmptyState();
    }

}