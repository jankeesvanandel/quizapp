package nl.jpoint.quizapp;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/wallboard")
public class WallboardResource extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    final MongoClient mongoClient = new MongoClient("localhost");
    final DB db = mongoClient.getDB("quizmaster");
    final DBCollection quizes = db.getCollection("quizes");
    final Object dbObjects = quizes.findOne(new BasicDBObject("id", Integer.parseInt(req.getParameter("id"))));
    final DBObject result = (DBObject) dbObjects;
    final String s = result.toString();
    res.setContentType("application/json");
    res.getWriter().write(s);

  }
}
