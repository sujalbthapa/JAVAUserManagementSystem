package usrmng.admin.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import usrmng.admin.bean.UserMngBean;
import usrmng.admin.dao.UsrMngDao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


@WebServlet("/")
public class UsrMngServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     private UsrMngDao usrDao;
 
    public UsrMngServlet() {
    	usrDao = new UsrMngDao();
    }

 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		switch(action) {
			case"/new":
				showNewForm(request, response);
				break;
			case"/insert":
			try {
				insertUsr(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				break;
			case"/delete":
				deleteUsr(request,response);
				break;
			case"/edit":
				showEditForm(request, response);
				break;
			case"/update":
			try {
				updateUsr(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				break;
			default:
				listUsr(request, response);}
		}  	
	

	private void listUsr(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<UserMngBean> listUsr = usrDao.selectAllUsr();
			request.setAttribute("listUser", listUsr);
			jakarta.servlet.RequestDispatcher dispatcher = request.getRequestDispatcher("usrList.jsp");
			dispatcher.forward(request, response);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void updateUsr(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		UserMngBean Usr = new UserMngBean(id, name, email, address);
		usrDao.updateUser(Usr);
		response.sendRedirect("list");

	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id"));
		UserMngBean existUsr;
		try {
			existUsr = usrDao.selectUsr(id);
			jakarta.servlet.RequestDispatcher dispatcher = request.getRequestDispatcher("usrForm.jsp");
			request.setAttribute("user", existUsr);
			dispatcher.forward(request, response);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	private void deleteUsr(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		try {
			usrDao.deleteUsr(id);
		}catch(Exception e) {
			e.printStackTrace();
		}
		response.sendRedirect("list");
	}

	private void insertUsr(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		UserMngBean newUsr = new UserMngBean(name, email, address);
		usrDao.insertUsr(newUsr);
		response.sendRedirect("list");
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		jakarta.servlet.RequestDispatcher dispatcher = request.getRequestDispatcher("usrForm.jsp");
		dispatcher.forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
