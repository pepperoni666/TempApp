package com.example.wirtualnaszafa.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wirtualnaszafa.model.Suite;
import com.example.wirtualnaszafa.remote.RemoteService;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class GalleryViewModel extends ViewModel {

    private final RemoteService remoteService = new RemoteService();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private final MutableLiveData<List<Suite>> suites = new MutableLiveData<>(null);
    public LiveData<List<Suite>> getSuites() { return suites; }

    private final MutableLiveData<Boolean> loading = new MutableLiveData<>(false);
    public LiveData<Boolean> getLoading() { return loading; }

    public void getAll(String token) {
        Disposable request = remoteService.getSuites(token)
                .doOnSubscribe(disposable -> loading.postValue(true))
                .doOnTerminate(() -> loading.postValue(false))
                .subscribe(result -> {
                    if(result != null && !result.isEmpty()) {
                        suites.postValue(result);
                    }
                }, throwable -> {
                    suites.postValue(null);
                });

        compositeDisposable.add(request);
    }

    @Override
    public void onCleared() {
        compositeDisposable.clear();
    }

}