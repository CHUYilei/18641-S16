package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import client.DefaultSocketClient;

/**
 * 18641 Project 1 Unit 5 
 * Servlet implementation class CarModelSelect
 * 
 * First servlet class:
 * Display a list of available models on server
 * Use drop down menu to let user to select a model from list
 * Send the result to next servlet fro car configuration
 */
@WebServlet("/CarModelSelect")
public class CarModelSelect extends HttpServlet {
	private static final long serialVersionUID = 19750285L;
	private static final String GETLIST_OPCODE = "3";
	private static final String NEXTPAGE = "CarConfigure";
	
	DefaultSocketClient client = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CarModelSelect() {
        super();
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 * create socket client to download list from server
	 */
	public void init(ServletConfig config) throws ServletException {
        String strLocalHost = "";
        try{
            strLocalHost = InetAddress.getLocalHost().getHostName();
        }
        catch (UnknownHostException e){
            System.err.println ("Unable to find local host");
        }

        client = new DefaultSocketClient(strLocalHost);
        client.setServletMode(true); // use servlet model rather than client model
        
		/* send opcode to server for download available model name list */
		this.client.setServletOpcode(GETLIST_OPCODE);
		this.client.start(); //could only start once
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		/* close the client socket and resources */
		this.client.closeSession();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {				
		/* show model name list in drop down menu */
		List<String> nameList = client.getModelNameList();
		
		/* page title */
		PrintWriter writer = response.getWriter();
		String title = "Car Model Selection";
		writer.println(ServletUtilities.headWithTitle(title));
		writer.println("<h1 align=center>Please choose a car model from the available list:</h1>");
		writer.println("<body>");
		
		// in case server delay
		while(nameList == null){
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			nameList = client.getModelNameList();
		}
		
		/* if list empty, not drop down menu */
		if(nameList.size() == 0){
			writer.println("<p align=center>No available list in server currently.</p>");
			writer.println("<p align=center>Please use command line client to upload a properties file first.</p></body></html>");
			return;
		}
		
		/* create drop down menu in form for name list */
		
		// print the table
		writer.println("<table border=2 align=center>");
		
		StringBuilder sb = new StringBuilder("<form method=GET align=center ");
		sb.append("action=\""+this.NEXTPAGE+"\"><br/>");
		sb.append("<tr><td><select name=\"modelName\">");
		
		for(String name:nameList){
			sb.append("<option value=\""+name+"\">"+name+"</option>");
		}
		
		sb.append("</select></td></tr>");
		
		/* Done button for submit */
		sb.append("<tr><td align=right><input type=submit value=\"Done\"></td></tr>");
		sb.append("</form></table></body></html>");
		
		writer.print(sb.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
