package com.example.allourtrees.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProfileViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ProfileViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("The profile page hasn't been implemented yet...\nWhen you no longer wish to test anymore, feel free to just delete the app\n\nAll data will be deleted no later than January 31st 2024");
    }

    public LiveData<String> getText() {
        return mText;
    }

}
