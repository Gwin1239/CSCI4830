
/**
 * @file SimpleFormInsert.java
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SimpleFormInsert")
public class InsertDjossou extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public InsertDjossou() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String bookTitle = request.getParameter("bookTitle");
      String fName = request.getParameter("fName");
      String lName = request.getParameter("lName");
      String price = request.getParameter("price");
      String isbn = request.getParameter("isbn");

      Connection connection = null;
      String insertSql = " INSERT INTO BookStoreTable (id, BOOK_TITLE, AUTHOR_FIRST_NAME, AUTHOR_LAST_NAME, PRICE, ISBN) values (default, ?, ?, ?, ?, ?)";

      try {
         DBConnectionDjossou.getDBConnection();
         connection = DBConnectionDjossou.connection;
         PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
         preparedStmt.setString(1, bookTitle);
         preparedStmt.setString(2, fName);
         preparedStmt.setString(3, lName);
         preparedStmt.setString(4, price);
         preparedStmt.setString(5, isbn);
         preparedStmt.execute();
         connection.close();
      } catch (Exception e) {
         e.printStackTrace();
      }

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Insert Data to DB table";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h2 align=\"center\">" + title + "</h2>\n" + //
            "<ul>\n" + //

            "  <li><b>Book Title</b>: " + bookTitle + "\n" + //
            "  <li><b>Author First Name</b>: " + fName + "\n" + //
            "  <li><b>Author Last Name</b>: " + lName + "\n" + //
            "  <li><b>Price</b>: " + price + "\n" + //
            "  <li><b>Isbn</b>: " + isbn + "\n" + //

            "</ul>\n");

      out.println("<a href=/tech-exercise/search_djossou.html>Search Data</a> <br>");
      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
