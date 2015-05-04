package nl.jpoint.quizapp;

import com.mongodb.*;

import java.util.Collections;

public class DBInitializer {

  public static void create(MongoClient mongoClient) {
    final DBCollection quizes = createQuizesCollection(mongoClient);
    populateQuizes(quizes);
  }

  private static void populateQuizes(final DBCollection quizes) {
    final BasicDBList questions = new BasicDBList();
    questions.add(new BasicDBObject()
            .append("question", "How old is Java in 2015?")
            .append("correctAnswer", "20")
            .append("otherAnswers", createStringDBList("19", "21"))
    );
    questions.add(new BasicDBObject()
            .append("question", "How old is Java in 2016?")
            .append("correctAnswer", "21")
            .append("otherAnswers", createStringDBList("20", "22"))
    );
    questions.add(new BasicDBObject()
            .append("question", "How old is Java in 2017?")
            .append("correctAnswer", "22")
            .append("otherAnswers", createStringDBList("23", "21"))
    );
    quizes.insert(new BasicDBObject()
            .append("name", "Devoxx2015Quiz")
            .append("questions", questions)
    );
  }

  private static DBCollection createQuizesCollection(MongoClient mongoClient) {
    DB quizmaster = mongoClient.getDB("quizmaster");
    if (quizmaster.collectionExists("quizes")) {
      quizmaster.dropDatabase();
      quizmaster = mongoClient.getDB("quizmaster");
    }
    return quizmaster.createCollection("quizes", null);
  }

  private static Object createStringDBList(String... strings) {
    final BasicDBList list = new BasicDBList();
    Collections.addAll(list, strings);
    return list;
  }
}
