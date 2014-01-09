<%@page import="me.geloin.door.bean.ListDto"%>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	ListDto dto = new ListDto();
	dto.setUserdata(((RuntimeException) request
			.getAttribute("exception")).getMessage());
	ObjectMapper mapper = new ObjectMapper();
	response.getWriter().print(mapper.writeValueAsString(dto));
%>
