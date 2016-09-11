<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%-- -------------- Request parsing and variable declaration --------------------- --%>
<%@ page import="java.util.*,javax.servlet.http.HttpSession,servlet.ServletUtilities"%>

<% 	
	String[] colorInfo = request.getParameter("Color").split("=");
	String color = colorInfo[0];
	int colorPrice = (int)Float.parseFloat(colorInfo[1]);
	
	String[] transInfo = request.getParameter("Transmission").split("=");
	String trans = transInfo[0];
	int  transPrice = (int)Float.parseFloat(transInfo[1]);
	
	String[] absInfo = request.getParameter("ABS/Traction_Control").split("=");
	String abs = absInfo[0];
	int absPrice = (int)Float.parseFloat(absInfo[1]);
	
	String[] airbagInfo = request.getParameter("Side_Impact_Air_Bags").split("=");
	String airbag = airbagInfo[0];
	int airbagPrice = (int)Float.parseFloat(airbagInfo[1]);
	
	String[] roofInfo = request.getParameter("Power_Moonroof").split("=");
	String roof = roofInfo[0];
	int roofPrice = (int)Float.parseFloat(roofInfo[1]);
	
	int basePrice = (Integer)session.getAttribute("basePrice");
	
	int totalPrice = basePrice + colorPrice +transPrice+absPrice+airbagPrice+roofPrice;
 %>
	
<html>

<title>Car Configuration Proof of Concept</title>
<h1  align=center>Here is what you selected:</h1>
<body>
<%-- -------------- The result table--------------------- --%>
	<table border=3 align=center>
		<tr>
			<td align=center> <%= session.getAttribute("selectedName") %> </td> <%-- model name --%>
			<td align=center> base price </td>
			<td align=center> <%= basePrice %> </td>  <%--base price  --%>
		</tr>
		
		<tr>
			<td align=center> Color </td> 
			<td align=center> <%= color %> </td>
			<td align=center> <%= colorPrice %> </td>  
		</tr>
	
		<tr>
			<td align=center> Transmission </td> 
			<td align=center> <%= trans %> </td>
			<td align=center> <%= transPrice %> </td>  
		</tr>
		
		<tr>
			<td align=center> ABS/Traction Control </td> 
			<td align=center> <%= abs %> </td>
			<td align=center> <%= absPrice %> </td>  
		</tr>
		
		<tr>
			<td align=center> Side Impact Air Bags </td> 
			<td align=center> <%= airbag %> </td>
			<td align=center> <%= airbagPrice %> </td>  
		</tr>
		
		<tr>
			<td align=center> Power Moonroof </td> 
			<td align=center> <%= roof %> </td>
			<td align=center> <%= roofPrice %> </td>  
		</tr>
	
		<tr>
			<td align=center>Total Cost</td> 
			<td align=center> </td>
			<td align=center> <%= totalPrice %> </td>  
		</tr>

	</table>
</body>
</html>