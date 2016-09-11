package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import client.DefaultSocketClient;
import model.Automobile;

/**
 * Servlet implementation class CarConfigure
 * 
 * Second servlet class:
 * Let user choose from drop down menu to configure a car
 * 
 */
@WebServlet("/CarConfigure")
public class CarConfigure extends HttpServlet {
	private static final long serialVersionUID = 830480584L;
	
	private static final String GETAUTO_OPCODE = "4";
	private static final String NEXTPAGE = "ShowResult.jsp";
	
	private DefaultSocketClient client = null;
	private static String strLocalHost = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CarConfigure() {
        super();
    }
    
    /**
	 * @see Servlet#init(ServletConfig)
	 * create socket client to download list from server
	 */
	public void init(ServletConfig config) throws ServletException {
        try{
            strLocalHost = InetAddress.getLocalHost().getHostName();
        }
        catch (UnknownHostException e){
            System.err.println ("Unable to find local host");
        }
        
		client = new DefaultSocketClient(strLocalHost);
        client.setServletMode(true); // use servlet model rather than client model
        
		/* send opcode to server for download available model name list */
		client.setServletOpcode(GETAUTO_OPCODE);		
		client.start(); //could only start once
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
		String selectedName = request.getParameter("modelName");		
		System.out.println("Selected name is "+selectedName);
		
		client.setSelectedName(selectedName);
		
		Automobile auto = this.client.getSelectedAuto();
		
		// in case server has delay
		while(auto == null){
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			auto = this.client.getSelectedAuto();
		}
		
		// page title and header
		PrintWriter writer = response.getWriter();
		String title = "Basic Car Choice";
		writer.println(ServletUtilities.headWithTitle(title));
		writer.println("<h1 align=\"center\">Basic Car Choice</h1><br/><br/>");
		writer.println("<body>");
		
		// construct drop down menu content 
		int cnt = auto.getOpsetsSize();
		List<String> setNameList = new ArrayList<String>();
		Map<String,String> tableMap = new HashMap<String, String>();
		
		for(int i=0;i<cnt;++i){
			StringBuilder sb = new StringBuilder();
			String opsetName = auto.getOpsetNameAt(i);			
			Map<String, Float> content = auto.getOptionSetContent(i);
			
			//attribute name without space
			sb.append("<select name="+opsetName.replace(' ', '_')+"> <optgroup label=\"set "+opsetName+"\">"); 
			
			for (Map.Entry<String, Float> entry : content.entrySet()){
				sb.append("<option value=\""+entry.getKey()+"="+entry.getValue()+"\">"+entry.getKey()+"</option>");
			}
			
			sb.append("</optgroup>");
			
			setNameList.add(opsetName);
			tableMap.put(opsetName, sb.toString());
		}
		
		/* print the whole form */
		writer.println("<form action=\""+NEXTPAGE+"\" method=GET >");
		
		// print the table
		writer.println("<table border=2 align=center>");
		
		// the make and model
		writer.println("<tr><td>Make/Model</td><td>"+auto.getMake()+" "+auto.getModel()+"</td></tr>");
		
		for(int i=0;i<cnt;++i){
			String setName = setNameList.get(i);
			String options = tableMap.get(setName);
			
			writer.println("<tr><td>"+setName+"</td><td>"+options+"</td></tr>");			
		}
		
		// Done button
		writer.println("<tr align=right><td colspan=\"2\" align=right><input type=submit value=\"Done\"></td></tr></table>");
		
		// end of form
		writer.println("</form>");		
		
		// end of html	
		writer.println("</body></html>");
		
		// pass model name and base price through http session
		HttpSession httpSession = request.getSession();
		httpSession.setAttribute("selectedName", selectedName);
		httpSession.setAttribute("basePrice", auto.getBasePrice());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
