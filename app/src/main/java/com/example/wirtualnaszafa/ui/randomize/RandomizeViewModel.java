package com.example.wirtualnaszafa.ui.randomize;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wirtualnaszafa.WardrobeDAO;
import com.example.wirtualnaszafa.WardrobeDB;
import com.example.wirtualnaszafa.model.Suite;
import com.example.wirtualnaszafa.remote.RemoteService;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class RandomizeViewModel extends ViewModel {

    public LiveData<List<WardrobeDB>> suitesLiveData;

    public LiveData<List<WardrobeDB>> setDb(WardrobeDAO db) {
        suitesLiveData = db.getAllLive();
        return suitesLiveData;
    }
}