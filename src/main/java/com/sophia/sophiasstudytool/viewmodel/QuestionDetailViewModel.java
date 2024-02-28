package com.sophia.sophiasstudytool.viewmodel;
import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import com.sophia.sophiasstudytool.model.Question;
import com.sophia.sophiasstudytool.repo.StudyRepository;

public class QuestionDetailViewModel extends AndroidViewModel {

    private StudyRepository mStudyRepo;
    private final MutableLiveData<Long> questionIdLiveData = new MutableLiveData<>();

    public LiveData<Question> questionLiveData =
            Transformations.switchMap(questionIdLiveData, questionId ->
                    mStudyRepo.getQuestion(questionId));

    public QuestionDetailViewModel(@NonNull Application application) {
        super(application);
        mStudyRepo = StudyRepository.getInstance(application.getApplicationContext());
    }

    public void loadQuestion(long questionId) {
        questionIdLiveData.setValue(questionId);
    }

    public void addQuestion(Question question) {
        mStudyRepo.addQuestion(question);
    }

    public void updateQuestion(Question question) {
        mStudyRepo.updateQuestion(question);
    }
}
