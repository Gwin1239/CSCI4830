import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SimpleFormSearch")
public class SearchDjossou extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public SearchDjossou() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String keyword = request.getParameter("keyword");
      search(keyword, response);
   }

   void search(String keyword, HttpServletResponse response) throws IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Books we have for that Search";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
            "transitional//en\">\n"; //
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h1 align=\"center\">" + title + "</h1>\n");

      Connection connection = null;
      PreparedStatement preparedStatement = null;
      try {
         DBConnectionDjossou.getDBConnection();
         connection = DBConnectionDjossou.connection;

         if (keyword.isEmpty()) {
            String selectSQL = "SELECT * FROM BookStoreTable";
            preparedStatement = connection.prepareStatement(selectSQL);
         } else {
            String selectSQL = "SELECT * FROM BookStoreTable WHERE BOOK_TITLE LIKE ?";
            String theEmail = "%" + keyword + "%";
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, theEmail);
         }
         ResultSet rs = preparedStatement.executeQuery();

         while (rs.next()) {
            int id = rs.getInt("id");
            String bookTitle = rs.getString("BOOK_TITLE").trim();
            String fName = rs.getString("AUTHOR_FIRST_NAME").trim();
            String lName = rs.getString("AUTHOR_LAST_NAME").trim();
            String price = rs.getString("PRICE").trim();
            String isbn = rs.getString("ISBN").trim();

            if (keyword.isEmpty() || bookTitle.contains(keyword)) {
               //out.println("ID: " + id + ", ");
               out.println("Book Title: " + bookTitle + ", ");
               out.println("Author First Name: " + fName + ", ");
               out.println("Author Last Name: " + lName + ", ");
               out.println("Price: $" + price + ", ");
               out.println("ISBN: " + isbn + "<br>");
            }
         }
         out.println("<a href=/tech-exercise/search_djossou.html>Search Books</a> <br>");
         out.println("</body></html>");
         rs.close();
         preparedStatement.close();
         connection.close();
      } catch (SQLException se) {
         se.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
            if (preparedStatement != null)
               preparedStatement.close();
         } catch (SQLException se2) {
         }
         try {
            if (connection != null)
               connection.close();
         } catch (SQLException se) {
            se.printStackTrace();
         }
      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
