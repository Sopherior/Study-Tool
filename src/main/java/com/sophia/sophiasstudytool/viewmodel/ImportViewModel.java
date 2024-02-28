package com.sophia.sophiasstudytool.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.sophia.sophiasstudytool.model.Subject;
import com.sophia.sophiasstudytool.repo.StudyRepository;
import java.util.List;

public class ImportViewModel extends AndroidViewModel {

    public MutableLiveData<String> importedSubject;
    public MutableLiveData<List<Subject>> fetchedSubjectList;

    private final StudyRepository mStudyRepo;

    public ImportViewModel(Application application) {
        super(application);
        mStudyRepo = StudyRepository.getInstance(application.getApplicationContext());

        importedSubject = mStudyRepo.importedSubject;
        fetchedSubjectList = mStudyRepo.fetchedSubjectList;
    }

    public void addSubject(Subject subject) {
        mStudyRepo.addSubject(subject);
        mStudyRepo.fetchQuestions(subject);
    }

    public void fetchSubjects() {
        mStudyRepo.fetchSubjects();
    }
}
