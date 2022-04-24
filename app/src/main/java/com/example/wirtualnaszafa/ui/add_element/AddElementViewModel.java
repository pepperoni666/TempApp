package com.example.wirtualnaszafa.ui.add_element;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wirtualnaszafa.model.Suite;
import com.example.wirtualnaszafa.remote.RemoteService;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class AddElementViewModel extends ViewModel {

    private final RemoteService remoteService = new RemoteService();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private final MutableLiveData<Boolean> requestResult = new MutableLiveData<>(null);
    public LiveData<Boolean> getRequestResult() { return requestResult; }

    private final MutableLiveData<Boolean> loading = new MutableLiveData<>(false);
    public LiveData<Boolean> getLoading() { return loading; }

    public void sendNewSuite(String token, String name, String description, Uri imageUri) {
        Disposable request = remoteService.newSuite(token, name, description, imageUri)
                .doOnSubscribe(disposable -> loading.postValue(true))
                .doOnTerminate(() -> loading.postValue(false))
                .subscribe(result -> {
                    if(result != null) {
                        requestResult.postValue(true);
                    }
                    else {
                        requestResult.postValue(false);
                    }
                }, throwable -> {
                    requestResult.postValue(false);
                });

        compositeDisposable.add(request);
    }

    @Override
    public void onCleared() {
        compositeDisposable.clear();
    }

}