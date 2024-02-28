package com.sophia.sophiasstudytool.repo;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.sophia.sophiasstudytool.model.Question;
import com.sophia.sophiasstudytool.model.Subject;

@Database(entities = {Question.class, Subject.class}, version = 1)
public abstract class StudyDatabase extends RoomDatabase {

    public abstract QuestionDao questionDao();
    public abstract SubjectDao subjectDao();
}
