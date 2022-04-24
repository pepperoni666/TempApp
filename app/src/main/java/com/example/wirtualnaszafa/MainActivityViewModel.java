package com.example.wirtualnaszafa;

import static java.util.stream.Collectors.toList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wirtualnaszafa.model.Suite;
import com.example.wirtualnaszafa.remote.RemoteService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class MainActivityViewModel extends ViewModel {
    private final RemoteService remoteService = new RemoteService();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public WardrobeDAO db;

    private final MutableLiveData<Boolean> loading = new MutableLiveData<>(false);
    public LiveData<Boolean> getLoading() { return loading; }

    public void fetchAll(String token) {
        if(token == null) return;
        Disposable request = remoteService.getSuites(token)
                .doOnSubscribe(disposable -> loading.postValue(true))
                .doOnTerminate(() -> loading.postValue(false))
                .subscribe(result -> {
                    if(result != null && !result.isEmpty() && db != null) {
                        List<WardrobeDB> list = new ArrayList<>();
                        for(int i = 0; i < result.size(); i++) {
                            Suite suite = result.get(i);
                            WardrobeDB wardrobeDB = new WardrobeDB();
                            wardrobeDB.setId(suite.id);
                            wardrobeDB.setPath(suite.imagePath);
                            wardrobeDB.setTag(suite.name);
                            wardrobeDB.setColor(suite.description);
                            list.add(wardrobeDB);
                        }
                        db.insertAll(list);
                    }
                }, throwable -> {
                    //TODO
                });

        compositeDisposable.add(request);
    }

    @Override
    public void onCleared() {
        compositeDisposable.clear();
    }
}
